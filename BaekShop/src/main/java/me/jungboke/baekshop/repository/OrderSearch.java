package me.jungboke.baekshop.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.jungboke.baekshop.domain.OrderStatus;

@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
