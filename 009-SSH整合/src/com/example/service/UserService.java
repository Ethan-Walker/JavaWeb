package com.example.service;

import com.example.domain.User;

import java.util.List;

/**
 * Created by EthanWalker on 2017/8/20.
 */
public interface UserService {

    void registerUser(User user);

    List<User> findAllUser();
}
