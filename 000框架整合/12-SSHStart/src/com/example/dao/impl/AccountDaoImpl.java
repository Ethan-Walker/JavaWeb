package com.example.dao.impl;

import com.example.dao.AccountDao;
import com.example.domain.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by EthanWalker on 2017/8/23.
 */
public class AccountDaoImpl implements AccountDao {
    private SessionFactory sessionFactory;

    protected Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }
    @Override
    public boolean push(String payee, Integer money){

        Account account= getSession().createQuery("select a from Account a where username = :name",Account.class).setParameter("name", payee).uniqueResult();
        if(account==null){
            System.out.println("收款人未找到");
            return false;
        }else{
            account.setMoney(account.getMoney()+money);
            return true;
        }

    }

    @Override
    public boolean pull(String payor, Integer money) {

        Account  account = getSession().createQuery("select a from Account a where username = :name ",Account.class).setParameter("name",payor).uniqueResult();
        if(account==null){
            System.out.println("付款人未找到");
            return false;
        }else{
            account.setMoney(account.getMoney()-money);
            return true;
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
