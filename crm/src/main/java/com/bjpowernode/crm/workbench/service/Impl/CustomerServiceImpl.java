package com.bjpowernode.crm.workbench.service.Impl;

import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangyb
 * @create 2021-07-31 0:41
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    //private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<String> getCustomerName(String name) {


        List<String> sList = customerDao.getCustomerName(name);

        return sList;
    }
}
