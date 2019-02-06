package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.dtos.ItemDTO;
import com.holykiwi.checkouttotal.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;


@Service
public class BasketServiceImpl implements BasketService{

    private ItemService itemService;
    private BigDecimal total = BigDecimal.ZERO;
    private Map<String, Integer> eachesItems = new HashMap<>();


    @Autowired
    public void setItemService(ItemService itemService)
    {
        this.itemService = itemService;
    }


    @Override
    public BigDecimal addItemToOrder(String itemName) throws ItemNotFoundException
    {
            ItemDTO item = itemService.getItem(itemName);
            return (calculateTotalAfterEachesAddition(item)).setScale(2, RoundingMode.HALF_UP);
    }


    private BigDecimal calculateTotalAfterEachesAddition(ItemDTO item)
    {
        BigDecimal currentOrderLineTotal;
        if(eachesItems.containsKey(item.getName()))
        {
            currentOrderLineTotal = calculateOrderLineTotal(item.getName(), item.getPrice());
            eachesItems.put(item.getName(), eachesItems.get(item.getName()) + 1 );
        }
        else{
            currentOrderLineTotal = BigDecimal.ZERO;
            eachesItems.put(item.getName(), 1);
        }

        BigDecimal newOrderLineTotal = calculateOrderLineTotal(item.getName(), item.getPrice());

        total = total.add(newOrderLineTotal.subtract(currentOrderLineTotal));
        return total;
    }


    private BigDecimal calculateOrderLineTotal(String itemName, BigDecimal price)
    {
        return price.multiply(BigDecimal.valueOf(eachesItems.get(itemName)));
    }
}
