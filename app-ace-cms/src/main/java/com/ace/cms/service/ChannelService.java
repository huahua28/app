package com.ace.cms.service;

import java.util.List;

import com.ace.cms.vo.channel.ChannelVo;

public interface ChannelService {

    List<ChannelVo> getListByParentId(int channelId);

    boolean add(int channelId, Long userId);

    List<ChannelVo> select(int parentId, Long userId);

}
