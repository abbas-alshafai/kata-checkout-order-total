package com.holykiwi.checkouttotal.services;

import com.holykiwi.checkouttotal.exceptions.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    private final String ITEM3_NAME = "Gala Apple";
    private final BigDecimal ITEM3_PRICE = new BigDecimal("0.893");

    private final String ITEM4_NAME = "Banana";
    private final BigDecimal ITEM4_PRICE = new BigDecimal("1.999");

    @Before
    public void setUp()
    {
        basketService.emptyBasket();
        itemService.deleteAll();

        itemService.addItem(ITEM1_NAME, ITEM1_PRICE);
        itemService.addItem(ITEM2_NAME, ITEM2_PRICE, true);

        itemService.addItem(ITEM3_NAME, ITEM3_PRICE);
        itemService.addItem(ITEM4_NAME, ITEM4_PRICE, true);
    }


    @Test
    public void whenAddingOneEachesItemToOrderGetBackItsPrice() throws ItemNotFoundException
    {
        BigDecimal total = basketService.addItemToOrder(ITEM1_NAME);
        assertEquals(ITEM1_PRICE, total);
        setUp();

        total = basketService.addItemToOrder(ITEM1_NAME, 1);
        assertEquals(ITEM1_PRICE, total);
        setUp();

        total = basketService.addItemToOrder(ITEM1_NAME, 1, null);
        assertEquals(ITEM1_PRICE, total);
    }


    @Test
    public void whenAddingZeroEachesItemToOrderThenNoTotalChange() throws ItemNotFoundException
    {
        BigDecimal total = basketService.addItemToOrder(ITEM1_NAME, 0, null);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), total);

        total = basketService.addItemToOrder(ITEM1_NAME, 1, null);
        assertEquals(ITEM1_PRICE, total);

        setUp();

        total = basketService.addItemToOrder(ITEM1_NAME, 0);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), total);

        total = basketService.addItemToOrder(ITEM1_NAME, 1);
        assertEquals(ITEM1_PRICE, total);
    }


    @Test
    public void whenAddingWeightedItemToOrderGetBackItsPrice() throws ItemNotFoundException
    {
        BigDecimal total = basketService.addItemToOrder(ITEM2_NAME);
        assertEquals(ITEM2_PRICE, total);
        setUp();

        total = basketService.addItemToOrder(ITEM2_NAME, BigDecimal.ONE);
        assertEquals(ITEM2_PRICE, total);
        setUp();

        total = basketService.addItemToOrder(ITEM2_NAME, 0, BigDecimal.ONE);
        assertEquals(ITEM2_PRICE, total);
    }


    @Test
    public void whenAddingWeightedItemWithIntWeightGetBackCorrectTotal() throws ItemNotFoundException
    {
        BigDecimal total = basketService.addItemToOrder(ITEM2_NAME, 2);
        assertEquals(ITEM2_PRICE.multiply(BigDecimal.valueOf(2)), total);
    }


    @Test
    public void whenAddingEachesItemWithBigDecimalQuantityGetBackCorrectTotal() throws ItemNotFoundException
    {
        BigDecimal total = basketService.addItemToOrder(ITEM1_NAME, new BigDecimal("2"));
        assertEquals(ITEM1_PRICE.multiply(BigDecimal.valueOf(2)), total);
    }


    @Test
    public void whenAddingWeightedItemWithNullWeightConsiderItOnePoundAPI() throws ItemNotFoundException
    {
        BigDecimal total = basketService.addItemToOrder(ITEM2_NAME, 0, null);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), total);
        setUp();

        total = basketService.addItemToOrder(ITEM2_NAME, null);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), total);
        setUp();

        total = basketService.addItemToOrder(ITEM2_NAME, null);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), total);
    }


    @Test
    public void whenAddingSameWeightedItemMultipleTimeGetCorrectTotal() throws ItemNotFoundException
    {
        BigDecimal total = basketService.addItemToOrder(ITEM4_NAME, 0, BigDecimal.ONE);
        assertEquals((new BigDecimal("2.00")).setScale(2, RoundingMode.HALF_UP), total);

        total = basketService.addItemToOrder(ITEM4_NAME, 0, BigDecimal.ZERO);
        assertEquals((new BigDecimal("2.00")).setScale(2, RoundingMode.HALF_UP), total);

        total = basketService.addItemToOrder(ITEM4_NAME, new BigDecimal("0.75"));
        assertEquals((new BigDecimal("3.50")).setScale(2, RoundingMode.HALF_UP), total);


        total = basketService.addItemToOrder(ITEM4_NAME, BigDecimal.ZERO);
        assertEquals((new BigDecimal("3.50")).setScale(2, RoundingMode.HALF_UP), total);
    }


    @Test
    public void whenDeletingNonAddedItemThenTotalShouldBeSame() throws ItemNotFoundException
    {
        BigDecimal total = basketService.removeItemFromOrder(ITEM1_NAME);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), total);

        basketService.addItemToOrder(ITEM2_NAME);
        total = basketService.removeItemFromOrder(ITEM1_NAME);
        assertEquals((ITEM2_PRICE).setScale(2, RoundingMode.HALF_UP), total);
    }


    @Test
    public void whenDeletingOneItemResetTotalToZero() throws ItemNotFoundException
    {
        basketService.addItemToOrder(ITEM2_NAME);
        BigDecimal total = basketService.removeItemFromOrder(ITEM2_NAME);
        assertEquals((BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP), total);

//        basketService.addItemToOrder(ITEM1_NAME);
//        basketService.addItemToOrder(ITEM3_NAME);
//        total = basketService.addItemToOrder(ITEM4_NAME);
//        assertEquals(ITEM1_PRICE.add(ITEM3_PRICE).add(ITEM4_PRICE).setScale(2, RoundingMode.HALF_UP), total);
    }

}
