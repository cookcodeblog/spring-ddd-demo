package cn.xdevops.domain.order.repository;

import cn.xdevops.domain.order.Order;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long id);

    Long save(Order order);

    void deleteOrderItem(Order order, Long productId);
}
