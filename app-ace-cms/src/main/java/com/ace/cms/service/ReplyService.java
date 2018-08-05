package com.ace.cms.service;

import com.ace.cms.vo.comment.PersonalReply;
import com.ace.cms.vo.comment.ReceiveReply;
import com.ace.cms.vo.comment.ReplyVo;

import java.util.List;

public interface ReplyService {

    List<ReplyVo> getReplyArrById(Long contentId, Long ancestorId, int first, int count);

	List<PersonalReply> getReplyArrByUserId(Long userId, int first, int count);

	List<ReceiveReply> getReplyByUserId(Long userId, int first, int count);

}
