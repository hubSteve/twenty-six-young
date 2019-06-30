package com.twentysixyoung.blog.modules.main.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


@Data
public class CommentReplyModel {

    @TableId(type = IdType.UUID)
    private String id;

    private String javaId;

    private String author;

    private String comment;

    private String reply;

    private String replier;

}
