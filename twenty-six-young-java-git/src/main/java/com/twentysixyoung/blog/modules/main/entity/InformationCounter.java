package com.twentysixyoung.blog.modules.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("information_counter")
public class InformationCounter implements Serializable {

    private static final long serialVersionUid = 1L;

    @TableId(type = IdType.UUID)
    private String id;

    private String ip;

    private String updateAuthor;

    private String articleId;

    private Integer likeCount;

    private Integer dislikeCount;

    private Integer commentCount;

    private Integer visitCount;

    private Date likeUpdateTime;

    private Date commentUpdateTime;
}
