package com.holykiwi.checkouttotal;

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

	@Autowired
	ItemService itemService;

	@Test
	public void whenSpringContextLoadsThenNoNullBeans() {
		assertNotNull(itemService);
	}

}

