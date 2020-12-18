package com.ssm.service;

import com.ssm.bean.Demo;
import com.ssm.mapper.DemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DemoService {
    @Autowired
    private DemoMapper demoMapper;

    public Demo getDemoById(int id){
        return Optional.ofNullable(
                demoMapper.selectByPrimaryKey(id)
        ).orElse(null);
    }
}
