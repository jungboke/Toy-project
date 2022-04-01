package com.example.baekshopV2.repository;

import com.example.baekshopV2.domain.Item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {

}
