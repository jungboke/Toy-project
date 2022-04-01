package com.example.baekshopV2.domain.Item;

import com.example.baekshopV2.domain.Category;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("M") // Entity간 부모자식 표현에 사용
public class Movie extends Item {
    private String director;
    private String actor;

    public static Movie createMovie(String name, int price, int stockQuantity,
                                    String director, String actor, Category... categories) {
        Movie movie = new Movie();
        movie.name = name;
        movie.price = price;
        movie.stockQuantity = stockQuantity;
        movie.director = director;
        movie.actor = actor;
        for (Category category : categories) {
            movie.addCategory(category);
        }
        return movie;
    }

}
