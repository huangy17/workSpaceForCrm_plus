package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author huangyb
 * @create 2021-07-10 23:10
 */
public interface UserDao {
    User login(Map<String, String> map);

    List<User> getUserList();
}
