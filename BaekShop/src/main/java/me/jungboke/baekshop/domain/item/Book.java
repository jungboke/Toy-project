package me.jungboke.baekshop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jungboke.baekshop.domain.Category;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {

    private String author;
    private String isbn;

    public static Book createBook(String name, int price, int stockQuantity,
                                  String author, String isbn) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        return book;
    }
}
