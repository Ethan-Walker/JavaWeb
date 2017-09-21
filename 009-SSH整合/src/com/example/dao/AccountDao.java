package com.example.dao;

/**
 * Created by EthanWalker on 2017/8/23.
 */
public interface AccountDao {
    boolean push(String payee,Integer money);
    boolean pull(String payor,Integer money);
}
