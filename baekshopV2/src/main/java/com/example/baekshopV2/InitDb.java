package com.example.baekshopV2;

import com.example.baekshopV2.domain.*;
import com.example.baekshopV2.domain.Item.Book;
import com.example.baekshopV2.repository.ItemRepository;
import com.example.baekshopV2.repository.MemberRepository;
import com.example.baekshopV2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 종 주문 2개
 * * userA
 * 	 * JPA1 BOOK
 * 	 * JPA2 BOOK
 * * userB
 * 	 * SPRING1 BOOK
 * 	 * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
//        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberRepository memberRepository;
        private final ItemRepository itemRepository;
        private final OrderRepository orderRepository;
        private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        public void dbInit1() {
            for(int i=1;i<=30;i++) {
                String si = Integer.toString(i);
                Member member = Member.createMember("user" + si, new Address("서울", si, si+si),"test1", passwordEncoder.encode(si+si));
                memberRepository.save(member);

                Book book1 = Book.createBook("JPA BOOK" +si, 10000*i, 100*i, "baek"+si, si+si);
                itemRepository.save(book1);

                Book book2 = Book.createBook("JPA2 BOOK" +si, 20000*i, 100*i, "baek"+si, si+si+si);
                itemRepository.save(book2);

                OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000*i, 1);
                OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000*i, 2);

                Delivery delivery = Delivery.createDelivery(member.getAddress(), DeliveryStatus.READY);

                Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
                orderRepository.save(order);
            }
        }

//        public void dbInit2() {
//            Member member = Member.createMember("userB", new Address("진주", "2", "2222"),"test2", passwordEncoder.encode("12345"));
//            memberRepository.save(member);
//
//            Book book1 = Book.createBook("SPRING1 BOOK", 20000, 200, "baek", "112277");
//            itemRepository.save(book1);
//
//            Book book2 = Book.createBook("SPRING2 BOOK", 40000, 300, "baek", "112288");
//            itemRepository.save(book2);
//
//            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
//            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
//
//            Delivery delivery = Delivery.createDelivery(member.getAddress(), DeliveryStatus.READY);
//
//            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
//            orderRepository.save(order);
//        }

    }
}
