package com.example.service;

import com.example.pojo.BaseDict;

import java.util.List;

/**
 * Created by EthanWalker on 2017/8/27.
 */
public interface BaseDictService {
    List<BaseDict> queryBaseDictsByCode(String code);
}
