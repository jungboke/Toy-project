package me.jungboke.baekshop.service.order;

import lombok.RequiredArgsConstructor;
import me.jungboke.baekshop.domain.*;
import me.jungboke.baekshop.domain.item.Item;
import me.jungboke.baekshop.exception.NoItemException;
import me.jungboke.baekshop.exception.NoMemberException;
import me.jungboke.baekshop.exception.NoOrderException;
import me.jungboke.baekshop.repository.ItemRepository;
import me.jungboke.baekshop.repository.MemberRepository;
import me.jungboke.baekshop.repository.OrderRepository;
import me.jungboke.baekshop.repository.OrderSearch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Item> item = itemRepository.findById(itemId);

        if (!member.isPresent()) {
            throw new NoMemberException();
        }
        if (!item.isPresent()) {
            throw new NoItemException();
        }
        //배송정보 생성
        Delivery delivery = Delivery.createDelivery(member.get().getAddress(), DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.CreateOrderItem(item.get(), item.get().getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member.get(), delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    //주문취소
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문엔티티 조회
        Optional<Order> order = orderRepository.findById(orderId);

        if (!order.isPresent()) {
            throw new NoOrderException();
        }

        //주문취소
        order.get().cancel();
    }

    //주문검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findByOrderSearch(orderSearch);
    }
}
