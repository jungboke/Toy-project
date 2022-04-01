package com.example.baekshopV2.api.dto.order.response;

import com.example.baekshopV2.domain.Order;
import com.example.baekshopV2.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String id;
    private String memberId;
    private String itemId;
    private int count;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.memberId = order.getMember().getId();
        this.itemId = order.getOrderItems().get(0).getId();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
    }

    public static OrderResponseDTO create(String id, String memberId, String itemId,
                                          LocalDateTime orderDate, OrderStatus orderStatus) {
        OrderResponseDTO orderDTO = new OrderResponseDTO();
        orderDTO.id = id;
        orderDTO.memberId = memberId;
        orderDTO.itemId = itemId;
        orderDTO.orderDate = orderDate;
        orderDTO.orderStatus = orderStatus;

        return orderDTO;
    }
}
