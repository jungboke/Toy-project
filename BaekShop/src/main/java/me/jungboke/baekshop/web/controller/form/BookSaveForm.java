package me.jungboke.baekshop.web.controller.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BookSaveForm {

    @NotBlank
    private String name;
    @NotNull
    @Range(min=1000, max=100000)
    private Integer price;
    @NotNull
    @Max(9999)
    private Integer stockQuantity;
    @NotBlank
    private String author;
    @NotBlank
    private String isbn;

    public BookSaveForm(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
