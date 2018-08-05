package com.ace.cms.service;

import java.util.List;

import com.ace.cms.vo.opportunity.OpportunityVo;

public interface OpportunityService {

    List<OpportunityVo> getList(int start, int pageSize);

	OpportunityVo getDetail(int opportunityId);


}
