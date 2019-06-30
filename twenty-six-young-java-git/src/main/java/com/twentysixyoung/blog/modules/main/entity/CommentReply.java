package com.twentysixyoung.blog.modules.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("comment_reply")
public class CommentReply implements Serializable {

    private static final long serialVersionUid = 1L;

    @TableId(type = IdType.UUID)
    private String id;

    private String javaId;

    private String comment;

    private String reply;

    private String replier;

    private String commentator;

    private String avatar;

    private Date createTime;
}
