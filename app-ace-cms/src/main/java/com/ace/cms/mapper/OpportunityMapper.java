package com.ace.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ace.cms.dto.OpportunityDto;
import com.ace.cms.vo.opportunity.OpportunityVo;

public interface OpportunityMapper {

	List<OpportunityDto> getList(@Param("start") int start,@Param("pageSize") int pageSize);
	OpportunityDto getDetail(@Param("opportunityId")int opportunityId);
}
