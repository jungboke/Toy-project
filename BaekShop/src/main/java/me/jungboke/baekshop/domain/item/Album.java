package me.jungboke.baekshop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jungboke.baekshop.domain.Category;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item {

    private String artist;
    private String etc;

    public static Album createAlbum(String name, int price, int stockQuantity,
                                    String artist, String etc) {
        Album album = new Album();
        album.setName(name);
        album.setPrice(price);
        album.setStockQuantity(stockQuantity);
        album.setArtist(artist);
        album.setEtc(etc);

        return album;
    }
}
