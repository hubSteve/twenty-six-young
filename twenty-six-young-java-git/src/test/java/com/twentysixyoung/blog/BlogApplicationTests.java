package com.twentysixyoung.blog;

import com.twentysixyoung.blog.modules.main.entity.CommentReply;
import com.twentysixyoung.blog.modules.main.service.CommentReplyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.UUID;

public class BlogApplicationTests {

    @Autowired
    private CommentReplyService replyService;

    public static void testString(MyInterface myInterface){
        myInterface.print("hahahha");
    }

    public static void main(String[] args) {
        PrintTest printTest = new PrintTest();
        testString(System.out::println);
    }

    private static Comparator<String> compare(){
        return (a,b)-> b.length() - a.length();
    }

    @Test
    public void testForThreads(){
        long start = System.currentTimeMillis();
        CommentReply commentReply = new CommentReply();
        commentReply.setId(UUID.randomUUID().toString().replace("-", ""));
        commentReply.setCommentator("Steve");
        commentReply.setComment("comment");
        commentReply.setReplier("Jobs");
        commentReply.setReply("reply");
        commentReply.setAvatar("pic");
        commentReply.setJavaId("123321");
        replyService.save(commentReply);
        long end = System.currentTimeMillis();
        System.out.println("=====================================\n" +
                "插入20条数据只用了" + (end - start)/1000 + "秒\n" +
                "==============================================");
    }
}
