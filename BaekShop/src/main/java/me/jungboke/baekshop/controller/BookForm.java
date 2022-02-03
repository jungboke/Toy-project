package me.jungboke.baekshop.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookForm {

    private String id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    public BookForm(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
