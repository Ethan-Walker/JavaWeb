package com.example.service.impl;

import com.example.dao.AccountDao;
import com.example.service.AccountService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by EthanWalker on 2017/8/23.
 */
@Transactional
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    @Override
    public void transfer(String payor, String payee, Integer money) {
        accountDao.pull(payor,money);
//        int i=1/0;
        accountDao.push(payee,money);
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
