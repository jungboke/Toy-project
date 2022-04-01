package com.example.baekshopV2.service;

import com.example.baekshopV2.domain.*;
import com.example.baekshopV2.domain.Item.Item;
import com.example.baekshopV2.repository.ItemRepository;
import com.example.baekshopV2.repository.MemberRepository;
import com.example.baekshopV2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // C
    @Transactional
    public Order order(final String memberId, final String itemId, final int count) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(!member.isPresent()) {
            throw new RuntimeException("Cannot find Entity.");
        }
        Optional<Item> item = itemRepository.findById(itemId);
        if(!item.isPresent()) {
            throw new RuntimeException("Cannot find Entity.");
        }
        Delivery delivery = Delivery.createDelivery(member.get().getAddress(), DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item.get(), item.get().getPrice(), count);

        Order order = Order.createOrder(member.get(), delivery, orderItem);
        orderRepository.save(order);
        return order;
    }

    // R
    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    // R
    public Order findOne(final String id) {
        Optional<Order> findOrder = orderRepository.findById(id);
        if(!findOrder.isPresent()) {
            throw new RuntimeException("Cannot find Entity.");
        }
        return findOrder.get();
    }

//    // D
//    @Transactional
//    public void cancelOrder(String orderId) {
//        Optional<Order> order = orderRepository.findById(orderId);
//        order.get().cancel();
//    }

    // D
    @Transactional
    public void deleteOrder(final String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(!order.isPresent()) {
            throw new RuntimeException("Cannot find Entity.");
        }
        order.get().cancel();
        try {
            orderRepository.delete(order.get());
        } catch (Exception e) {
            log.error("error deleting entity ", order.get().getId(), e);
            throw new RuntimeException("error deleting entity " + order.get().getId());
        }
    }

    private void validate(final Order order) {
        if(order == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(order.getId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
