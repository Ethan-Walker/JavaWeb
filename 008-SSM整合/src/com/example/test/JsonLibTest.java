package com.example.test;

import com.example.dao.CustomerMapper;
import com.example.pojo.Customer;
import com.example.service.CustomerService;
import com.example.service.CustomerServiceImpl;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.junit.Test;

/**
 * Created by EthanWalker on 2017/8/28.
 */
public class JsonLibTest {

    @Test
    public void objectToJsonString() {
        Customer customer = new Customer();
        customer.setCust_address("地址");
        customer.setCust_create_id(11L);
        customer.setCust_industry("行业");
        JSONObject jsonObject = JSONObject.fromObject(customer);
        System.out.println(jsonObject.toString());
    }

    @Test
    public void xml2json() {
        String s = "<student><name id='n1'>xiapi</name> <sex class='s1'>男</sex> <age>20</age>  </student>";
        XMLSerializer x = new XMLSerializer();
        JSON json = x.read(s);
        System.out.println("XmlToJson");
        System.out.println(json.toString());
    }
    @Test
    public void jsonStrToBean(){
        String customerStr = "{'cust_name':'ethan'}";
        JSONObject jsonObject = JSONObject.fromObject(customerStr);
        Customer custoemr = (Customer) JSONObject.toBean(jsonObject, Customer.class);
        System.out.println(custoemr);
    }

    /**
     * fastjson
     */
    @Test
    public void testFastJson(){
        Customer customer = new Customer();
        customer.setCust_address("地址");
        customer.setCust_create_id(11L);
        customer.setCust_industry("行业");
        String s = com.alibaba.fastjson.JSON.toJSONString(customer);
        System.out.println(s);
    }

    @Test
    public void testStringToBean(){
        String str ="{\"cust_address\":\"地址\",\"cust_create_id\":11,\"cust_industry\":\"行业\"}";
        Customer customer = com.alibaba.fastjson.JSON.parseObject(str,Customer.class);
        System.out.println(customer);
    }
}
