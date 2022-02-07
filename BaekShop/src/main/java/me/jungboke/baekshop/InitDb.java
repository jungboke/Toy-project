package me.jungboke.baekshop;

import lombok.RequiredArgsConstructor;
import me.jungboke.baekshop.domain.*;
import me.jungboke.baekshop.domain.item.Book;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            System.out.println("Init1" + this.getClass());
            Member member = Member.createMember("userA", new Address("서울", "1", "1111"), "userA", "1234");
            em.persist(member);

            Book book1 = Book.createBook("JPA2 BOOK", 10000, 100, "백","1234");
            em.persist(book1);

            Book book2 = Book.createBook("JPA2 BOOK", 20000, 100, "백","1234");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.CreateOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.CreateOrderItem(book2, 20000, 2);

            Delivery delivery = Delivery.createDelivery(member.getAddress(), DeliveryStatus.READY);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = Member.createMember("userB", new Address("진주","2","2222"),"userB", "1234");
            em.persist(member);

            Book book1 = Book.createBook("SPRING1 BOOK", 20000, 200, "백", "1234");
            em.persist(book1);

            Book book2 = Book.createBook("SPRING1 BOOK", 40000, 300, "백", "1234");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.CreateOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.CreateOrderItem(book2, 40000, 4);

            Delivery delivery = Delivery.createDelivery(member.getAddress(), DeliveryStatus.READY);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

    }
}

