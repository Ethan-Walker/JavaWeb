package org.dao;

import org.entity.Customer;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

public class CustomerDaoImpl extends HibernateDaoSupport{
	public Customer findCustomerById(Long id) {
		return getHibernateTemplate().get(Customer.class, id);
	}
}
