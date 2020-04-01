package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item createItemDtoToItem(CreateItemDto createItemDto) {
        return new Item(
                createItemDto.getName(),
                createItemDto.getDescription(),
                createItemDto.getPrice(),
                createItemDto.getAmountInStock());
    }
}
