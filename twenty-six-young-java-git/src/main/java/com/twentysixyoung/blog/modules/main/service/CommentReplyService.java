package com.twentysixyoung.blog.modules.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.twentysixyoung.blog.modules.main.entity.CommentReply;

import java.util.List;

public interface CommentReplyService extends IService<CommentReply> {

    /**
     *
     * @return
     */
   List<CommentReply> queryCommentTree();

   boolean saveComment(CommentReply commentReply);

    List<CommentReply> queryComments(String javaId);
}
