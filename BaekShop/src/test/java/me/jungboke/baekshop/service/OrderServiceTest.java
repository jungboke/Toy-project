package me.jungboke.baekshop.service;

import me.jungboke.baekshop.domain.Address;
import me.jungboke.baekshop.domain.Member;
import me.jungboke.baekshop.domain.Order;
import me.jungboke.baekshop.domain.OrderStatus;
import me.jungboke.baekshop.domain.item.Book;
import me.jungboke.baekshop.repository.OrderRepository;
import me.jungboke.baekshop.service.order.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품주문")
    void 상품주문() throws Exception {
        //given
        Member member = Member.createMember("baek", new Address("대전", "유성구", "123-123"),"baek", "1111");
        em.persist(member);
        Book item = Book.createBook("JPA", 10000, 10, "james", "103");
        em.persist(item);
        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Optional<Order> findOrder = orderRepository.findById(orderId);
        //then
        assertEquals(OrderStatus.ORDER, findOrder.get().getStatus(),"상품주문시 상태는 ORDER");
    }

}