package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.daos.ItemDAO;
import com.holykiwi.checkouttotal.dtos.ItemDTO;
import com.holykiwi.checkouttotal.exceptions.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemServiceImpl implements ItemService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);
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
    public ItemDTO getItem(String itemName) throws ItemNotFoundException {
        return itemDAO.findByName(itemName);
    }

    @Override
    public void delete(ItemDTO item) throws ItemNotFoundException {
        delete(item.getName());
    }

    @Override
    public void delete(String itemName) throws ItemNotFoundException {
        itemDAO.delete(itemName);
    }

    @Override
    public String addItem(String name, BigDecimal price, boolean isByWeight)
    {
        ItemDTO item = ItemDTO.builder()
                .name(name)
                .price(price)
                .isByWeight(false)
                .build();
        return itemDAO.save(item);
    }

    @Override
    public void deleteAll() {
        itemDAO.deleteAll();
    }
}
