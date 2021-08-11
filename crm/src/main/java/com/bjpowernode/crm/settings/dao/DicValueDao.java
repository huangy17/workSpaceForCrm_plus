package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @author huangyb
 * @create 2021-07-19 23:35
 */
public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
