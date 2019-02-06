package com.holykiwi.checkouttotal.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemDTO {
    private String name;

    /*
    This price will cover both: "eaches" items and by weight items. If it is by weight, then the rate will be
    per 1 pound.
        ex1: price is 2.99 and it is by weight, then it will be considered as $2.99 per pound
        ex2: price is 1.98 and it is by weight, then it will be considered as $1.98 per pound
     */
    private BigDecimal price;

    /*
    This flag is to distinguish between eaches (quantity) type, and between
    weight type. I decided to go with isByWeight (rather than isEaches or isByQuantity because the
    default value for boolean if not initialized is false. This means that by default, it will be considered as
    eaches.
     */
    private boolean isByWeight;
}
