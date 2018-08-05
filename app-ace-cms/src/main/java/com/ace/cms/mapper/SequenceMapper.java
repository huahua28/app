package com.ace.cms.mapper;

import com.ace.cms.cache.model.SequenceDefination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SequenceMapper {

    int updateSequence(@Param(value = "name") String name, @Param(value = "currentValue") long currentValue, @Param(value = "length") long length);

    SequenceDefination getSequence(@Param(value = "name") String name);

    int insertSequence(@Param(value = "name") String name);

    List<SequenceDefination> getAllSequence();

}
