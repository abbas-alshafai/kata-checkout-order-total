package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.dtos.ItemDTO;
import com.holykiwi.checkouttotal.exceptions.ItemNotFoundException;

import java.math.BigDecimal;

public interface ItemService {
    String addItem(String name, BigDecimal price);
    ItemDTO getItem(String itemName) throws ItemNotFoundException;
    void delete(ItemDTO item) throws ItemNotFoundException;
    void delete(String itemName) throws ItemNotFoundException;
    String addItem(String item1_name, BigDecimal item1_price, boolean b);
    void deleteAll();
}
