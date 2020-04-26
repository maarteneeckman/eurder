package com.switchfully.eurder.domain.item;

import com.switchfully.eurder.domain.exceptions.ItemNotFoundException;
import com.switchfully.eurder.domain.exceptions.ItemNotUniqueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ItemRepositoryNoDB {

    private final Map<UUID, Item> items;

    @Autowired
    public ItemRepositoryNoDB() {
        this.items = new HashMap<>();
    }

    public void addItem(Item newItem){
        if(items.values().stream()
                .anyMatch(item -> item.equals(newItem))){
            throw new ItemNotUniqueException("Item already exists!");
        }
        items.put(newItem.getId(),newItem);
    }

    public Item getItem(UUID itemId) {
        if(items.get(itemId) == null){
            throw new ItemNotFoundException("Item does not exist");
        }
        return items.get(itemId);
    }

    public Collection<Item> getAllItems() {
        return items.values();
    }
}
