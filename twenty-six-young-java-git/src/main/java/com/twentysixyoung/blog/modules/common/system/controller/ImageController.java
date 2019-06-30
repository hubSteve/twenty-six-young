package com.twentysixyoung.blog.modules.common.system.controller;

import com.baidu.ueditor.ActionEnter;
import com.twentysixyoung.blog.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/image/handle")
public class ImageController {

    @Value("${26young.path.upload}")
    private String uploadPath;

    /**
     * <p>图片上传</p>
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result<?> upload(HttpServletRequest request, HttpServletResponse response) {
        Result<?> result = new Result<>();
        try {
            String contextPath = uploadPath;
            String fileName = null;
            String commonPath = "files";
            String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
            File file = new File(contextPath + File.separator + commonPath + File.separator + today);
            if (!file.exists()) {
                file.mkdirs(); // 创建文件根目录
            }
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile mf = multipartRequest.getFile("file");
            String originName = mf.getOriginalFilename(); // 获取文件名
            fileName = originName.substring(0, originName.lastIndexOf(".")) + "-"
                    + System.currentTimeMillis() + originName.substring(originName.indexOf("."));
            String savePath = file.getPath() + File.separator + fileName;
            File saveFile = new File(savePath);
            FileCopyUtils.copy(mf.getBytes(), saveFile);
            String dbPath = commonPath + File.separator + today + File.separator + fileName;
            if (dbPath.contains("\\")) {
                dbPath = dbPath.replace("\\", "/");
            }
            result.setMessage(dbPath);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            e.fillInStackTrace();
            log.error(e.getMessage(), e.getCause());
        }
        return result;
    }

    /**
     * <p>图片预览</p>
     * @param request
     * @param response
     */
    @RequestMapping(value = "/view/**", method = RequestMethod.GET)
    public void view(HttpServletRequest request, HttpServletResponse response) {
        String imagePath = picPathFormatter(request);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            imagePath = imagePath.replace("..", "");
            if (imagePath.endsWith(",")) {
                imagePath = imagePath.substring(0, imagePath.length() - 1);
            }
            response.setContentType("image/jpeg;charset=utf-8");
            String localPath = uploadPath;
            String imgUrl = localPath + File.separator + imagePath;
            inputStream = new BufferedInputStream(new FileInputStream(imgUrl));
            outputStream = response.getOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }
            response.flushBuffer();
        } catch (Exception e) {
            e.fillInStackTrace();
            log.error("预览图片失败" + e.getMessage());
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e.getCause());
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e.getCause());
                }
            }
        }
    }

    private static String picPathFormatter(final HttpServletRequest request){
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/exec", method = RequestMethod.GET)
    public String exec(HttpServletRequest request, HttpServletResponse response, String action){
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            String rootPath = request.getServletContext().getRealPath("/");
//            String rootPath = request.getServletContext().getRealPath("/");
            System.out.println("========================" + action);
            System.out.println("===================rootPath" + rootPath);
            return new ActionEnter(request, rootPath).exec();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping(value = "/uEditor", method = RequestMethod.POST)
    public Result<?> uploadGoodsPic(@RequestParam(value = "upfile") MultipartFile upfile) {
        Result<?> result = new Result<>();
        if (upfile == null) {
            result.setSuccess(false);
            result.setMessage("上传附件为空");
            return result;
        }
        try {
            InputStream ins = upfile.getInputStream();
            int size = ins.available();
            if (size <= 0) {
                result.setSuccess(false);
                result.setMessage("上传附件为空");
                return result;
            }
            String fileName = upfile.getOriginalFilename();
            System.out.println( "------------------" + fileName);
            return null;
        } catch (IOException e) {
            log.error("FileItem.getInputStream() error", e);
            result.setSuccess(false);
            result.setMessage("获取输入流异常");
            return result;
        }
    }
}
