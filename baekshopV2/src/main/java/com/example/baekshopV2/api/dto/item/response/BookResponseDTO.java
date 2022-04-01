package com.example.baekshopV2.api.dto.item.response;

import com.example.baekshopV2.domain.Item.Book;
import com.example.baekshopV2.domain.Item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {

    private String id;
    private String name;
    private Integer price;
    private Integer stockQuantity;
    private String author;
    private String isbn;

    public BookResponseDTO(Item item) {
        Book book = (Book) item;
        this.id = book.getId();
        this.name = book.getName();
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }

    public static BookResponseDTO create(String id, String name, int price, int stockQuantity,
                                                String author, String isbn) {
        BookResponseDTO bookDTO = new BookResponseDTO();
        bookDTO.id = id;
        bookDTO.name = name;
        bookDTO.price = price;
        bookDTO.stockQuantity = stockQuantity;
        bookDTO.author = author;
        bookDTO.isbn = isbn;
        return bookDTO;
    }
}
