package com.ace.cms.service.impl;

import com.ace.cms.dto.ChannelDto;
import com.ace.cms.mapper.ChannelUserMapper;
import com.ace.cms.vo.channel.ChannelVo;
import com.ace.cms.mapper.ChannelMapper;
import com.ace.cms.service.ChannelService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private ChannelUserMapper channelUserMapper;


    @Override
    public List<ChannelVo> getListByParentId(int parentId) {
        List<ChannelDto> list = channelMapper.getListByParentId(parentId);

        List<ChannelVo> result = new ArrayList<>();
        for(ChannelDto dto:list) {
        	ChannelVo vo=new ChannelVo();
        	BeanUtils.copyProperties(dto, vo);
        	result.add(vo);
        }
        return result;
    }

    @Override
    public boolean add(int channelId, Long userId) {
        int count = channelUserMapper.count(channelId, userId);
        if(count == 0) {
            if(channelUserMapper.insert(channelId, userId) <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ChannelVo> select(int parentId, Long userId) {
        List<ChannelDto> list = channelMapper.getListByUserId(parentId, userId);

        List<ChannelVo> result = new ArrayList<>();
        for(ChannelDto dto:list) {
            ChannelVo vo=new ChannelVo();
            BeanUtils.copyProperties(dto, vo);
            result.add(vo);
        }
        return result;
    }


}
