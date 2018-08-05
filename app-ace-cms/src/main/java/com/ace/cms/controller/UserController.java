package com.ace.cms.controller;

import com.ace.cms.dto.TagDto;
import com.ace.cms.dto.UserDto;
import com.ace.cms.enums.ErrorCode;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.mapper.UserMapper;
import com.ace.cms.service.TagService;
import com.ace.cms.service.UserService;
import com.ace.cms.vo.Response;
import com.ace.cms.vo.user.TagLog;
import com.ace.cms.vo.user.UserInfo;
import com.ace.cms.vo.user.UserTag;
import com.ace.cms.vo.user.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
public class UserController  extends AbstractController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private TagService tagService;

	@RequestMapping(value = "/casLogin")
	public Response<UserVo> casLogin(@RequestParam(value = "username")  String username,
											@RequestParam(value = "password")  String password)
					throws HttpException, java.io.IOException {
		 username = username.replaceAll("[\\t\\n\\r]", "").trim();
		 password = password.toLowerCase();
		 String server="http://122.112.2.54:8089/cas/v1/tickets";
		 String service = "http://localhost:9082/cms/api/front/cas-login"; 
		 String ticket=getServiceTicket(server,
				getTicketGrantingTicket(server, username, password), service);
		 if(ticket!=null) {
			 Header h=getCookie(service,ticket);
			 String result = h.getValue().split(";")[1].split("=")[1];
			 UserVo user = userService.selectByUsername(username,password,result);
			 if(Objects.equals(user, null)) {
			 	throw new WebControllerException(ErrorCode.USER_OR_PASSWORD_ERROR);
			 }
			 user.setSessionId(result);
			 return new Response<>(user);
		 }else {
			 throw new WebControllerException(ErrorCode.USER_OR_PASSWORD_ERROR);
		 }
	}
	
	@RequestMapping(value = "/casLogout")
	public Response<Boolean> casLogout(@RequestParam(value = "jsessionId")  String jsessionId)
					throws HttpException, java.io.IOException {
		 String server="http://122.112.2.54:8089/cas/login";
		 final HttpClient client = new HttpClient();
		 
			final PostMethod post = new PostMethod(server);
			post.setRequestBody(new NameValuePair[] { new NameValuePair("JSESSIONID",
					jsessionId) });
			try {
				try {
					client.executeMethod(post);
				} catch (Exception e) {
					e.printStackTrace();
				}
				switch (post.getStatusCode()) {
				case 200:
					removeUserVoBySession(jsessionId);
					return new Response<Boolean>(true);
				default:
					break;
				}
			}
			finally {
				post.releaseConnection();
			}
			return null;
	}

	@RequestMapping(value = "/register")
	public Response<Boolean> register(@RequestParam(value = "mobile")  String mobile,
									 @RequestParam(value = "password")  String password,
									 @RequestParam(value = "userName")  String userName,
									 @RequestParam(value = "gender")  int gender,
									 @RequestParam(value = "code")  String code,
									 @RequestParam(value = "userImg")  String userImg){
		userName = userName.replaceAll("[\\t\\n\\r]", "").trim();
		password = password.toLowerCase();
		//用户验证码校验
		return new Response<>(userService.register(mobile, password, userName, gender, code, userImg));
	}

	@RequestMapping(value = "/verifyMobile")
	public Response<Boolean> verifyMobile(@RequestParam(value = "mobile")  String mobile){
		//用户验证码校验
		return new Response<>(userService.verifyMobile(mobile));
	}


	@RequestMapping(value = "/forgetPassword")
	public Response<Boolean> forgetPassword(@RequestParam(value = "mobile")  String mobile,
											@RequestParam(value = "password")  String password,
											@RequestParam(value = "code")  String code){
		password = password.toLowerCase();
		//用户验证码校验
		return new Response<>(userService.forgetPassword(mobile, password, code));
	}

	@RequestMapping(value = "/modifyPassword")
	public Response<Boolean> modifyPassword(@RequestParam(value = "jsessionId")  String jsessionId,
											@RequestParam(value = "oldPassword")  String oldPassword,
											@RequestParam(value = "currentPassword")  String currentPassword,
											@RequestParam(value = "confirmPassword")  String confirmPassword){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		if(!Objects.equals(userVo.getPassword(), oldPassword.toLowerCase())) {
			throw new WebControllerException(ErrorCode.OLD_PASSWORD_ERROR);
		}

		if(!Objects.equals(currentPassword, confirmPassword)) {
			throw new WebControllerException(ErrorCode.TWO_INCONSISTENCIES_IN_CIPHER_INPUT);
		}


		//用户验证码校验
		return new Response<>(userService.modifyPassword(userVo.getUserId(), currentPassword.toLowerCase()));
	}

	@RequestMapping(value = "/realname")
	public Response<Boolean> realname(@RequestParam(value = "jsessionId")  String jsessionId,
									  @RequestParam(value = "cardName")  String cardName,
									  @RequestParam(value = "cardNo")  String cardNo,
									  @RequestParam(value = "facadeImg")  String facadeImg,
									  @RequestParam(value = "backImg")  String backImg){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		cardName =  cardName.replaceAll("[\\t\\n\\r]", "").trim();
		//用户验证码校验
		return new Response<>(userService.realname(userVo.getUserId(), cardName, cardNo, facadeImg, backImg));
	}

	@RequestMapping(value = "/realInfo")
	public Response<Boolean> realInfo(@RequestParam(value = "jsessionId")  String jsessionId){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		//用户验证码校验
		return new Response<>(userService.realInfo(userVo.getUserId()));
	}

	@RequestMapping(value = "/checkUsername")
	public Response<Boolean> checkUsername(@RequestParam(value = "username")  String username) {
		try {
			userService.checkUsername(username);
		}catch(WebControllerException e) {
			return new Response<Boolean>(e.getCode(),e.getMessage());
		}
		return new Response<>(true);
	}

	private static String getServiceTicket(final String server,
			final String ticketGrantingTicket, final String service) throws HttpException, java.io.IOException {
		if (ticketGrantingTicket == null)
			return null;
		final HttpClient client = new HttpClient();
		final PostMethod post = new PostMethod(server + "/"
				+ ticketGrantingTicket);
		post.setRequestBody(new NameValuePair[] { new NameValuePair("service",
				service) });
		try {
			try {
				client.executeMethod(post);
			} catch (Exception e) {
				e.printStackTrace();
			}
			final String response = post.getResponseBodyAsString();
			switch (post.getStatusCode()) {
			case 200:
				return response;
			default:
				break;
			}
		}
		finally {
			post.releaseConnection();
		}
		return null;
	}

	private static String getTicketGrantingTicket(final String server,
			final String username, final String password) throws HttpException, java.io.IOException {
		final HttpClient client = new HttpClient();
		final PostMethod post = new PostMethod(server);
		post.setRequestBody(new NameValuePair[] {new NameValuePair("username", username),
				new NameValuePair("password", password) });
		try {
			client.executeMethod(post);
			final String response = post.getResponseBodyAsString();
			switch (post.getStatusCode()) {
			case 201: {
				final Matcher matcher = Pattern.compile(".*action=\".*/(.*?)\".*").matcher(response);
				if (matcher.matches())
					return matcher.group(1);
				break;
			}
			default:
				break;
			}
		}finally {
			post.releaseConnection();
		}
		return null;
	}


	private static Header getCookie(final String service,
			final String ticket) throws HttpException, java.io.IOException {
		final HttpClient client = new HttpClient();
		final GetMethod get = new GetMethod(service+"?ticket="+ticket);
		try {
			client.executeMethod(get);
			Header[] header=get.getRequestHeaders();
			for(Header h:header) {
				if(h.getName().equals("Cookie"))
					return h;
			}
			return null;
		}finally {
			get.releaseConnection();
		}
	}
	//get persional infomation including summary of tags
	@RequestMapping(value = "/homepage")
	public Response<UserInfo> homepage(@RequestParam(value = "jsessionId")  String jsessionId
			){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		return new Response<>(userService.findByUserId(userVo.getUserId()));
	}
	

	
	//get other user's infomation including summary of tags
	@RequestMapping(value = "/otherhome")
	public Response<UserInfo> otherhome(@RequestParam(value = "jsessionId")  String jsessionId,
			@RequestParam(value = "userId")  Long userId){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		return new Response<>(userService.findByUserId(userId ,userVo.getUserId()));
	}
	
	
		
	//get lighting up log for one specific tag
	@RequestMapping(value = "/tagLog")
	public Response<List<TagLog>> tagLog(@RequestParam(value = "jsessionId")  String jsessionId,
			@RequestParam(value = "tagId")Long tagId,
			@RequestParam(value = "userId")Long userId){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		return new Response<>(userService.findTagLog(tagId,userId));
	}
	
	
	//get list of tag and it's lighting summay list
	@RequestMapping(value = "/tagList")
	public Response<List<UserInfo>> tagList(@RequestParam(value = "jsessionId")  String jsessionId,
			@RequestParam(value = "userId")  Long userId){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		return new Response<>(userService.findAllByUserId(userId ,userVo.getUserId()));
	}	
	
	
	//light up or light off a tag
	@RequestMapping(value = "/tagOperate")
	public Response<Boolean> tagOperate(@RequestParam(value = "jsessionId")String jsessionId,
			@RequestParam(value = "tagId")Long tagId,
			@RequestParam(value = "operate")Boolean light,
			@RequestParam(value = "userId")Long userId){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		return new Response<>(userService.operateTag(userVo.getUserId(),userId,tagId,light));
	}
	
	@RequestMapping(value = "/tagUserAdd")
	public Response<Boolean> tagUserAdd(@RequestParam(value = "jsessionId")String jsessionId,
			@RequestParam(value = "userId", required = false)Long userId,
			@RequestParam(value = "tag", required = false)String tag,
			@RequestParam(value = "tagId", required = false)Long tagId){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		if(userId==null)userId=userVo.getUserId();
		if(tagId==null)tagId=tagService.addTag(tag);
		return new Response<>(userService.addUserTag(userVo.getUserId(),userId,tagId));
	}
	
	@RequestMapping(value = "/tagUserDelete")
	public Response<Boolean> tagUserDelete(@RequestParam(value = "jsessionId")String jsessionId,
			@RequestParam(value = "tagId")Long tagId){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		return new Response<>(userService.deleteUserTag(tagId,userVo.getUserId()));
	}
	
	
	@RequestMapping(value = "/tagHistory")
	public Response<List<TagDto>> tagHistory(@RequestParam(value = "jsessionId")String jsessionId
			){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		return new Response<>(userService.getTagHistory(userVo.getUserId()));
	}
	
	
	@RequestMapping(value = "/tagSearch")
	public Response<List<TagDto>> tagSearch(@RequestParam(value = "jsessionId")String jsessionId,
			@RequestParam(value = "keyword")String keyword){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		return new Response<>(tagService.searchTag(keyword));
	}
	
	@RequestMapping(value = "/tagRecommend")
	public Response<List<TagDto>> tagRecommend(@RequestParam(value = "jsessionId")String jsessionId
			){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		return new Response<>(tagService.recommendTag());
	}
	
	@RequestMapping(value = "/updateInformation")
	public Response<Boolean> updateInformation(@RequestParam(value = "jsessionId")String jsessionId,
			@RequestParam(value = "username", required = false)String username, 
			@RequestParam(value = "gender", required = false)Integer gender, 
			@RequestParam(value = "userImg", required = false)String userImg){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		//remove the same username
		if(username!=null&&username.equals(userVo.getUsername()))username=null;
		if(userService.update(userVo.getUserId(),username,gender,userImg)) {
			removeUserVoBySession(jsessionId);
			removeUserVo(userVo.getUserId());
			return new Response<>(true);
		}
		return new Response<>(false);
	}
	
	@RequestMapping(value = "/getInformation")
	public Response<UserInfo> getInformation(@RequestParam(value = "jsessionId")  String jsessionId
			){
		UserVo userVo = getUserVoBySession(jsessionId);
		if(Objects.equals(userVo, null)) {
			throw new WebControllerException(ErrorCode.LOGIN_OUT_OF_TIME);
		}
		return new Response<>(userService.getByUserId(userVo.getUserId()));
	}
	
}
