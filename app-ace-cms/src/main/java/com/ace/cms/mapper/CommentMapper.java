package com.ace.cms.mapper;

import com.ace.cms.dto.CommentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {

	List<CommentDto> getCommentPagesByContentId(@Param("contentId") Long contentId,
										   @Param("start") Integer start,
										   @Param("pageSize") Integer pageSize);

	List<CommentDto> getCommentByAncestorId(@Param("contentId") Long contentId,
										   @Param("commentId") Long commentId,
											@Param("start") Integer start,
											@Param("pageSize") Integer pageSize);

	List<CommentDto> getAllCommentByAncestorId(@Param("contentId") Long contentId,
											@Param("commentId") Long commentId);

	CommentDto getCommentByParentId(@Param("parentId") Long parentId);

	int insert(CommentDto dto);

	int addComments(@Param("commentId") Long commentId);

	List<CommentDto> getCommentPagesByUserId(@Param("userId") Long userId,
			   @Param("start") Integer start,
			   @Param("pageSize") Integer pageSize);

	List<CommentDto> getCommentByUserId(@Param("userId") Long userId,
			   @Param("start") Integer start,
			   @Param("pageSize") Integer pageSize);

	List<CommentDto> getReplyByUserId(@Param("userId") Long userId,
			   @Param("start") Integer start,
			   @Param("pageSize") Integer pageSize);
}
