package com.holykiwi.checkouttotal;

import com.holykiwi.checkouttotal.daos.ItemDAO;
import com.holykiwi.checkouttotal.services.BasketService;
import com.holykiwi.checkouttotal.services.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckoutTotalApplicationTests {

	@Autowired private ItemService itemService;
	@Autowired private ItemDAO itemDAO;
	@Autowired private BasketService basketService;

	@Test
	public void whenSpringContextLoadsThenNoNullBeans() {
		assertNotNull(itemService);
		assertNotNull(itemDAO);
		assertNotNull(basketService);
	}

}

