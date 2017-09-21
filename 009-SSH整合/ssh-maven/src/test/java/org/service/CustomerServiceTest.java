package org.service;

import static org.junit.Assert.*;

import org.entity.Customer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerServiceTest {

	@Test
	public void test() {
//		fail("Not yet implemented");
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		CustomerService customerService = applicationContext.getBean(CustomerService.class,"customerService");
		Customer customer = customerService.findCustomerById(1L);
		System.out.println(customer);
	}

}
