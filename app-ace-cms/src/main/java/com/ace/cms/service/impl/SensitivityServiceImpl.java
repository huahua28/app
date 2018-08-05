package com.ace.cms.service.impl;

import com.ace.cms.dto.SensitivityDto;
import com.ace.cms.vo.SensitivityVo;
import com.ace.cms.mapper.SensitivityMapper;
import com.ace.cms.service.SensitivityService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SensitivityServiceImpl implements SensitivityService {

    @Autowired
    private SensitivityMapper sensitivityMapper;


    @Override
    public List<SensitivityVo> getList() {
        List<SensitivityDto> list = sensitivityMapper.getList();

        List<SensitivityVo> result = new ArrayList<>();
        for(SensitivityDto dto:list) {
        	SensitivityVo vo=new SensitivityVo();
        	BeanUtils.copyProperties(dto, vo);
        	result.add(vo);
        }
        return result;
    }


}
