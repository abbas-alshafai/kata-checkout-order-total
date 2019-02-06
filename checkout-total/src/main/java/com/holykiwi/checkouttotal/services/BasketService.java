package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.exceptions.ItemNotFoundException;

import java.math.BigDecimal;

public interface BasketService {
    BigDecimal addItemToOrder(String itemName) throws ItemNotFoundException;
}
