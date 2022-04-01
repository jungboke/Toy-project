package com.example.baekshopV2.domain.Item;

import com.example.baekshopV2.domain.Category;
import com.example.baekshopV2.domain.OrderItem;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public class Item {
    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "item_id")
    private String id;

    protected String name;

    protected int price;

    protected int stockQuantity;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    protected List<OrderItem> orderItems = new ArrayList<>();

    @ManyToMany(mappedBy = "items", cascade = CascadeType.REMOVE)
    protected List<Category> categories = new ArrayList<>();

    public void setOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        category.setItem(this);
    }

    public void update(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) {
            throw new IllegalStateException("수량이 부족합니다.");
        }
        this.stockQuantity = restStock;
    }
}
