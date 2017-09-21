package org.web;

import org.entity.Customer;
import org.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;


public class CustomerAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Customer customer;
	
	private CustomerService customerService;	
	
	public String queryCustomerById() {
		customer = customerService.findCustomerById(id);
		System.out.println(customer);
		return SUCCESS;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	
}
