package cn.xdevops.infrastructure.repository.jpa;

import cn.xdevops.domain.order.Order;
import cn.xdevops.domain.order.OrderItem;
import cn.xdevops.domain.order.repository.OrderRepository;
import cn.xdevops.infrastructure.repository.jpa.entity.OrderEntity;
import cn.xdevops.infrastructure.repository.jpa.entity.OrderItemEntity;
import cn.xdevops.mapper.OrderItemMapper;
import cn.xdevops.mapper.OrderMapper;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaOrderRepository implements OrderRepository {

    private final SpringDataJpaOrderRepository orderRepository;
    private final SpringDataJpaOrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;


    public JpaOrderRepository(SpringDataJpaOrderRepository orderRepository, SpringDataJpaOrderItemRepository orderItemRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public Optional<Order> findById(Long id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if (orderEntityOptional.isPresent()) {
            OrderEntity orderEntity = orderEntityOptional.get();
            Order order = orderMapper.toOrderFromEntity(orderEntity);

            // load order items
            List<OrderItemEntity> orderItemEntityList = orderItemRepository.findByOrderId(orderEntity.getId());
            List<OrderItem> orderItems = orderItemEntityList.stream()
                    .map(orderItemMapper::toOrderItem)
                    .collect(Collectors.toList());

            order.setOrderItems(orderItems);
            return Optional.of(order);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Long save(Order order) {

        // save orders
        OrderEntity orderEntity = orderRepository.save(orderMapper.toOrderEntity(order));

        // save order items
        List<OrderItem> orderItems = order.getOrderItems();
        if (orderItems != null && !orderItems.isEmpty()) {
            List<OrderItemEntity> orderItemEntityList = orderItems.stream()
                    .map(orderItem -> orderItemMapper.toOrderItemEntity(orderItem, orderEntity.getId())).collect(Collectors.toList());
            orderItemEntityList.forEach(orderItemRepository::save);
        }

        return orderEntity.getId();
    }

    @Override
    @Transactional
    public void deleteOrderItem(Order order, Long productId) {
        // update order
        orderRepository.save(orderMapper.toOrderEntity(order));

        // delete order item by product id
        orderItemRepository.findByOrderId(order.getId()).stream()
                .filter(itemEntity -> productId.equals(itemEntity.getProductId()))
                .forEach(orderItemRepository::delete);
    }

}
