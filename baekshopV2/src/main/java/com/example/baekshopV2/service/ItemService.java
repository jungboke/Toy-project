package com.example.baekshopV2.service;

import com.example.baekshopV2.domain.Item.Item;
import com.example.baekshopV2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    // C
    @Transactional
    public Item saveItem(final Item item) {
        Item saveItem = itemRepository.save(item);
        return saveItem;
    }

    // R
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    // R
    public Item findOne(final String itemId) {
        return itemRepository.findById(itemId).get();
    }

    // U
    @Transactional
    public void updateItem(final String itemId, final String name, final int price, final int stockQuantity) {
        Optional<Item> item = itemRepository.findById(itemId);
        if(!item.isPresent()) {
            throw new RuntimeException("Cannot find Entity.");
        }
        item.get().update(name,price,stockQuantity);
    }

    // D
    @Transactional
    public void deleteItem(final String itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if(!item.isPresent()) {
            throw new RuntimeException("Cannot find Entity.");
        }
        itemRepository.delete(item.get());
    }

    private void validate(final Item item) {
        if(item == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(item.getId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
