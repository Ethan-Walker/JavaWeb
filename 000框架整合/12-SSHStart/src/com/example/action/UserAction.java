package com.example.action;

import com.example.domain.User;
import com.example.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * Created by EthanWalker on 2017/8/23.
 */
public class UserAction extends ActionSupport implements ModelDriven<User>{
    private UserService userService;
    private User user = new User();
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getModel() {
        return user;
    }

    public String register(){
        this.userService.registerUser(user);
        return "success";
    }

}
