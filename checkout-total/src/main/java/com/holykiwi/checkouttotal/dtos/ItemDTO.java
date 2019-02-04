package com.holykiwi.checkouttotal.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemDTO {
    private String name;
    private BigDecimal price;
}
