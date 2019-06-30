package com.twentysixyoung.blog;

import freemarker.template.utility.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;
import java.awt.print.Printable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ToStringTest implements Runnable {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    static int i = 1;
    private int ticket = 100;
    Object lock = new Object();
    Lock lo = new ReentrantLock();
/*    public static void main(String args[]){
        System.out.println("love " + new ToStringTest());
        ToStringTest a = new ToStringTest();
        a.i++;
        System.out.println("me " + a.i);
    }*/
    public String toString(){
        System.out.print("I ");
        return "java ";
    }

    @Test
    public void test1ForString(){
        String s1 = "ab";
        String s2 = "abc";
        String s3 = s1 + "c";
        System.out.println(s2.equals(s3));
    }
    @Test
    public void test2ForDate(){
        /**
         *2088-08-08 08:08:08。
         */
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        System.out.println(format);
    }

    @Test
    public void test2ForCalendar() throws ParseException{
        String time = "2019-03-14";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int dayOfWeek = instance.get(instance.DAY_OF_WEEK);
        System.out.println("2018-02-14是星期" + (dayOfWeek - 1));
    }
    @Override
    public void run() {
        while (true){
            lo.lock();
                if(ticket > 0){
                    try{
                        Thread.sleep(0);
                    }catch(InterruptedException ie){
                        ie.fillInStackTrace();
                    }
                    String name  = Thread.currentThread().getName();
                    System.out.println("机票还剩" + (ticket--));
                }
                lo.unlock();
        }
    }
    @Test
    public void testForSynchronization(){
        new ToStringTest().run();
    }
    @Test
    public void testForPlay(){
        int row = 4;
        int length = 15;
        for (int j = 0, len = 15; j < len; j++) {
            int index = (length - 1) / 2 + 1;
            for (int k = 0; k < length; k++) {
                if(k == index && j == 0){
                    System.out.print("*");
                }else if(j == row && k >= j && k <= j * j - 4){
                    System.out.print("*");
                }else if( (k >= index - j && k <= index + j) && j < row){
                    System.out.print("*");
                }else if(j <= row){
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }

    @Test
    public void testForNumber(){
        int a = 0, b= 0, c = 0, d = 0;
        int ab = 0, cd = 0, abcd = 0;
        while (true){
            a = new Random().nextInt(10);
            b = new Random().nextInt(10);
            c = new Random().nextInt(10);
            d = new Random().nextInt(10);
            ab = Integer.valueOf(new StringBuilder().append(a).append(b).toString());
            cd = Integer.valueOf(new StringBuilder().append(c).append(d).toString());
            abcd = Integer.valueOf(new StringBuilder().append(a).append(b).append(c).append(d).toString());
            if((ab + cd) * (ab + cd) == abcd){
                System.out.println(abcd);
                break;
            }
        }
    }

    @Test
    public void testForNarcissisticNumber(){
        int a = 0, b = 0, c = 0;
        int abc = 0;
        while (true){
            a = (new Random().nextInt(901) + 100) / 100;
            b = (new Random().nextInt(901) + 100) / 10 % 10;
            c = (new Random().nextInt(901) + 100) % 100 % 10;
            abc = Integer.valueOf(new StringBuilder().append(a).append(b).append(c).toString());
            if (a * a * a + b * b * b + c * c * c == abc) {
                System.out.println(abc);
            }
        }
    }

    @Test
    public void testForRedis(){
        Map<String, Object> map = new HashMap<>();
        map.put("a","尽快恢复上课的机会");
        redisTemplate.opsForList().leftPush("map", map);
    }

    @Test
    public void testForRedisGet(){
        Map<String, Object> test = (Map<String, Object>)redisTemplate.opsForList().leftPop("map");
        System.out.println("===========================" + test);
    }
}
