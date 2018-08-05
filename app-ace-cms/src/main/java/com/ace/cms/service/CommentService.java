package com.ace.cms.service;

import com.ace.cms.vo.comment.Comment;
import com.ace.cms.vo.comment.PersonalComment;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentById(Long contentId, boolean onlyRoot, int start, int pageSize);

    void add(Long contentId, Long userId, Long parentCommentId, String text,int isrecommend);

	List<Comment> getCommentOnlyById(Long contentId, int first, int count);

	//List<PersonalComment> getPersonalComment(Long userId, int first, int count);

}
