package com.twentysixyoung.blog.modules.main.vo;

import com.twentysixyoung.blog.modules.main.entity.Article;
import com.twentysixyoung.blog.modules.main.entity.CommentReply;
import lombok.Data;

@Data
public class ArticleAndCommentsVo {

    private Article article;

    private CommentReply commentReply;
}