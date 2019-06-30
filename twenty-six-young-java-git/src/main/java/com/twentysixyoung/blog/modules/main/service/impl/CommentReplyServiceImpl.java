package com.twentysixyoung.blog.modules.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twentysixyoung.blog.modules.main.entity.CommentReply;
import com.twentysixyoung.blog.modules.main.mapper.CommentReplyMapper;
import com.twentysixyoung.blog.modules.main.service.CommentReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply> implements CommentReplyService {

    @Override
    public List<CommentReply> queryCommentTree() {
        return null;
    }

    @Override
    public List<CommentReply> queryComments(String javaId) {
        LambdaQueryWrapper<CommentReply> query = new LambdaQueryWrapper<>();
        query.eq(CommentReply::getJavaId, javaId);
        try{
            return this.list(query);
        }catch(Exception e){
            e.fillInStackTrace();
            log.error("===========查询遇到问题了==========", e.getCause());
            return null;
        }
    }

    @Override
    public boolean saveComment(CommentReply commentReply) {
        if(commentReply != null){
            commentReply.setCreateTime(new Date());
            return this.save(commentReply);
        }
        return false;
    }
}
