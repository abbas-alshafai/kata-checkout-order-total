package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.dtos.ItemDTO;
import com.holykiwi.checkouttotal.exceptions.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Map<String, BigDecimal> weightedItems = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);


    @Autowired
    public BasketServiceImpl(ItemService itemService)
    {
        this.itemService = itemService;
    }


    @Override
    public BigDecimal addItemToOrder(String itemName, int quantity, BigDecimal weight) throws ItemNotFoundException
    {
        ItemDTO item = itemService.getItem(itemName);
        return addItemToOrder(item, quantity, weight);
    }


    @Override
    public BigDecimal addItemToOrder(ItemDTO item, int quantity, BigDecimal weight)
    {
        return (item.isByWeight() ?
                calculateTotalAfterWeightedAddition(item, weight) : calculateTotalAfterEachesAddition(item, quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal addItemToOrder(String itemName, int quantity) throws ItemNotFoundException
    {
        ItemDTO item = itemService.getItem(itemName);

        if(item.isByWeight())
            return addItemToOrder(item, 0, new BigDecimal(quantity));

        return addItemToOrder(item, quantity, null);
    }


    @Override
    public BigDecimal addItemToOrder(String itemName, BigDecimal weight) throws ItemNotFoundException
    {
        ItemDTO item = itemService.getItem(itemName);

        if(item.isByWeight())
            return addItemToOrder(item, 0, weight);

        LOGGER.warn("You have entered a BigDecimal value for an eaches item. It will be converted to int.");
        return addItemToOrder(itemName, weight.intValue(), null);
    }


    @Override
    public BigDecimal addItemToOrder(String itemName) throws ItemNotFoundException
    {
            ItemDTO item = itemService.getItem(itemName);
            return (item.isByWeight() ?
                    calculateTotalAfterWeightedAddition(item, BigDecimal.ONE) :
                    calculateTotalAfterEachesAddition(item, 1))
                    .setScale(2, RoundingMode.HALF_UP);
    }


    private BigDecimal calculateTotalAfterWeightedAddition(ItemDTO item, BigDecimal weight)
    {
        if(weight == null || weight.compareTo(BigDecimal.ZERO) <= 0)
        {
            LOGGER.error("The entered weight should not be {}. It should be more than zero.", weight);
            return total;
        }

        BigDecimal currentOrderLineTotal = calculateOrderLineTotal(item);

        if(weightedItems.containsKey(item.getName()))
            weightedItems.put(item.getName(), weightedItems.get(item.getName()).add(weight));
        else
            weightedItems.put(item.getName(), weight);


        BigDecimal newOrderLineTotal = calculateWeightedOrderLineTotal(item.getName(), item.getPrice());

        total = total.add(newOrderLineTotal.subtract(currentOrderLineTotal));
        return total;
    }


    private BigDecimal calculateTotalAfterEachesAddition(ItemDTO item, int quantity)
    {
        if(quantity <= 0) {
            LOGGER.error("Eaches item quantity should not be {}. It should be bigger than zero.", quantity);
            return total;
        }

        BigDecimal currentOrderLineTotal;
        if(eachesItems.containsKey(item.getName()))
        {
            currentOrderLineTotal = calculateEachesOrderLineTotal(item.getName(), item.getPrice());
            eachesItems.put(item.getName(), eachesItems.get(item.getName()) + quantity );
        }
        else{
            currentOrderLineTotal = BigDecimal.ZERO;
            eachesItems.put(item.getName(), quantity);
        }

        BigDecimal newOrderLineTotal = calculateEachesOrderLineTotal(item.getName(), item.getPrice());

        total = total.add(newOrderLineTotal.subtract(currentOrderLineTotal));
        return total;
    }


    private BigDecimal calculateEachesOrderLineTotal(String itemName, BigDecimal price)
    {
        return price.multiply(BigDecimal.valueOf(eachesItems.get(itemName)));
    }


    private BigDecimal calculateWeightedOrderLineTotal(String itemName, BigDecimal price)
    {
        return price.multiply(weightedItems.get(itemName));
    }


    @Override
    public BigDecimal emptyBasket()
    {
        eachesItems.clear();
        weightedItems.clear();
        total = BigDecimal.ZERO;
        return total;
    }

    @Override
    public BigDecimal removeItemFromOrder(String itemName) throws ItemNotFoundException
    {
        ItemDTO item = itemService.getItem(itemName);
        removeWeightedItem(itemName, item.getPrice());
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateOrderLineTotal(ItemDTO item)
    {
        if(item.isByWeight())
            return weightedItems.containsKey(item.getName()) ?
                    weightedItems.get(item.getName()).multiply(item.getPrice()): BigDecimal.ZERO;
        else
            return eachesItems.containsKey(item.getName()) ?
                    item.getPrice().multiply(BigDecimal.valueOf(eachesItems.get(item.getName()))) : BigDecimal.ZERO;
    }


    private void removeWeightedItem(String itemName, BigDecimal price)
    {
        BigDecimal currentOrderLineTotal = calculateWeightedOrderLineTotal(itemName, price);


    }
}


