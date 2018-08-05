package com.ace.cms.service;

import java.util.List;

import com.ace.cms.dto.TagDto;
import com.ace.cms.vo.user.TagLog;
import com.ace.cms.vo.user.UserInfo;
import com.ace.cms.vo.user.UserVo;

public interface UserService {

    boolean register(String mobile,String password,String userName, int gender,String code,String userImg);

    boolean verifyMobile(String mobile);

    UserVo selectByUsername(String username,String password,String jsessionId);

    UserVo selectBySessionId(String jsessionId);

    boolean forgetPassword(String mobile, String password, String code);

    boolean modifyPassword(Long userId, String newPassword);

    boolean realname(Long userId, String cardName, String cardNo, String facadeImg, String backImg);

    boolean realInfo(Long userId);
    
    UserInfo findByUserId(Long userId);

	List<TagLog> findTagLog(Long tagId,Long userId);

	UserInfo findByUserId(Long vieweeId, Long viewerId);

	Boolean operateTag(Long operator, Long owner, Long tagId, Boolean light);
	
	List<UserInfo> findAllByUserId(Long vieweeId, Long viewerId);

	Boolean addUserTag(Long userId, Long userId2, Long tagId);

	Boolean deleteUserTag(Long tagId,Long userId);

	List<TagDto> getTagHistory(Long userId);

	Boolean update(long userId, String username, Integer gender, String userImg);

	UserInfo getByUserId(Long userId);

	void checkUsername(String username);
}
