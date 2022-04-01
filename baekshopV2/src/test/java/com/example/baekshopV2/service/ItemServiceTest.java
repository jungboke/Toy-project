package com.example.baekshopV2.service;

import com.example.baekshopV2.domain.Item.Book;
import com.example.baekshopV2.domain.Item.Item;
import com.example.baekshopV2.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("등록")
    void 등록() throws Exception {
        //given
        Book book = Book.createBook("springbook", 20000, 5, "baek", "1111");
        //when
        Item saveItem = itemService.saveItem(book);
        Optional<Item> findBook = itemRepository.findById(saveItem.getId());
        //then
        assertThat(book).isEqualTo(findBook.get());
    }

    @Test
    @DisplayName("조회")
    void 조회() throws Exception {
        //given
        Book book = Book.createBook("springbook", 20000, 5, "baek", "1111");
        //when
        Item saveItem = itemService.saveItem(book);
        Item findBook = itemService.findOne(saveItem.getId());
        //then
        assertThat(book).isEqualTo(findBook);
    }

    @Test
    @DisplayName("업데이트")
    void 업데이트() throws Exception {
        //given
        Book book = Book.createBook("springbook", 20000, 5, "baek", "1111");
        //when
        Item saveItem = itemService.saveItem(book);
        itemService.updateItem(saveItem.getId(),"kotlin",10000,4);
        Item findBook = itemService.findOne(saveItem.getId());
        //then
        assertThat(findBook.getName()).isEqualTo("kotlin");
    }

}