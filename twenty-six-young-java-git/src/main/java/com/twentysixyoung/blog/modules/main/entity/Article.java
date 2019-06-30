package com.twentysixyoung.blog.modules.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;
@Data
@TableName("article")
public class Article implements Serializable{

    private static final long serialVersionUid = 1L;

    @TableId(type = IdType.UUID)
    private String id;

    private String title;

    private String picPath;

    private String categoryId;

    private String createAuthor;

    private String contact;

    private String description;

    private String content;

    private Date createTime;

    private Date updateTime;

    private Integer viewCount;

    private String updateAuthor;

    private String href;

    private String avatar;

    private Integer likeCount;

    private Integer dislikeCount;

    private Integer commentCount;

    private String articleTags;

    private String articleType;

    private String blogType;

}
