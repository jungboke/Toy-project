package me.jungboke.baekshop.repository;

import me.jungboke.baekshop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
