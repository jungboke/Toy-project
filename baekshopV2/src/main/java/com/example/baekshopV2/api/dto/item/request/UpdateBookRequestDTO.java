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
public class UpdateBookRequestDTO {

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

}
