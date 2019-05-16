package com.qiaoxun.demo.service.impl;

import com.qiaoxun.demo.dao.QiswlCapterDao;
import com.qiaoxun.demo.pojo.QiswlCapterWithBLOBs;
import com.qiaoxun.demo.service.QiswlCapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QiswlChapterServiceImpl implements QiswlCapterService {
    @Autowired
    private QiswlCapterDao capterDao;

    @Override
    public int insertSelective(QiswlCapterWithBLOBs record) {
        return capterDao.insertSelective(record);
    }
}
