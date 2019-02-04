package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.dtos.ItemDTO;

import java.math.BigDecimal;

public interface ItemService {
    String addItem(String name, BigDecimal ITEM1_PRICE);
    ItemDTO getItem(String createdItemName);
}
