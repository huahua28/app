package com.ace.cms.service.impl;

import com.ace.cms.cache.SerialService;
import com.ace.cms.dto.*;
import com.ace.cms.enums.ErrorCode;
import com.ace.cms.enums.TableNameEnum;
import com.ace.cms.exceptions.SequenceException;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.mapper.*;
import com.ace.cms.service.SensitivityService;
import com.ace.cms.service.UserService;
import com.ace.cms.vo.SensitivityVo;
import com.ace.cms.vo.user.TagLog;
import com.ace.cms.vo.user.UserInfo;
import com.ace.cms.vo.user.UserTag;
import com.ace.cms.vo.user.UserVo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private SerialService serialService;

    @Autowired
    private SensitivityService sensitivityService;

    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Override
    public boolean verifyMobile(String mobile) {
        UserDto mobileUser = userMapper.selectByMobile(mobile);
        if(!Objects.equals(mobileUser, null)) {
            throw new WebControllerException(ErrorCode.MOBILE_IS_REGISTER);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(String mobile, String password, String userName, int gender, String code, String userImg) {

        //敏感词加缓存 敏感词替换
        List<SensitivityVo> sens=sensitivityService.getList();
        for(SensitivityVo sensitivityVo: sens) {
            if(userName.contains(sensitivityVo.getSearch())){
                throw new WebControllerException(ErrorCode.SENSITIVE_WORDS);
            }
        }
        verifyMobile(mobile);

        UserDto nameUser = userMapper.selectByName(userName);
        if(!Objects.equals(nameUser, null)) {
            throw new WebControllerException(ErrorCode.NAME_ALREADY_EXIST);
        }

        long userId  = serialService.get(TableNameEnum.JC_USER.getCode());

        UserDto insertUser = UserDto.builder().userId(userId).groupId(1l).password(password).username(userName).build();

        if(userMapper.insert(insertUser) <=0) {
            throw new WebControllerException(ErrorCode.SYS_ERR);
        }

        userExtMapper.insert(UserExtDto.builder().userId(insertUser.getUserId()).gender(gender).mobile(mobile).userImg(userImg).build());

        return true;

    }


    @Override
    public UserVo selectByUsername(String username, String password, String jsessionId) {
        UserDto user = userMapper.selectByMobileAndPassword(username, password);
        if(Objects.equals(user, null)) {
            user = userMapper.selectByNameAndPassword(username, password);
            if(Objects.equals(user, null)) {
                return null;
            }
        }

        userMapper.updateSessionByUserId(jsessionId, user.getUserId());

        UserVo userVo = getUserVo(user);
        return userVo;
    }

    @Override
    public UserVo selectBySessionId(String jsessionId) {
        UserDto user = userMapper.selectBySessionId(jsessionId);

        if(Objects.equals(user, null)) {
            return null;
        }

        UserVo userVo = getUserVo(user);

        return userVo;
    }

    @Override
    public boolean forgetPassword(String mobile, String password, String code) {
        UserDto mobileUser = userMapper.selectByMobile(mobile);
        if(Objects.equals(mobileUser, null)) {
            throw new WebControllerException(ErrorCode.MOBILE_IS_NOT_REGISTER);
        }
        if(userMapper.forgetPassword(mobileUser.getUserId(), password)<=0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyPassword(Long userId, String newPassword) {
        if(userMapper.forgetPassword(userId, newPassword) <= 0) {
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public boolean realname(Long userId, String cardName, String cardNo, String facadeImg, String backImg) {
        int authentication = authenticationMapper.insert(AuthenticationDto.builder()
                .userId(userId)
                .cardId(cardNo)
                .cardName(cardName)
                .facadeImg(facadeImg)
                .backImg(backImg).build());
        if(authentication <=0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean realInfo(Long userId) {
        AuthenticationDto  authenticationDto = authenticationMapper.selectByUserId(userId);
        if(Objects.equals(authenticationDto, null)) {
            return false;
        }
        return true;
    }

    private UserVo getUserVo(UserDto user) {
        UserVo userVo = new UserVo();
        userVo.setUserId(user.getUserId());
        userVo.setUsername(user.getUsername());
        userVo.setSessionId(user.getSessionId());
        userVo.setUserImg(user.getUserImg());
        userVo.setPassword(user.getPassword());
        return userVo;
    }

	@Override
	public UserInfo findByUserId(Long userId) {
		UserDto uv=userMapper.getUserInfo(userId);
		UserInfo ui=new UserInfo();
		ui.setUserId(userId);
		ui.setUserImg(uv.getUserImg());
		ui.setUsername(uv.getUsername());
		List<UserTag>list=userMapper.findTagByUserId(userId);
		ui.setTag(list);
		return ui;
	}

	@Override
	public UserInfo getByUserId(Long userId) {
		UserDto uv=userMapper.getUserInfo(userId);
		UserInfo ui=new UserInfo();
		ui.setUserId(userId);
		ui.setUserImg(uv.getUserImg());
		ui.setUsername(uv.getUsername());
		UserExtDto ue=userMapper.getUserExtInfo(userId);
		ui.setGender(ue.getGender());
		return ui;
	}
	
	@Override
	public UserInfo findByUserId(Long vieweeId, Long viewerId) {
		UserDto uv=userMapper.getUserInfo(vieweeId);
		UserInfo ui=new UserInfo();
		ui.setUserId(vieweeId);
		ui.setUserImg(uv.getUserImg());
		ui.setUsername(uv.getUsername());
		List<UserTag>list=userMapper.findTagStatusByUserId(vieweeId,viewerId);
		ui.setTag(list);
		return ui;
	}
	
	@Override
	public List<TagLog> findTagLog(Long tagId,Long userId) {
		return userMapper.findTagLog(tagId,userId);
	}

	@Override
	public Boolean operateTag(Long operator,Long owner, Long tagId, Boolean light) {
		if(light==true) {
			userMapper.addLight(operator,owner,tagId);
			userMapper.tagCount(tagId,owner,1L);
		}
		if(light==false) {
			userMapper.deleteLight(operator,owner,tagId);
			userMapper.tagCount(tagId,owner,-1L);
		}
		return true;
	}

	@Override
	public List<UserInfo> findAllByUserId(Long vieweeId, Long viewerId) {
		return userMapper.findAllTagStatusByUserId(vieweeId,viewerId);
	}

    @Transactional
	@Override
	public Boolean addUserTag(Long creator, Long userId, Long tagId) {
		userMapper.addUserTag(creator, userId,tagId);
		tagMapper.tagCount(tagId,1l);
		return true;
	}

	@Override
	public Boolean deleteUserTag(Long tagId, Long userId) {
		userMapper.deleteUserTag(tagId,userId);
		tagMapper.tagCount(tagId,-1l);
		return true;
	}

	@Override
	public List<TagDto> getTagHistory(Long userId) {
		
		return userMapper.getTagHistory(userId);
	}

    @Transactional
	@Override
	public Boolean update(long userId, String username, Integer gender, String userImg) {
		if(username!=null&&!"".equals(username)) {
			//敏感词加缓存 敏感词替换
	        List<SensitivityVo> sens=sensitivityService.getList();
	        for(SensitivityVo sensitivityVo: sens) {
	            if(username.contains(sensitivityVo.getSearch())){
	                throw new WebControllerException(ErrorCode.SENSITIVE_WORDS);
	            }
	        }

	        UserDto nameUser = userMapper.selectByName(username);
	        if(!Objects.equals(nameUser, null)) {
	            throw new WebControllerException(ErrorCode.NAME_ALREADY_EXIST);
	        }
			userMapper.updateUsername(userId,username);
		}
		if(gender!=null)userMapper.updateGender(userId,gender);
		if(userImg!=null&&!"".equals(userImg))userMapper.updateUserImg(userId, userImg);
		return true;
	}

	@Override
	public void checkUsername(String username) {
		List<SensitivityVo> sens=sensitivityService.getList();
        for(SensitivityVo sensitivityVo: sens) {
            if(username.contains(sensitivityVo.getSearch())){
                throw new WebControllerException(ErrorCode.SENSITIVE_WORDS);
            }
        }
        UserDto nameUser = userMapper.selectByName(username);
        if(!Objects.equals(nameUser, null)) {
            throw new WebControllerException(ErrorCode.NAME_ALREADY_EXIST);
        }
	}

	
}
