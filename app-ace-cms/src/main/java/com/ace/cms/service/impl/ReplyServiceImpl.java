package com.ace.cms.service.impl;

import com.ace.cms.cache.UserCache;
import com.ace.cms.dto.CommentDto;
import com.ace.cms.dto.ContentDto;
import com.ace.cms.dto.ContentPictureDto;
import com.ace.cms.dto.UserDto;
import com.ace.cms.mapper.CommentMapper;
import com.ace.cms.mapper.ContentMapper;
import com.ace.cms.mapper.ContentPictureMapper;
import com.ace.cms.mapper.UserMapper;
import com.ace.cms.service.ReplyService;
import com.ace.cms.utils.DateUtil;
import com.ace.cms.vo.ContentVo;
import com.ace.cms.vo.Page;
import com.ace.cms.vo.comment.Comment;
import com.ace.cms.vo.comment.Parent;
import com.ace.cms.vo.comment.PersonalReply;
import com.ace.cms.vo.comment.ReceiveReply;
import com.ace.cms.vo.comment.ReplyArr;
import com.ace.cms.vo.comment.ReplyVo;
import com.ace.cms.vo.user.Commenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserCache userCache;
    
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private ContentPictureMapper contentPictureMapper;
    
    @Override
    public List<ReplyVo> getReplyArrById(Long contentId, Long commentId, int first, int count) {
        List<ReplyVo> replyVos = new ArrayList<>();
        Page page = new Page(first,count);
        List<CommentDto> comments = commentMapper.getCommentByAncestorId(contentId, commentId, page.getStartRow(), page.getPageCount());
        for(CommentDto commentDto : comments) {
            ReplyVo replyVo = new ReplyVo();
            if(commentDto.getIsRecommend() == 0) {
                replyVo.setAnonymous(true);
            }
            replyVo.setReplyId(commentDto.getCommentId());
            replyVo.setText(commentDto.getText());
            replyVo.setUserId(commentDto.getCommentUserId());
            if(!replyVo.isAnonymous()) {
	            UserDto userDto = userCache.get(commentDto.getCommentUserId());
	            replyVo.setUsername(userDto.getUsername());
	            replyVo.setUserimg(userDto.getUserImg());
            }else {
            	replyVo.setUserId(-1L);
            	replyVo.setUsername(commentDto.getFakeName());
            	replyVo.setUserimg("");
            }
            
            replyVo.setCreateTime(DateUtil.format(commentDto.getCreateTime(), DateUtil.timePattern));
            
            Parent parent = null;
            if(!Objects.equals(commentDto.getParentId(), null)) {
                parent = new Parent();
                CommentDto parentComent = commentMapper.getCommentByParentId(commentDto.getParentId());
                UserDto parentUser = userCache.get(parentComent.getCommentUserId());
                boolean parentAnonymous = false;
                if(parentComent.getIsRecommend() == 0l) {
                    parentAnonymous = true;
                }
                parent.setAnonymous(parentAnonymous);
                parent.setReplyId(parentComent.getCommentId());
                parent.setText(parentComent.getText());
                if(!parentAnonymous) {
	                parent.setUserId(parentComent.getCommentUserId());
	                if(!Objects.equals(parentUser, null)) {
	                    parent.setUsername(parentUser.getUsername());
	                }
                }
                else {
                	parent.setUserId(-1L);
                	parent.setUsername(parentComent.getFakeName());
                }
            }
            replyVo.setParent(parent);
            replyVos.add(replyVo);

        }
        return replyVos;
    }
    
    
    
    
    
    @Override
    public List<PersonalReply> getReplyArrByUserId(Long userId, int first, int count) {
        List<PersonalReply> replyVos = new ArrayList<>();
        Page page = new Page(first,count);
        List<CommentDto> comments = commentMapper.getCommentByUserId(userId, page.getStartRow(), page.getPageCount());
        for(CommentDto commentDto : comments) {
        	PersonalReply replyVo = new PersonalReply();
        	Long contentId=commentDto.getContentId();
        	Long channelId=commentDto.getChannelId();
        	ContentVo vo=new ContentVo();
        	vo.setContentId(contentId);
        	vo.setChannelId(channelId);
        	vo.setCreateTime(DateUtil.format(commentDto.getContentDate(),DateUtil.mdPattern));
        	List<ContentPictureDto>pics=contentPictureMapper.getNewsPicArr(contentId);
        	if(pics!=null &&pics.size()>0)vo.setPicUrl(pics.get(0).getImgPath());
        	if(channelId==75l) {
        		ContentDto dto=contentMapper.getByContentId(contentId);
        		vo.setText(dto.getTxt());
        	
        	}else {
        		ContentDto dto=contentMapper.getNewsByContentId(contentId);
        		vo.setText(dto.getTitle());
        	}
        	replyVo.setContentVo(vo);
            if(commentDto.getIsRecommend() == 0) {
                replyVo.setAnonymous(true);
            }
            replyVo.setReplyId(commentDto.getCommentId());
            replyVo.setText(commentDto.getText());
            
            replyVo.setCreateTime(DateUtil.format(commentDto.getCreateTime(), DateUtil.timePattern));

            Parent parent = null;
            if(!Objects.equals(commentDto.getParentId(), null)) {
                parent = new Parent();
                CommentDto parentComent = commentMapper.getCommentByParentId(commentDto.getParentId());
                UserDto parentUser = userCache.get(parentComent.getCommentUserId());
                boolean parentAnonymous = false;
                if(parentComent.getIsRecommend() == 0l) {
                    parentAnonymous = true;
                }
                parent.setAnonymous(parentAnonymous);
                parent.setReplyId(parentComent.getCommentId());
                parent.setText(parentComent.getText());
                if(!parentAnonymous) {
	                parent.setUserId(parentComent.getCommentUserId());
	                if(!Objects.equals(parentUser, null)) {
	                    parent.setUsername(parentUser.getUsername());
	                }
                }
                else {
                	parent.setUserId(-1L);
                	parent.setUsername(parentComent.getFakeName());
                }
            }
            replyVo.setParent(parent);
            replyVos.add(replyVo);

        }
        return replyVos;
    }





	@Override
	public List<ReceiveReply> getReplyByUserId(Long userId, int first, int count) {
		List<ReceiveReply> replyVos = new ArrayList<>();
        Page page = new Page(first,count);
        List<CommentDto> comments = commentMapper.getReplyByUserId(userId, page.getStartRow(), page.getPageCount());
        for(CommentDto commentDto : comments) {
        	ReceiveReply replyVo = new ReceiveReply();
        	Long contentId=commentDto.getContentId();
        	Long channelId=commentDto.getChannelId();
        	Long commenterId=commentDto.getCommentUserId();
        	UserDto user= userCache.get(commenterId);
        	Commenter c=new Commenter();
        	c.setId(commenterId);
        	c.setImg(user.getUserImg());
        	c.setName(user.getUsername());
        	replyVo.setCommenter(c);
        	ContentVo vo=new ContentVo();
        	vo.setContentId(contentId);
        	vo.setChannelId(channelId);
        	vo.setCreateTime(DateUtil.format(commentDto.getContentDate(),DateUtil.mdPattern));
        	List<ContentPictureDto>pics=contentPictureMapper.getNewsPicArr(contentId);
        	if(pics!=null &&pics.size()>0)vo.setPicUrl(pics.get(0).getImgPath());
        	if(channelId==75l) {
        		ContentDto dto=contentMapper.getByContentId(contentId);
        		vo.setText(dto.getTxt());
        	
        	}else {
        		ContentDto dto=contentMapper.getNewsByContentId(contentId);
        		vo.setText(dto.getTitle());
        	}
        	replyVo.setContentVo(vo);
            if(commentDto.getIsRecommend() == 0) {
                replyVo.setAnonymous(true);
            }
            replyVo.setReplyId(commentDto.getCommentId());
            replyVo.setText(commentDto.getText());
            
            replyVo.setCreateTime(DateUtil.format(commentDto.getCreateTime(), DateUtil.timePattern));

            Parent parent = null;
            if(!Objects.equals(commentDto.getParentId(), null)) {
                parent = new Parent();
                CommentDto parentComent = commentMapper.getCommentByParentId(commentDto.getParentId());
                UserDto parentUser = userCache.get(parentComent.getCommentUserId());
                boolean parentAnonymous = false;
                if(parentComent.getIsRecommend() == 0l) {
                    parentAnonymous = true;
                }
                parent.setAnonymous(parentAnonymous);
                parent.setReplyId(parentComent.getCommentId());
                parent.setText(parentComent.getText());
                if(!parentAnonymous) {
	                parent.setUserId(parentComent.getCommentUserId());
	                if(!Objects.equals(parentUser, null)) {
	                    parent.setUsername(parentUser.getUsername());
	                }
                }
                else {
                	parent.setUserId(-1L);
                	parent.setUsername(parentComent.getFakeName());
                }
            }
            replyVo.setParent(parent);
            replyVos.add(replyVo);

        }
        return replyVos;
	}
}
