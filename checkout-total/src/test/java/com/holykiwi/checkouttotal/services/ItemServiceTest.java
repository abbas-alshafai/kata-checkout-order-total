package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.dtos.ItemDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

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
    public void whenAddingTwoItemsWithSameNameThenGetBackTheLatestName()
    {
        String createdItemName = itemService.addItem(ITEM1_NAME, ITEM1_PRICE);
        String laterItem = itemService.addItem(ITEM1_NAME, ITEM1_PRICE);
        assertEquals(ITEM1_NAME, laterItem);
    }

    @Test
    public void whenAddingItemThenItCanBeRetrieved()
    {
        String createdItemName = itemService.addItem(ITEM1_NAME, ITEM1_PRICE);
        ItemDTO storedItem = itemService.getItem(createdItemName);
        assertEquals(item1.getName(), storedItem.getName());
        assertEquals(item1.getPrice(), storedItem.getPrice());
    }


}
