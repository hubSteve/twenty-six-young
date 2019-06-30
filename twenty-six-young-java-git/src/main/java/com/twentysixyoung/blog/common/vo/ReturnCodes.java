package com.twentysixyoung.blog.common.vo;

public class ReturnCodes {
    /**
     * This static variable represents the status that some data has been successful updated,
     * when a process executed without any exceptions being thrown, you can return this code;
     * */
    public static final Integer UPDATE_SUCCESSFUL = 100;

    public static final Integer UPDATE_FAILED = 0;

    public static final Integer USER_ALREADY_LIKE = 1;

    public static final Integer USER_ALREADY_DISLIKE = 2;

    public static final Integer SAVE_SUCCESSFUL = 10;

    public static final Integer SAVE_FAILED = 4;

}
