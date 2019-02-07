package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.dtos.ItemDTO;
import com.holykiwi.checkouttotal.exceptions.ItemNotFoundException;

import java.math.BigDecimal;

public interface BasketService {
    BigDecimal addItemToOrder(ItemDTO item, int quantity, BigDecimal weight) throws ItemNotFoundException;
    BigDecimal addItemToOrder(String itemName, int quantity, BigDecimal weight) throws ItemNotFoundException;
    BigDecimal addItemToOrder(String itemName, int quantity) throws ItemNotFoundException;
    BigDecimal addItemToOrder(String itemName, BigDecimal weight) throws ItemNotFoundException;
    BigDecimal addItemToOrder(String itemName) throws ItemNotFoundException;

    BigDecimal emptyBasket();

    BigDecimal removeItemFromOrder(String itemName) throws ItemNotFoundException;
}
