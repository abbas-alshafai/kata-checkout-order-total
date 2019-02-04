package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.daos.ItemDAO;
import com.holykiwi.checkouttotal.dtos.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemServiceImpl implements ItemService{

    private final ItemDAO itemDAO;

    @Autowired
    public ItemServiceImpl(ItemDAO itemDAO)
    {
        this.itemDAO = itemDAO;
    }

    @Override
    public String addItem(String name, BigDecimal price)
    {
        ItemDTO item = ItemDTO.builder()
                .name(name)
                .price(price)
                .build();
        return itemDAO.save(item);
    }


    @Override
    public ItemDTO getItem(String itemName)
    {
        return itemDAO.findByName(itemName);
    }
}
