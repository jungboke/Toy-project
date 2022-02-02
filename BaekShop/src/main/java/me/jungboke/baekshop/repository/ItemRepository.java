package me.jungboke.baekshop.repository;

import me.jungboke.baekshop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
