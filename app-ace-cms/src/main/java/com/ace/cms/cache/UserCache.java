package com.ace.cms.cache;


import com.ace.cms.dto.UserDto;
import com.ace.cms.mapper.UserMapper;
import com.ace.cms.utils.BasedataConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserCache extends AbstractForeverGuavaCache<Long, UserDto>{
	
	@Autowired
	private UserMapper userMapper;
	@Override
	protected UserDto loadData(Long id) {
		try{
			return userMapper.getUserInfo(id);
		}
		catch (Exception e){
			log.error("{} queryUserById exception:{} ,id:",this.getClass().toString(),e,id);
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
