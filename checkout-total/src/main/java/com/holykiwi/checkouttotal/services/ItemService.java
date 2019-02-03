package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.dtos.ItemDTO;

public interface ItemService {
    String addItem(String name);
    ItemDTO getItem(String createdItemName);
}
