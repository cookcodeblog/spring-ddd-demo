package cn.xdevops.domain.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Product {

    private Long id;
    private BigDecimal price;
    private String name;

}
