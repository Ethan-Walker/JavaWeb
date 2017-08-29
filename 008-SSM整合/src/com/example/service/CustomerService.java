package com.example.service;

import com.example.pojo.QueryVo;
import com.example.pojo.Customer;
import com.example.utils.Page;

import java.util.List;

/**
 * Created by EthanWalker on 2017/8/26.
 */
public interface CustomerService {
    Page<Customer> queryAll();
    Page<Customer> queryByQueryVo(QueryVo queryVo);
    Integer countByQueryVo(QueryVo queryVo);

    Customer queryCustomerById(Integer id);

    void updateCustomer(Customer customer);

    void deleteCustomerById(Integer id);
}
