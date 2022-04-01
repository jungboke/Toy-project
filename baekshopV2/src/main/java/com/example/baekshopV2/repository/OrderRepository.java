package com.example.baekshopV2.repository;

import com.example.baekshopV2.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
