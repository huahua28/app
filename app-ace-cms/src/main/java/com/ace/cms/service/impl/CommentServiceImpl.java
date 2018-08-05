package com.ace.cms.service.impl;

import com.ace.cms.cache.SerialService;
import com.ace.cms.cache.UserCache;
import com.ace.cms.dto.CommentDto;
import com.ace.cms.dto.CommentExtDto;
import com.ace.cms.dto.ContentDto;
import com.ace.cms.dto.UserDto;
import com.ace.cms.enums.ErrorCode;
import com.ace.cms.enums.TableNameEnum;
import com.ace.cms.exceptions.SequenceException;
import com.ace.cms.exceptions.WebControllerException;
import com.ace.cms.mapper.CommentExtMapper;
import com.ace.cms.mapper.CommentMapper;
import com.ace.cms.mapper.ContentMapper;
import com.ace.cms.service.CommentService;
import com.ace.cms.service.SensitivityService;
import com.ace.cms.utils.DateUtil;
import com.ace.cms.vo.Page;
import com.ace.cms.vo.SensitivityVo;
import com.ace.cms.vo.comment.Comment;
import com.ace.cms.vo.comment.Parent;
import com.ace.cms.vo.comment.ReplyArr;
import com.ace.cms.vo.user.Commenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserCache userCache;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private SerialService serialService;
    
    @Autowired
    private SensitivityService sensitivityService;
    @Override
    public List<Comment> getCommentById(Long contentId, boolean onlyRoot, int start, int pageSize) {
        Page page = new Page(start,pageSize);
        List<CommentDto> list = commentMapper.getCommentPagesByContentId(contentId, page.getStartRow(), page.getPageCount());

        List<Comment> result = new ArrayList<>();
        for(CommentDto firstComment : list) {

            Comment comment = new Comment();
            comment.setCommentId(firstComment.getCommentId());
            comment.setText(firstComment.getText());
            comment.setCreateTime(DateUtil.format(firstComment.getCreateTime(), DateUtil.timePattern));
            comment.setReplyNum(firstComment.getReplyCount());
            boolean parentAnonymous = false;
            if(firstComment.getIsRecommend() == 0) {
                parentAnonymous = true;
            }
            comment.setAnonymous(parentAnonymous);

            UserDto nowUser = userCache.get(firstComment.getCommentUserId());
            //评论者的用户信息
            Commenter commenter = new Commenter();
            if(!parentAnonymous) {
            	commenter.setId(nowUser.getUserId());
            	commenter.setName(nowUser.getUsername());
                commenter.setImg(nowUser.getUserImg());
            }
            else {
            	commenter.setId(-1L);
            	commenter.setName(firstComment.getFakeName());
                commenter.setImg("");
            }

            if (!onlyRoot) {
                List<ReplyArr> replyArrs = new ArrayList<>();
                List<CommentDto> comments = commentMapper.getCommentByAncestorId(contentId, firstComment.getCommentId(), 0, 3);
                for(CommentDto commentDto : comments) {
                    ReplyArr replyArr = new ReplyArr();
                    if(commentDto.getIsRecommend() == 0) {
                        replyArr.setAnonymous(true);
                    }
                    replyArr.setReplyId(commentDto.getCommentId());
                    replyArr.setText(commentDto.getText());
                    if(commentDto.getIsRecommend()!=0) {
                        replyArr.setUserId(commentDto.getCommentUserId());
                        UserDto userDto = userCache.get(commentDto.getCommentUserId());
                        replyArr.setUsername(userDto.getUsername());
                    }
                    else {
                        replyArr.setUserId(-1L);
                        replyArr.setUsername(commentDto.getFakeName());
                    }
                    Parent parent = null;
                    if(!Objects.equals(commentDto.getParentId(), null)) {
                        parent = new Parent();
                        CommentDto parentComent = commentMapper.getCommentByParentId(commentDto.getParentId());
                        parent.setAnonymous(parentComent.getIsRecommend()==0);
                        parent.setReplyId(parentComent.getCommentId());
                        parent.setText(parentComent.getText());
                        if(parentComent.getIsRecommend()!=0) {
                            parent.setUserId(parentComent.getCommentUserId());
                            UserDto parentUser = userCache.get(parentComent.getCommentUserId());
                            parent.setUsername(parentUser.getUsername());
                        }
                        else {
                            parent.setUserId(-1L);
                            parent.setUsername(parentComent.getFakeName());
                        }

                    }

                    replyArr.setParent(parent);
                    replyArrs.add(replyArr);

                }
                comment.setReplyArr(replyArrs);
            }
            comment.setCommenter(commenter);

            result.add(comment);

        }
        return result;
    }

    
