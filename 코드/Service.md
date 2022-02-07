## Service
ItemService
````
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    //물품저장
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    //전체물품조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    //단일물품조회
    public Item findOne(Long itemId) {
        Optional<Item> findItem = itemRepository.findById(itemId);
        if (!findItem.isPresent()) {
            throw new NoItemException("물품이 존재하지 않습니다.");
        }
        return findItem.get();
    }

    @Transactional
    public void updateItem(Long itemid, String name, int price, int stockQuantity) {
        Optional<Item> findItem = itemRepository.findById(itemid);
        findItem.get().setName(name);
        findItem.get().setPrice(price);
        findItem.get().setStockQuantity(stockQuantity);
    }
}
````
MemberService
````
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(Member member) {
        //중복회원검증
        validateDuplicateMember(member);
        log.info("중복검증완료");
        Member saveMember = memberRepository.save(member);
        return saveMember.getId();
    }

    //회원전체조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원단일조회
    public Member findOne(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (!findMember.isPresent()) {
            throw new NoMemberException("회원이 존재하지 않습니다.");
        }
        return findMember.get();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> Member = memberRepository.findByName(member.getName());
        if (!Member.isEmpty()) {
            throw new ExistMemberException("이미 존재하는 회원입니다.");
        }
    }
}
````
OrderService
````
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
````