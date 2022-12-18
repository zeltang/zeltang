package com.zeltang.it.service.impl;

import com.zeltang.it.mapper.mapper1.MultiTestMapper;
import com.zeltang.it.mapper.mapper2.MultiTestMapper2;
import com.zeltang.it.service.MultiTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiTestServiceImpl implements MultiTestService {
    @Autowired
    private MultiTestMapper multiTestMapper;

    @Autowired
    private MultiTestMapper2 multiTestMapper2;

    @Override
    public int ms() {
        return multiTestMapper.sele();
    }

    @Override
    public int ms2() {
        return multiTestMapper2.sele();
    }
}
