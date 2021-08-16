package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @author huangyb
 * @create 2021-07-19 23:37
 */
public interface DicService {
    Map<String, List<DicValue>> getAll();
}
