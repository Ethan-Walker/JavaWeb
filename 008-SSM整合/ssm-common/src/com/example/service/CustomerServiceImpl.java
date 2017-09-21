package com.example.service;

import com.example.dao.CustomerMapper;
import com.example.pojo.QueryVo;
import com.example.pojo.Customer;
import com.example.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by EthanWalker on 2017/8/26.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Page<Customer> queryAll() {
        List<Customer> customers = customerMapper.queryAllCustomer();
        Page<Customer> page = new Page<>();
        page.setTotal(customers.size());
        page.setSize(10);
        page.setRows(customers);
        page.setPage(1);
        return page;
    }

    @Override
    public Customer queryCustomerById(Integer id) {
        return customerMapper.queryCustomerById(id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerMapper.updateCustomer(customer);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customerMapper.deleteCustomerById(id);
    }

    @Override
    public Page<Customer> queryByQueryVo(QueryVo queryVo) {

        queryVo.setStartRow((queryVo.getPage()-1)*queryVo.getRows());
        List<Customer> customers = customerMapper.queryByQueryVo(queryVo);
        Integer total = countByQueryVo(queryVo);
        Page<Customer> customerPage=  new Page<>();
        customerPage.setTotal(total);
        customerPage.setPage(queryVo.getPage());
        customerPage.setRows(customers);
        customerPage.setSize(queryVo.getRows());
        return customerPage;
    }

    @Override
    public Integer countByQueryVo(QueryVo queryVo) {
        return customerMapper.countByQueryVo(queryVo);
    }
}
