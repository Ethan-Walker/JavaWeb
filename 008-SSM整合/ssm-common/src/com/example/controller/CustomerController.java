package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.pojo.BaseDict;
import com.example.pojo.Customer;
import com.example.pojo.QueryVo;
import com.example.service.BaseDictService;
import com.example.service.CustomerService;
import com.example.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by EthanWalker on 2017/8/26.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BaseDictService baseDictService;

    // 客户来源
    @Value("${CUSTOMER_FROM_TYPE}")
    private String CUSTOMER_FROM_TYPE;
    // 客户行业
    @Value("${CUSTOMER_INDUSTRY_TYPE}")
    private String CUSTOMER_INDUSTRY_TYPE;
    // 客户级别
    @Value("${CUSTOMER_LEVEL_TYPE}")
    private String CUSTOMER_LEVEL_TYPE;

    @RequestMapping(value = "/list.action", method = RequestMethod.GET)
    public String customerList(Model model, HttpSession session, QueryVo queryVo) {
        List<BaseDict> fromType = baseDictService.queryBaseDictsByCode(CUSTOMER_FROM_TYPE);
        List<BaseDict> industryType = baseDictService.queryBaseDictsByCode(CUSTOMER_INDUSTRY_TYPE);
        List<BaseDict> levelType = this.baseDictService.queryBaseDictsByCode(CUSTOMER_LEVEL_TYPE);

        Page<Customer> page = customerService.queryByQueryVo(queryVo);
        model.addAttribute("fromType", fromType);
        model.addAttribute("industryType", industryType);
        model.addAttribute("levelType", levelType);
        model.addAttribute("page", page);
        return "customer";
    }

    @RequestMapping(value = "/list.action", method = RequestMethod.POST)
    public String postCustomerList(Model model, QueryVo queryVo, HttpSession session) {

        if (session.getAttribute("token").equals(queryVo.getToken())) {
            session.removeAttribute("token");
            List<BaseDict> fromType = baseDictService.queryBaseDictsByCode(CUSTOMER_FROM_TYPE);
            List<BaseDict> industryType = baseDictService.queryBaseDictsByCode(CUSTOMER_INDUSTRY_TYPE);
            List<BaseDict> levelType = this.baseDictService.queryBaseDictsByCode(CUSTOMER_LEVEL_TYPE);
            System.out.println(queryVo);
            Page<Customer> customerPage = customerService.queryByQueryVo(queryVo);
            model.addAttribute("fromType", fromType);
            model.addAttribute("industryType", industryType);
            model.addAttribute("levelType", levelType);
            model.addAttribute("page", customerPage);
            if (queryVo.getCust_level() != null && !"".equals(queryVo.getCust_level())) {
                model.addAttribute("custLevel", queryVo.getCust_level());
            }
            if (queryVo.getCust_industry() != null && !"".equals(queryVo.getCust_industry())) {
                model.addAttribute("custIndustry", queryVo.getCust_industry());
            }
            if (queryVo.getCust_name() != null && !"".equals(queryVo.getCust_name())) {
                model.addAttribute("custName", queryVo.getCust_name());
            }
            if (queryVo.getCust_source() != null && !"".equals(queryVo.getCust_source())) {
                model.addAttribute("custSource", queryVo.getCust_source());
            }

            return "customer";
        } else {
            return "repeat";
        }
    }

    @RequestMapping(value = "/edit")
    public @ResponseBody
    Customer getCustomer(Integer id) {
        Customer customer = customerService.queryCustomerById(id);
        return customer;
    }

    @RequestMapping(value = "/update")
    public @ResponseBody
    String updateCustomer(Customer customer) {
        customerService.updateCustomer(customer);
        return "ok";
    }

    @RequestMapping(value = "/delete")
    public @ResponseBody
    String deleteCustomer(Integer id) {
        customerService.deleteCustomerById(id);
        return "ok";
    }

    /*注解跨域*/
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/ajax")
    public @ResponseBody
    Customer ajax(Integer id,String password ,HttpServletResponse response) {
        System.out.println(password);
        Customer customer = customerService.queryCustomerById(id);
/*        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");*/
        return customer;
    }

    @RequestMapping("/jsonp")
    public @ResponseBody
    String jsonp(Integer id, String callback, HttpServletResponse response) throws UnsupportedEncodingException {
        Customer customer = customerService.queryCustomerById(id);
        String s = JSON.toJSONString(customer);
        System.out.println(s);
        String combinedStr = callback + '(' + s + ')';
        System.out.println(combinedStr);
        response.setContentType("text/html;charset=utf-8");
        return combinedStr;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setBaseDictService(BaseDictService baseDictService) {
        this.baseDictService = baseDictService;
    }
}
