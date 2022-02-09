package me.jungboke.baekshop.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.jungboke.baekshop.domain.*;

import javax.persistence.EntityManager;
import java.util.List;

import static me.jungboke.baekshop.domain.QDelivery.*;
import static me.jungboke.baekshop.domain.QMember.*;
import static me.jungboke.baekshop.domain.QOrder.*;
import static me.jungboke.baekshop.domain.QOrderItem.*;
import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.StringUtils.isEmpty;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findByOrderSearch(OrderSearch orderSearch) {
        return  queryFactory
                .selectFrom(order)
                .leftJoin(order.member, member).fetchJoin()
                .leftJoin(order.orderItems, orderItem).fetchJoin()
                .leftJoin(order.delivery, delivery).fetchJoin()
                .where(
                        membernameEq(orderSearch.getMemberName()),
                        orderstatusEq(orderSearch.getOrderStatus())
                )
                .fetch();
    }

    private BooleanExpression orderstatusEq(OrderStatus orderStatus) {
        return orderStatus != null ? order.status.eq(orderStatus) : null;
    }

    private BooleanExpression membernameEq(String memberName) {
        return hasText(memberName) ? order.member.name.eq(memberName) : null;
    }
}
