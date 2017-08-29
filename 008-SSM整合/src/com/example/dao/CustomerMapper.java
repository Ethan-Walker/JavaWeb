package com.example.dao;

import com.example.pojo.Customer;
import com.example.pojo.QueryVo;
import com.example.utils.Page;

import java.util.List;

/**
 * Created by EthanWalker on 2017/8/27.
 */
public interface CustomerMapper {
    List<Customer> queryAllCustomer();
    List<Customer> queryByQueryVo(QueryVo queryVo);
    Integer countByQueryVo(QueryVo queryVo);

    Customer queryCustomerById(Integer id);

    void updateCustomer(Customer customer);

    void deleteCustomerById(Integer id);
}
