package cn.xdevops.application.dto;

import cn.xdevops.domain.order.OrderItem;
import cn.xdevops.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private OrderStatus status;
    private BigDecimal price;
    private List<OrderItem> orderItems;
}
