## Repository
ItemRepository
````
public interface ItemRepository extends JpaRepository<Item, Long> {
}
````
MemberRepository
````
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
}
````
OrderRepository
````
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
````
OrderRepositoryCustom
````
public interface OrderRepositoryCustom {
    List<Order> findByOrderSearch(OrderSearch orderSearch);
}
````
OrderRepositoryImpl
````
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findByOrderSearch(OrderSearch orderSearch) {
        return queryFactory
                .selectFrom(order)
                .leftJoin(order.member, member)
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
````
OrderSearch
````
@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
````