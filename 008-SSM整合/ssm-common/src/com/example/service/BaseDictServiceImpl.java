package com.example.service;

import com.example.dao.BaseDictMapper;
import com.example.pojo.BaseDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by EthanWalker on 2017/8/27.
 */
@Service
@Transactional
public class BaseDictServiceImpl implements BaseDictService {

    @Autowired
    BaseDictMapper baseDictMapper;

    @Override
    public List<BaseDict> queryBaseDictsByCode(String code) {
        List<BaseDict> baseDicts = baseDictMapper.queryBaseDictsByTypeCode(code);
        return baseDicts;
    }
}
