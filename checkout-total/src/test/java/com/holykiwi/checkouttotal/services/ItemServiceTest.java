package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.dtos.ItemDTO;
import com.holykiwi.checkouttotal.exceptions.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

    @Autowired ItemService itemService;

    private ItemDTO item1;
    private final String ITEM1_NAME = "soup";
    private final BigDecimal ITEM1_PRICE = new BigDecimal("1.89");


    @Before
    public void setUp()
    {
        item1 = ItemDTO.builder()
                .name(ITEM1_NAME)
                .price(ITEM1_PRICE)
                .build();
    }


    @Test
    public void whenAddingItemThenGetBackItsName()
    {
        String createdItemName = itemService.addItem(ITEM1_NAME, ITEM1_PRICE);
        assertEquals(item1.getName(), createdItemName);
    }


    @Test
    public void whenAddingTwoItemsWithSameNameThenGetBackTheLatestName() throws ItemNotFoundException
    {
        itemService.addItem(ITEM1_NAME, ITEM1_PRICE);
        ItemDTO oldItem = itemService.getItem(ITEM1_NAME);

        BigDecimal newPrice = new BigDecimal("24.23");
        String newItemName = itemService.addItem(ITEM1_NAME, newPrice);
        ItemDTO storedItem = itemService.getItem(newItemName);

        assertNotEquals(newPrice, oldItem.getPrice());
        assertEquals(ITEM1_NAME, newItemName);
        assertEquals(ITEM1_NAME, storedItem.getName());
        assertEquals(newPrice, storedItem.getPrice());
    }


    @Test
    public void whenAddingItemThenItCanBeRetrieved() throws ItemNotFoundException
    {
        String createdItemName = itemService.addItem(ITEM1_NAME, ITEM1_PRICE);
        ItemDTO storedItem = itemService.getItem(createdItemName);
        assertEquals(item1.getName(), storedItem.getName());
        assertEquals(item1.getPrice(), storedItem.getPrice());
    }


    @Test(expected = ItemNotFoundException.class)
    public void whenQueryingNotExistingItemThrowException() throws ItemNotFoundException
    {
        itemService.getItem("not existing item");
    }


    @Test(expected = ItemNotFoundException.class)
    public void whenQueryingDeletedItemThrowException() throws ItemNotFoundException
    {
        String createdItemName = itemService.addItem(ITEM1_NAME, ITEM1_PRICE);
        itemService.delete(itemService.getItem(createdItemName));
        itemService.getItem(createdItemName);
    }


    @Test(expected = ItemNotFoundException.class)
    public void whenDeletingNotFoundItemThrowException() throws ItemNotFoundException
    {
        itemService.delete(itemService.getItem(ITEM1_NAME));
    }
}
