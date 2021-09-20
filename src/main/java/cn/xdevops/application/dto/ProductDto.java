package cn.xdevops.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotNull(message = "Product id is mandatory")
    private Long id;
    @NotNull(message = "Price is mandatory")
    private BigDecimal price;
    @NotBlank(message = "Product name is mandatory")
    private String name;
}
