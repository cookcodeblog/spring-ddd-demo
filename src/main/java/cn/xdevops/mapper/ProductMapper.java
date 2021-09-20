package cn.xdevops.mapper;

import cn.xdevops.application.dto.ProductDto;
import cn.xdevops.domain.order.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductDto productDto);
}
