package com.example.baekshopV2.service;

import com.example.baekshopV2.domain.*;
import com.example.baekshopV2.domain.Item.Book;
import com.example.baekshopV2.domain.Item.Movie;
import com.example.baekshopV2.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.example.baekshopV2.domain.Member.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("주문")
    void 주문() throws Exception {
        //given
        Member member = createMember("baek", new Address("11","11","11"));
        em.persist(member);
        Book book = Book.createBook("springbook", 20000, 5, "baek", "1111");
        em.persist(book);
        //when
        Order saveOrder = orderService.order(member.getId(), book.getId(), 2);
        Optional<Order> findOrder = orderRepository.findById(saveOrder.getId());
        //then
        assertThat(findOrder.get().getMember()).isEqualTo(member);
        assertThat(book.getStockQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("영화주문")
    void 영화주문() throws Exception {
        //given
        Member member = createMember("baek", new Address("11","11","11"));
        em.persist(member);
        Movie movie = Movie.createMovie("Movie1", 20000, 3, "baek", "seung");
        em.persist(movie);
        //when
        Order saveOrder = orderService.order(member.getId(), movie.getId(), 2);
        Optional<Order> findOrder = orderRepository.findById(saveOrder.getId());
        //then
        assertThat(findOrder.get().getMember()).isEqualTo(member);
        assertThat(movie.getStockQuantity()).isEqualTo(1);
    }


    @Test
    @DisplayName("취소")
    void 취소() throws Exception {
        //given
        Member member = createMember("baek", new Address("11","11","11"));
        em.persist(member);
        Book book = Book.createBook("springbook", 20000, 5, "baek", "1111");
        em.persist(book);
        //when
        Order saveOrder = orderService.order(member.getId(), book.getId(), 2);
        orderService.cancelOrder(saveOrder.getId());
        //then
        assertThat(book.getStockQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("영화취소")
    void 영화취소() throws Exception {
        //given
        Member member = createMember("baek", new Address("11","11","11"));
        em.persist(member);
        Movie movie = Movie.createMovie("Movie1", 20000, 3, "baek", "seung");
        em.persist(movie);
        //when
        Order saveOrder = orderService.order(member.getId(), movie.getId(), 2);
        orderService.cancelOrder(saveOrder.getId());
        //then
        assertThat(movie.getStockQuantity()).isEqualTo(3);
    }

}