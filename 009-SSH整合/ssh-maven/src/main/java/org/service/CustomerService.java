package org.service;

import org.dao.CustomerDaoImpl;
import org.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class CustomerService {

	private CustomerDaoImpl customerDao;
	
	public Customer findCustomerById(Long id) {
		return customerDao.findCustomerById(id);
	}

	public CustomerDaoImpl getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDaoImpl customerDao) {
		this.customerDao = customerDao;
	}
}
