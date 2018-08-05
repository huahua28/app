package com.ace.cms.mapper;

import com.ace.cms.dto.TagDto;
import com.ace.cms.dto.UserDto;
import com.ace.cms.dto.UserExtDto;
import com.ace.cms.vo.user.TagLog;
import com.ace.cms.vo.user.UserInfo;
import com.ace.cms.vo.user.UserTag;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {

	UserDto getUserInfo(@Param("authorId") Long authorId);

	UserDto selectByMobile(@Param("mobile") String mobile);

	UserDto selectByName(@Param("name") String name);

	UserDto selectBySessionId(@Param("sessionId") String sessionId);

	UserDto selectByMobileAndPassword(@Param("mobile") String mobile, @Param("password") String password);

	UserDto selectByNameAndPassword(@Param("name") String name, @Param("password") String password);

	int insert(UserDto dto);

	int updateSessionByUserId(@Param("sessionId") String sessionId, @Param("userId") Long userId);

	int forgetPassword(@Param("userId") Long userId, @Param("password") String password);

	List<UserTag> findTagByUserId(Long userId);

	List<UserTag> findTagStatusByUserId(@Param("vieweeId")Long vieweeId, @Param("viewerId")Long viewerId);
	
	List<TagLog> findTagLog(@Param("tagId")Long tagId,@Param("userId")Long userId);

	void addLight(@Param("operator")Long operator,@Param("owner")Long owner,@Param("tagId") Long tagId);

	void deleteLight(@Param("operator")Long operator,@Param("owner")Long owner, @Param("tagId")Long tagId);

	List<UserInfo> findAllTagStatusByUserId(@Param("vieweeId")Long vieweeId, @Param("viewerId")Long viewerId);

	void tagCount(@Param("tagId")Long tagId,@Param("userId")Long userId, @Param("num")Long num);

	void addUserTag(@Param("userId")Long userId,@Param("creator") Long creator, @Param("tagId")Long tagId);

	void deleteUserTag(@Param("tagId")Long tagId, @Param("userId")Long userId);

	List<TagDto> getTagHistory(Long userId);

	void updateUsername(@Param("userId")long userId, @Param("username")String username);

	void updateGender(@Param("userId")long userId, @Param("gender")Integer gender);
	
	void updateUserImg(@Param("userId")long userId, @Param("userImg") String userImg);

	UserExtDto getUserExtInfo(@Param("userId")Long userId);
	
}
