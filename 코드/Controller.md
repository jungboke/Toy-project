## Controller
InitDb
````
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
            Member member = Member.createMember("userA", new Address("서울", "1", "1111"));
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
            Member member = Member.createMember("userB", new Address("진주","2","2222"));
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
````
HomeController
````
@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }
}
````
MemberController
````
@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        log.info("CreateForm");
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = Member.createMember(form.getName(), address);
        log.info("member 생성완료");
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String ListMember(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        log.info("ListMember");
        return "members/memberList";
    }
}
````
MemberForm
````
@Getter
@Setter
@NoArgsConstructor
public class MemberForm {

    private String name;

    private String city;
    private String street;
    private String zipcode;
}
````
ItemController
````
@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        log.info("Book 생성");
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String create(@Valid BookForm bookForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }
        Book book = Book.createBook(bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity(),
                bookForm.getAuthor(), bookForm.getIsbn());

        log.info("book 생성완료");
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String ListItem(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        log.info("ListBook");
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String EditItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book findItem = (Book) itemService.findOne(itemId);
        BookForm bookForm = new BookForm(findItem.getName(), findItem.getPrice(), findItem.getStockQuantity(),
                findItem.getAuthor(), findItem.getIsbn());

        model.addAttribute("form", bookForm);
        log.info("Book 업데이트 시작");
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String EditItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm bookForm) {
        itemService.updateItem(itemId, bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());
        return "redirect:/items";
    }
}
````
BookForm
````
@Getter
@Setter
@NoArgsConstructor
public class BookForm {

    private String id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    public BookForm(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
````
OrderController
````
@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        log.info("Order 생성");
        return "order/orderForm";
    }

    @PostMapping("/order")
    public String create(@RequestParam("memberId") Long memberId,
                         @RequestParam("itemId") Long itemId,
                         @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);
        log.info("Order 생성완료");
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String ListOrder(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        log.info("ListOrder");
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        log.info("Order 취소완료");
        return "redirect:/orders";
    }
}
````