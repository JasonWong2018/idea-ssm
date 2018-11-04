package com.edums.sys.controller;

import com.edums.common.utils.FileOperateUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * springmvc上传测试
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    private static Logger logger = Logger.getLogger(UploadController.class);

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request) {
        String savePath = "d:\\uploadtest";
        try{
            // 使用Apache文件上传组件处理文件上传步骤：
            // 1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            // 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项

            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                // 如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    // 解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    logger.info("name=="+name);
                }else {// 如果fileitem中封装的是上传文件
                    // 得到上传的文件名称
                    String filename = item.getName();
                    logger.info("上传文件名称=="+filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    File file = new File(savePath);
                    // 判断上传文件的保存目录是否存在
                    if (!file.exists() && !file.isDirectory()) {
                        logger.info("目录不存在，需要创建");
                        // 创建目录
                        file.mkdir();
                    }
                    FileOperateUtil.updateFile(savePath,file);
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return "SUCCESS";
    }

}
