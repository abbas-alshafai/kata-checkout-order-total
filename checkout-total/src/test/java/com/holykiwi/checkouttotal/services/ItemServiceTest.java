package com.holykiwi.checkouttotal.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

    @Autowired ItemService itemService;

    @Test
    public void whenAddingItemThenItCanBeReturned()
    {
        String itemName = "soup";
        String createdItemName = itemService.addItem(itemName);
        assertEquals(itemName, createdItemName);
    }
}
