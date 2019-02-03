package com.holykiwi.checkouttotal.services;

import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService{
    @Override
    public String addItem(String name) {
        return name;
    }
}
