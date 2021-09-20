package cn.xdevops.mapper;

import cn.xdevops.application.dto.OrderDto;
import cn.xdevops.domain.order.Order;
import cn.xdevops.infrastructure.repository.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toOrderDto(Order order);

    OrderEntity toOrderEntity(Order order);

    Order toOrderFromEntity(OrderEntity orderEntity);
}
