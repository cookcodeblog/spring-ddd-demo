package cn.xdevops.application.rest;


import cn.xdevops.application.dto.OrderDto;
import cn.xdevops.application.dto.ProductDto;
import cn.xdevops.domain.order.service.OrderService;
import cn.xdevops.mapper.OrderMapper;
import cn.xdevops.mapper.ProductMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, ProductMapper productMapper, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.productMapper = productMapper;
        this.orderMapper = orderMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDto createOrder(@Valid @RequestBody ProductDto productDto) {
        Long orderId = orderService.createOrder(productMapper.toProduct(productDto));
        return orderMapper.toOrderDto(orderService.getOrder(orderId));
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id) {
        return orderMapper.toOrderDto(orderService.getOrder(id));
    }

    @PostMapping("/{id}/products")
    public OrderDto addProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        orderService.addProduct(id, productMapper.toProduct(productDto));
        return orderMapper.toOrderDto(orderService.getOrder(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/products")
    public void deleteProduct(@PathVariable Long id, @RequestParam Long productId) {
        orderService.deleteProduct(id, productId);
    }

    @PostMapping("/{id}/complete")
    public OrderDto completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
        return orderMapper.toOrderDto(orderService.getOrder(id));
    }


}
