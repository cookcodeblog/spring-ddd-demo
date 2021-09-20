package cn.xdevops.domain.order;

import cn.xdevops.exception.ProductNotFoundException;
import cn.xdevops.exception.ServiceException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {

    private Long id;
    private OrderStatus status;
    private BigDecimal price;
    private List<OrderItem> orderItems;


    public Order(Product product) {
        this.orderItems = new ArrayList<>(Collections.singletonList(new OrderItem(product)));
        this.status = OrderStatus.CREATED;
        this.price = product.getPrice();
    }
    
    public void complete() {
        validateState();
        this.status = OrderStatus.COMPLETED;
    }

    public void addOrder(Product product) {
        validateState();
        validateProduct(product);
        orderItems.add(new OrderItem(product));
        price = price.add(product.getPrice());
    }

    public void removeOrderItem(Long productId) {
        validateState();
        OrderItem orderItem = getOrderItem(productId);
        orderItems.remove(orderItem);
        price = price.subtract(orderItem.getPrice());
    }

    private OrderItem getOrderItem(Long productId) {
        return orderItems.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    private void validateState() {
        if (OrderStatus.COMPLETED.equals(status)) {
            throw new ServiceException("The order is in completed state.");
        }
    }

    private void validateProduct(Product product) {
        if (product == null) {
            throw new ServiceException("The product can not be null.");
        }
    }

}
