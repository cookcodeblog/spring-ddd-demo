package cn.xdevops.domain.order.service;

import cn.xdevops.domain.order.Order;
import cn.xdevops.domain.order.Product;
import cn.xdevops.domain.order.repository.OrderRepository;
import cn.xdevops.exception.OrderNotFoundException;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Long createOrder(Product product) {
        Order order = new Order(product);
        return orderRepository.save(order);
    }

    @Override
    public void addProduct(Long id, Product product) {
        Order order = getOrder(id);
        order.addOrder(product);
        orderRepository.save(order);
    }

    @Override
    public void completeOrder(Long id) {
        Order order = getOrder(id);
        order.complete();
        orderRepository.save(order);
    }

    @Override
    public void deleteProduct(Long id, Long productId) {
        Order order = getOrder(id);
        order.removeOrderItem(productId);
        orderRepository.deleteOrderItem(order, productId);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
