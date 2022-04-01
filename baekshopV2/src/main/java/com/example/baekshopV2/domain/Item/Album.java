package com.example.baekshopV2.domain.Item;

import com.example.baekshopV2.domain.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Cache;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("A") // Entity간 부모자식 표현에 사용
public class Album extends Item {

    private String artist;
    private String etc;

    public static Album createAlbum(String name, int price, int stockQuantity,
                                    String artist, String etc, Category... categories) {
        Album album = new Album();
        album.name = name;
        album.price = price;
        album.stockQuantity = stockQuantity;
        album.artist = artist;
        album.etc = etc;
        for(Category category : categories) {
            album.addCategory(category);
        }
        return album;
    }
}
