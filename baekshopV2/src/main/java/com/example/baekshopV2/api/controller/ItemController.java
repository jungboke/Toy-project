package com.example.baekshopV2.api.controller;

import com.example.baekshopV2.api.dto.item.request.CreateBookRequestDTO;
import com.example.baekshopV2.api.dto.item.request.UpdateBookRequestDTO;
import com.example.baekshopV2.api.dto.item.response.BookResponseDTO;
import com.example.baekshopV2.domain.Item.Book;
import com.example.baekshopV2.domain.Item.Item;
import com.example.baekshopV2.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ItemController {

    private  final ItemService itemService;

    // C
    @PostMapping("/v1/books")
    public ResponseEntity<?> saveItemV1(@RequestBody @Validated CreateBookRequestDTO DTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("검증 오류 발생 error = {}", bindingResult);
            throw new IllegalArgumentException();
        }
        Book book = Book.createBook(DTO.getName(), DTO.getPrice(), DTO.getStockQuantity(),
                DTO.getAuthor(), DTO.getIsbn());
        Book saveBook = (Book)itemService.saveItem(book);
        BookResponseDTO dto = BookResponseDTO.create(saveBook.getId(), saveBook.getName(), saveBook.getPrice(), saveBook.getStockQuantity(),
                saveBook.getAuthor(), saveBook.getIsbn());
        log.info("도서 등록 완료");

        return ResponseEntity.ok(dto);
    }

    // R
    @GetMapping("/v1/books")
    public ResponseEntity<?> findItemsV1() {
        List<Item> items = itemService.findItems();
        List<BookResponseDTO> dtos = items.stream().map(BookResponseDTO::new).collect(Collectors.toList());
        log.info("도서 리스트 조회 완료");

        return ResponseEntity.ok(dtos);
    }

    // R
    @GetMapping("/v1/books/{id}")
    public ResponseEntity<?> findItemV1(@PathVariable("id") String id) {
        Book findBook = (Book) itemService.findOne(id);
        BookResponseDTO dto = BookResponseDTO.create(findBook.getId(), findBook.getName(),
                findBook.getPrice(), findBook.getStockQuantity(),
                findBook.getAuthor(), findBook.getIsbn());
        log.info("도서 단일 조회 완료");

        return ResponseEntity.ok(dto);
    }

    // U
    @PutMapping("/v1/books/update")
    public ResponseEntity<?> updateItemV1(@RequestBody @Validated UpdateBookRequestDTO DTO,
                                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("검증 오류 발생 error = {}", bindingResult);
            throw new IllegalArgumentException();
        }
        itemService.updateItem(DTO.getId(), DTO.getName(), DTO.getPrice(), DTO.getStockQuantity());
        Book findBook = (Book) itemService.findOne(DTO.getId());
        BookResponseDTO dto = BookResponseDTO.create(findBook.getId(), findBook.getName(), findBook.getPrice(), findBook.getStockQuantity(),
                findBook.getAuthor(), findBook.getIsbn());
        log.info("도서 수정 완료");

        return ResponseEntity.ok(dto);
    }

    // D
    @DeleteMapping("/v1/books/delete/{id}")
    public ResponseEntity<?> deleteItemV1(@PathVariable("id") String id) {
        itemService.deleteItem(id);
        log.info("도서 삭제 완료");

        return ResponseEntity.ok(id);
    }
}
