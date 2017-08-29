package com.example.dao;

import com.example.pojo.BaseDict;

import java.util.List;

public interface BaseDictMapper {

    List<BaseDict> queryBaseDictsByTypeCode(String code);

}