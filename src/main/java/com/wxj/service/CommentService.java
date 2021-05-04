package com.wxj.service;

import com.wxj.po.Comment;

import java.util.List;

/**
 * @Author: ONESTAR
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    Long countComment();

    Long countCommentByBlogId(Long blogId);
}
