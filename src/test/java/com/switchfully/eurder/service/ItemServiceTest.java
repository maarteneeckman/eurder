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
        assertThat(itemRepository.getAllItems().size()).isEqualTo(1);
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

    @Test
    void getAllItems_returnsCorrectItemDtos(){
        //given
        ItemRepository itemRepository = new ItemRepository();
        ItemService itemService = new ItemService(itemRepository, new ItemMapper());

        Item item1 = new Item(
                "Pen",
                "It writes underwater. It also writes other words.",
                12.5,
                20);
        Item item2 = new Item(
                "Coffee",
                "Keeps you awake.",
                2.0,
                150);
        itemRepository.addItem(item1);
        itemRepository.addItem(item2);

        //then
        assertThat(itemService.getAllItems()).containsExactlyInAnyOrder(new ItemDto(item1), new ItemDto(item2));

    }

}
