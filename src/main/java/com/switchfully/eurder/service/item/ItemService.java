package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        itemRepository.addItem(item);
        return new ItemDto(item);
    }

    private void validate(CreateItemDto createItemDto) {
        if(createItemDto.getName()==null || createItemDto.getName().isEmpty()){
            throw new IllegalArgumentException("Name is not valid");
        }
        if(createItemDto.getAmountInStock() < 0){
            throw new IllegalArgumentException("Amount of stock is not valid");
        }
        if(createItemDto.getDescription()==null || createItemDto.getDescription().isEmpty()){
            throw new IllegalArgumentException("Description is not valid");
        }
        if(createItemDto.getPrice() < 0){
            throw new IllegalArgumentException("Price is not valid");
        }
    }
}
