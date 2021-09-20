package cn.xdevops.mapper;

import cn.xdevops.domain.order.OrderItem;
import cn.xdevops.infrastructure.repository.jpa.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "orderId", target = "orderId")
    OrderItemEntity toOrderItemEntity(OrderItem orderItem, Long orderId);

    OrderItem toOrderItem(OrderItemEntity orderItemEntity);
}
