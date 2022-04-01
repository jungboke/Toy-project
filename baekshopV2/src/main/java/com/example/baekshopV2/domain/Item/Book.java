package com.example.baekshopV2.domain.Item;

import com.example.baekshopV2.domain.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("B") // Entity간 부모자식 표현에 사용
public class Book extends Item {
    private String author;
    private String isbn;

    public static Book createBook(String name, int price, int stockQuantity,
                                  String author, String isbn, Category... categories) {
        Book book = new Book();
        book.name = name;
        book.price = price;
        book.stockQuantity = stockQuantity;
        book.author = author;
        book.isbn = isbn;
        for (Category category : categories) {
            book.addCategory(category);
        }
        return book;
    }
}
