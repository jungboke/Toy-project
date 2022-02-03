package me.jungboke.baekshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jungboke.baekshop.domain.Member;
import me.jungboke.baekshop.domain.Order;
import me.jungboke.baekshop.domain.item.Item;
import me.jungboke.baekshop.repository.OrderSearch;
import me.jungboke.baekshop.service.ItemService;
import me.jungboke.baekshop.service.MemberService;
import me.jungboke.baekshop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
