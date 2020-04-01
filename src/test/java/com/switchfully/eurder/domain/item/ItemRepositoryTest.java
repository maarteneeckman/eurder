package com.switchfully.eurder.domain.item;

import com.switchfully.eurder.domain.exceptions.ItemNotFoundException;
import com.switchfully.eurder.domain.exceptions.ItemNotUniqueException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemRepositoryTest {

    @Test
    void addItem_ifItemDoesNotExistYet_addsItemToRepository(){
        //given
        ItemRepository itemRepository = new ItemRepository();
        Item item = new Item("Cookie","It's delicious", 5.0, 100);
        UUID itemId = item.getId();
        //when
        itemRepository.addItem(item);
        //then
        assertThat(itemRepository.getItem(itemId)).isEqualTo(item);
    }

    @Test
    void addItem_ifItemAlreadyExists_throwsException(){
        //given
        ItemRepository itemRepository = new ItemRepository();
        Item item = new Item("Cookie","It's delicious", 5.0, 100);
        Item item2 = new Item("Cookie","It's delicious", 5.0, 100);
        //when
        itemRepository.addItem(item);
        //then
        assertThatThrownBy(() -> itemRepository.addItem(item2)).isInstanceOf(ItemNotUniqueException.class);
    }

    @Test
    void getItem_ifItemDoesNotExist_throwException(){
        //given
        ItemRepository itemRepository = new ItemRepository();
        //then
        assertThatThrownBy(() -> itemRepository.getItem(UUID.randomUUID())).isInstanceOf(ItemNotFoundException.class);

    }


}