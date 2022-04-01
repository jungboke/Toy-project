package com.example.baekshopV2.api.controller;

import com.example.baekshopV2.api.dto.order.response.OrderResponseDTO;
import com.example.baekshopV2.domain.Order;
import com.example.baekshopV2.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    // C
    @PostMapping("/v1/orders")
    public ResponseEntity<?> saveOrderV1(@RequestParam("memberId") @NotBlank String memberId,
                                         @RequestParam("itemId") @NotBlank String itemId,
                                         @RequestParam("count") @NotNull Integer count) {
        Order order = orderService.order(memberId, itemId, count);
        OrderResponseDTO dto = OrderResponseDTO.create(order.getId(),order.getMember().getId(),
                order.getOrderItems().get(0).getId(),order.getOrderDate(), order.getStatus());
        log.info("주문 완료");

        return ResponseEntity.ok(dto);
    }

    // R
    @GetMapping("/v1/orders")
    public ResponseEntity<?> findOrderV1() {
        List<Order> orders = orderService.findOrders();
        List<OrderResponseDTO> dtos = orders.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
        log.info("주문 조회 완료");

        return ResponseEntity.ok(dtos);
    }

    // D
    @DeleteMapping("/v1/orders/cancel/{id}")
    public ResponseEntity<?> cancelOrderV1(@PathVariable("id") String id) {
        orderService.deleteOrder(id);
        log.info("주문 삭제 완료");

        return ResponseEntity.ok(id);
    }
}
