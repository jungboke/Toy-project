package me.jungboke.baekshop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jungboke.baekshop.domain.Category;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Item {

    private String director;
    private String actor;

    public static Movie createMovie(String name, int price, int stockQuantity,
                                    String director, String actor) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setPrice(price);
        movie.setStockQuantity(stockQuantity);
        movie.setDirector(director);
        movie.setActor(actor);
        return movie;
    }
}
