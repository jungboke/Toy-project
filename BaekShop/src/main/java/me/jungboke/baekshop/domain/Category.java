package me.jungboke.baekshop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jungboke.baekshop.domain.item.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "category_item",
            joinColumns =@JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public static Category CreateCategory(String name, List<Item> items,
                                          Category parent, List<Category> child) {
        Category category = new Category();
        category.setName(name);
        for (Item item : items) {
            category.getItems().add(item);
        }
        category.setParent(parent);
        for (Category category1 : child) {
            category.getChild().add(category1);
        }
        return category;
    }

    public void addItems(Item item) {
        this.items.add(item);
        item.getCategories().add(this);
    }

    public void addParent(Category category) {
        this.parent = category;
        category.child.add(this);
    }
}
