package com.twentysixyoung.blog.modules.common.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UEditorController {



    protected final Logger logger = LoggerFactory.getLogger(getClass());


//    @RequestMapping(value = { "/ueditor/config" }, method = {
//            RequestMethod.POST, RequestMethod.GET })
//    @ResponseBody
//    public Object ueditorConfig(
//            @RequestParam(value = "action", required = false) String action,
//            HttpServletRequest request, HttpServletRequest response)
//            throws Exception {
//        logger.info("百度编辑器action=[{}]", action);
//        if ("config".equals(action)) {
//            UeditorConfigBean ucb = new UeditorConfigBean();
//            return ucb;
//        } else if ("/ueditor/uploadImg".equals(action)) {// 图片上传
//            return uploadImg(((MultipartHttpServletRequest) request)
//                    .getFile("upfile"));
//        }
//        return action;
//    }

}
