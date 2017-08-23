package com.example.dao.impl;

import com.example.dao.UserDao;
import com.example.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by EthanWalker on 2017/8/20.
 */
public class UserDaoImpl  implements UserDao{

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public List<User> findAll() {
        return getSession().createQuery("select u from User u").list();
    }

    @Override
    public void save(User user) {
        getSession().save(user);
    }
}
