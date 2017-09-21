package com.example.dao;

import com.example.domain.User;

import java.util.List;

/**
 * Created by EthanWalker on 2017/8/20.
 */
public interface UserDao {
    void save(User user);
    List<User> findAll();
}