//    @Override
//    public List<PersonalComment> getPersonalComment(Long userId,  int start, int pageSize) {
//        Page page = new Page(start,pageSize);
//        List<CommentDto> list = commentMapper.getCommentPagesByUserId(userId, page.getStartRow(), page.getPageCount());
//
//        List<PersonalComment> result = new ArrayList<>();
//        for(CommentDto firstComment : list) {
//        	PersonalComment comment = new PersonalComment();
//        	Long contentId=firstComment.getContentId();
//        	Long channelId=firstComment.getChannelId();
//        	ContentVo vo=new ContentVo();
//        	vo.setContentId(contentId);
//        	vo.setChannelId(channelId);
//        	if(channelId==75l) {
//        		ContentDto dto=contentMapper.getByContentId(contentId);
//        		vo.setText(dto.getTxt());
//        	}else {
//        		ContentDto dto=contentMapper.getNewsByContentId(contentId);
//        		vo.setText(dto.getTitle());
//        	}
//            comment.setContentVo(vo);
//            comment.setCommentId(firstComment.getCommentId());
//            comment.setText(firstComment.getText());
//            comment.setCreateTime(DateUtil.format(firstComment.getCreateTime(), DateUtil.timePattern));
//            comment.setReplyNum(firstComment.getReplyCount());
//            boolean parentAnonymous = false;
//            if(firstComment.getIsRecommend() == 0) {
//                parentAnonymous = true;
//            }
//            comment.setAnonymous(parentAnonymous);
//
//            
//            result.add(comment);
//        }
//        return result;
//    }
    
    @Override
    public void add(Long contentId, Long userId, Long parentCommentId, String text, int isrecommend) {
        //内容数目加1   内容的评论数加1讨论
        contentMapper.addComments(contentId);
        String fakeName="";
        if(isrecommend==0) {
        	fakeName=String.valueOf(contentId^userId);
        }
        Long ancestorId = null;
        long commentId = serialService.get(TableNameEnum.JC_COMMENT.getCode());
        CommentDto dto = new CommentDto();
        dto.setCommentId(commentId);
        dto.setCommentUserId(userId);
        dto.setContentId(contentId);
        dto.setSiteId(1l);
        dto.setParentId(parentCommentId);
        dto.setAncestorId(ancestorId);
        dto.setIsRecommend(isrecommend);
        dto.setFakeName(fakeName);
        
        //添加评论表
        if(!Objects.equals(parentCommentId, null)) {
            CommentDto parentComment = commentMapper.getCommentByParentId(parentCommentId);
            dto.setReplyUserId(parentComment.getCommentUserId());
            if(Objects.equals(parentComment.getAncestorId(), null)) {
                ancestorId = parentComment.getCommentId();
            } else {
                ancestorId = parentComment.getAncestorId();
            }
            
        }else {
        	ContentDto cd=contentMapper.getByContentId(contentId);
        	dto.setReplyUserId(cd.getAuthorId());
        }
        dto.setAncestorId(ancestorId);
        commentMapper.insert(dto);

        // 父类评论数加1
        if(!Objects.equals(parentCommentId, null)) {
            commentMapper.addComments(parentCommentId);

        }

        //添加评论内容表
        CommentExtDto commentExtDto = new CommentExtDto();
        commentExtDto.setCommentId(commentId);
        commentExtDto.setText(text);
      //remove sensitivity words
        List<SensitivityVo> sens=sensitivityService.getList();
        sens.forEach(sen->{
        	if(commentExtDto.getText().contains(sen.getSearch())){
        		commentExtDto.setText(commentExtDto.getText().replaceAll(sen.getSearch(), sen.getReplacement()));
        	}
        });
        commentExtMapper.insert(commentExtDto);


    }
    
    
    
    @Override
    public List<Comment> getCommentOnlyById(Long contentId, int start, int pageSize) {
        Page page = new Page(start,pageSize);
        List<CommentDto> list = commentMapper.getCommentPagesByContentId(contentId, page.getStartRow(), page.getPageCount());

        List<Comment> result = new ArrayList<>();
        for(CommentDto firstComment : list) {

            Comment comment = new Comment();
            comment.setCommentId(firstComment.getCommentId());
            comment.setText(firstComment.getText());
            comment.setCreateTime(DateUtil.format(firstComment.getCreateTime(), DateUtil.timePattern));
            comment.setReplyNum(firstComment.getReplyCount());
            boolean parentAnonymous = false;
            if(firstComment.getIsRecommend() == 0) {
                parentAnonymous = true;
            }
            comment.setAnonymous(parentAnonymous);

            UserDto nowUser = userCache.get(firstComment.getCommentUserId());
            //评论者的用户信息
            Commenter commenter = new Commenter();
            if(!parentAnonymous) {
            	commenter.setId(nowUser.getUserId());
            	commenter.setName(nowUser.getUsername());
                commenter.setImg(nowUser.getUserImg());
            }
            else {
            	commenter.setId(-1L);
            	commenter.setName(firstComment.getFakeName());
                commenter.setImg("");
            }
            comment.setCommenter(commenter);
            result.add(comment);
        }
        return result;
    }
}
