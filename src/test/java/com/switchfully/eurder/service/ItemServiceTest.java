package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.service.item.CreateItemDto;
import com.switchfully.eurder.service.item.ItemDto;
import com.switchfully.eurder.service.item.ItemMapper;
import com.switchfully.eurder.service.item.ItemService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ItemServiceTest {

    @Test
    void addItem_givenValidCreateItemDto_addsItemToRepository(){
        //given
        ItemRepository itemRepository = new ItemRepository();
        ItemService itemService = new ItemService(itemRepository, new ItemMapper());
        CreateItemDto createItemDto = new CreateItemDto(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        //when
        itemService.addItem(createItemDto);
        //then
        assertThat(itemRepository.getAllItems().values().size()).isEqualTo(1);
    }

    @Test
    void addItem_givenValidCreateItemDto_returnsCorrectDto(){
        //given
        ItemRepository itemRepository = new ItemRepository();
        ItemService itemService = new ItemService(itemRepository, new ItemMapper());
        CreateItemDto createItemDto = new CreateItemDto(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        Item item = new Item(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        ItemDto expectedItemDto = new ItemDto(item);
        //when
        ItemDto actualItemDto = itemService.addItem(createItemDto);
        //then
        assertThat(actualItemDto).isEqualTo(expectedItemDto);
    }

    @Test
    void addItem_ifDataAreInvalid_throwException(){
        //given
        ItemRepository itemRepository = new ItemRepository();
        ItemService itemService = new ItemService(itemRepository, new ItemMapper());
        CreateItemDto createItemDto = new CreateItemDto(
                "",
                "",
                -5,
                -6);
        //then
        assertThatThrownBy(() -> itemService.addItem(createItemDto)).isInstanceOf(IllegalArgumentException.class);

    }

}
