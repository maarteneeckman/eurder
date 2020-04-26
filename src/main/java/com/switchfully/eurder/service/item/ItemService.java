package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.exceptions.ItemNotFoundException;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ItemRepositoryNoDB;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ItemService {

    ItemRepository itemRepository;
    ItemMapper itemMapper;

    @Autowired
    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public ItemDto addItem(CreateItemDto createItemDto) {
        validate(createItemDto);
        Item item = itemMapper.createItemDtoToItem(createItemDto);
        itemRepository.save(item);
        return new ItemDto(item);
    }

    private void validate(CreateItemDto createItemDto) {
        if (createItemDto.getName() == null || createItemDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is not valid");
        }
        if (createItemDto.getAmountInStock() < 0) {
            throw new IllegalArgumentException("Amount of stock is not valid");
        }
        if (createItemDto.getDescription() == null || createItemDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description is not valid");
        }
        if (createItemDto.getPrice() < 0) {
            throw new IllegalArgumentException("Price is not valid");
        }
    }

    public Collection<ItemDto> getAllItems() {
        Collection<ItemDto> allItemDtos = new ArrayList<>();
        itemRepository.findAll().forEach(item -> allItemDtos.add(new ItemDto(item)));
        return allItemDtos;
    }

    public ItemDto updateItem(UUID id, CreateItemDto createItemDto) {
        Item itemWithNewData = itemMapper.createItemDtoToItem(createItemDto);
        Item existingItem = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item does not yet exist."));
        BeanUtils.copyProperties(itemWithNewData, existingItem);
        return new ItemDto(existingItem);
    }
}
