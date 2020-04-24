package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepositoryNoDB;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final CustomerRepositoryNoDB customerRepository;
    private final ItemRepository itemRepository;

    public OrderMapper(CustomerRepositoryNoDB customerRepository, ItemRepository itemRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    public Order createOrderDtoToOrder(CreateOrderDto createOrderDto) {
        Customer customer = customerRepository.getCustomer(createOrderDto.getCustomerId());
        List<ItemGroup> itemGroups = createOrderDto.getCreateItemGroupDtos().stream()
                .map(groupDto -> itemGroupDtoToItemGroup(groupDto))
                .collect(Collectors.toList());
        return new Order(customer, itemGroups);
    }

    public ItemGroup itemGroupDtoToItemGroup(CreateItemGroupDto groupDto) {
        Item item = itemRepository.getItem(groupDto.getItemId());
        int amount = groupDto.getAmount();
        LocalDate deliveryDate;
        if (item.getAmountInStock() >= amount) {
            deliveryDate = LocalDate.now().plusDays(1);
        } else {
            deliveryDate = LocalDate.now().plusDays(7);
        }
        return new ItemGroup(item, amount, deliveryDate, item.getPrice()*amount);
    }

    public OrderDto orderToOrderDto(Order order) {
        List<ItemGroupDto> groupDtos = order.getItemGroups().stream()
                .map(itemGroup -> itemGroupToItemGroupDto(itemGroup))
                .collect(Collectors.toList());
        return new OrderDto(order.getId(), order.getCustomer().getId(), groupDtos);
    }

    public ItemGroupDto itemGroupToItemGroupDto(ItemGroup itemGroup) {
        return new ItemGroupDto(
                itemGroup.getItem().getId(),
                itemGroup.getAmount(),
                itemGroup.getShippingDate(),
                itemGroup.getPrice());
    }
}
