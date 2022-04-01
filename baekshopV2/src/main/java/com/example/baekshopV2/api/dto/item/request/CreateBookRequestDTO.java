package com.example.baekshopV2.api.dto.item.request;

import com.example.baekshopV2.domain.Item.Book;
import com.example.baekshopV2.domain.Item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequestDTO {

    private String id;
    @NotBlank
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stockQuantity;
    @NotBlank
    private String author;
    @NotBlank
    private String isbn;

    public CreateBookRequestDTO(Item item) {
        Book book = (Book) item;
        this.id = book.getId();
        this.name = book.getName();
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }

    public static CreateBookRequestDTO createBookDTO(String id, String name, int price, int stockQuantity,
                                                     String author, String isbn) {
        CreateBookRequestDTO bookDTO = new CreateBookRequestDTO();
        bookDTO.id = id;
        bookDTO.name = name;
        bookDTO.price = price;
        bookDTO.stockQuantity = stockQuantity;
        bookDTO.author = author;
        bookDTO.isbn = isbn;
        return bookDTO;
    }
}
