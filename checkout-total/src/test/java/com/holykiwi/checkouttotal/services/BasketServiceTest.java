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
public class BasketServiceTest {

    @Autowired BasketService basketService;
    @Autowired ItemService itemService;

    private final String ITEM1_NAME = "soup";
    private final BigDecimal ITEM1_PRICE = new BigDecimal("1.89");

    private final String ITEM2_NAME = "lean ground beef";
    private final BigDecimal ITEM2_PRICE = new BigDecimal("5.99");


    @Before
    public void setUp()
    {
        itemService.deleteAll();

        itemService.addItem(ITEM1_NAME, ITEM1_PRICE);
        itemService.addItem(ITEM2_NAME, ITEM2_PRICE);
    }


    @Test
    public void whenAddingOneItemToOrderGetBackItsPrice() throws ItemNotFoundException
    {
        BigDecimal total = basketService.addItemToOrder(ITEM1_NAME);
        assertEquals(ITEM1_PRICE, total);
    }

}
