package com.example.test;
import com.example.domain.User;
import com.example.service.AccountService;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by EthanWalker on 2017/8/20.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DemoTest {

    @Autowired
    private UserService userService ;

    @Autowired
    private AccountService accountService;

    @Test
    public void test(){
        User user = new User();
        user.setUsername("傻逼");
        user.setPassword("123456789");
        userService.registerUser(user);
    }

    @Test
    public void a(){
       List<User> users=  userService.findAllUser();
        for(User user:users){
            System.out.println(user);
        }
    }
    @Test
    public void b(){
        accountService.transfer("jack","rose",100);
    }

}
