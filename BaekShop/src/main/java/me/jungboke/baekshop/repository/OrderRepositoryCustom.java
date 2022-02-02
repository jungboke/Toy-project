package me.jungboke.baekshop.repository;

import me.jungboke.baekshop.domain.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findByOrderSearch(OrderSearch orderSearch);
}
