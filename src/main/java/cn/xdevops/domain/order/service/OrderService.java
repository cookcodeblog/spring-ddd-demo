package cn.xdevops.domain.order.service;

import cn.xdevops.domain.order.Order;
import cn.xdevops.domain.order.Product;

public interface OrderService {

    Long createOrder(Product product);

    void addProduct(Long id, Product product);

    void completeOrder(Long id);

    void deleteProduct(Long id, Long productId);

    Order getOrder(Long id);

}
