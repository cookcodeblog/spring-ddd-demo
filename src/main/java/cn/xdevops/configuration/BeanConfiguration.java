package cn.xdevops.configuration;

import cn.xdevops.domain.order.repository.OrderRepository;
import cn.xdevops.domain.order.service.OrderService;
import cn.xdevops.domain.order.service.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    OrderService orderService(OrderRepository orderRepository) {
        return new OrderServiceImpl(orderRepository);
    }
}
