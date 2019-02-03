package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.dtos.ItemDTO;
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

    private ItemDTO item1;
    private final String ITEM_NAME1 = "soup";

    @Before
    public void setUp()
    {
        item1 = ItemDTO.builder()
                .name(ITEM_NAME1)
                .build();
    }

    @Test
    public void whenAddingItemThenGetBackItsName()
    {
        String createdItemName = itemService.addItem(ITEM_NAME1);
        assertEquals(item1.getName(), createdItemName);
    }



}
