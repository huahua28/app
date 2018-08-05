package com.ace.cms.cache;


import com.ace.cms.service.UserService;
import com.ace.cms.utils.BasedataConst;
import com.ace.cms.vo.user.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserSessionCache extends AbstractForeverGuavaCache<String, UserVo>{
	
	@Autowired
	private UserService userService;

	@Override
	protected UserVo loadData(String jsessionId) {
		try{
			return userService.selectBySessionId(jsessionId);
		}
		catch (Exception e){
			log.error("{} queryUserById exception:{} ,id:",this.getClass().toString(),e,jsessionId);
		}
		return null;
	}

	@Override
	protected int initialCapacity() {
		return BasedataConst.NUMBER_INT_VALUE_50000;
	}

	@Override
	protected int maximumSize() {
		return BasedataConst.NUMBER_INT_VALUE_50000;
	}

	@Override
	protected String name() {
		return this.getClass().toString();
	}
}
