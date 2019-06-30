package com.twentysixyoung.blog.common.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    private static final SimpleDateFormat dateFormatNoHMS = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dateFormatHasHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");

    public static Date getTimeWithNoHMS(){
        try{
            String todayStr = dateFormatNoHMS.format(new Date());
            return dateFormatNoHMS.parse(todayStr);
        }catch (ParseException p){
            return null;
        }
    }

    public static Date getTimeWithHMS(){
        try{
            String todayStr = dateFormatHasHMS.format(new Date());
            return dateFormatHasHMS.parse(todayStr);
        }catch (ParseException p){
            return null;
        }
    }
}
