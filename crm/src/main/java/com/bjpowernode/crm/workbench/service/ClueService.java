package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;

/**
 * @author huangyb
 * @create 2021-07-19 23:20
 */
public interface ClueService {
    boolean save(Clue c);

    Clue detail(String id);



    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String clueId, Tran t, String createBy);
}
