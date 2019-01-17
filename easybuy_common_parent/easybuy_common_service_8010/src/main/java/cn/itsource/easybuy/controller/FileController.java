package cn.itsource.easybuy.controller;

import cn.itsource.easybuy.util.AjaxResult;
import cn.itsource.easybuy.util.FastDfsApiOpr;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    //上传文件到分布式文件系统
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public AjaxResult uplodFile(@RequestParam(value = "file",required = true) MultipartFile file){
        try {
            //获取文件真实名称
            String fileName = file.getOriginalFilename();
            //获取文件类型（需要的后缀是不带点的，所以最后一个.索引+1）
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            //调用工具方法上传文件到分布式文件系统
            String filePath = FastDfsApiOpr.upload(file.getBytes(), fileType);
            //将上传保存的路径返回
            return AjaxResult.me().setSuccess(true).setMessage("上传成功").setResultObj(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上传失败！");
        }
    }

    //从分布式文件系统删除文件
    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public AjaxResult delFile(@RequestParam(value = "filePath",required = true) String filePath){
        try {
            //需要从filePath中获取group 与 id
            //group是第一个/到第二个/，先将第一个/及以前去掉
            String path = filePath.substring(filePath.indexOf("/") + 1);
            //获取group(第一个/之前)
            String group = path.substring(0, path.indexOf("/"));
            //获取id（第一个/之前）
            String id = path.substring(path.indexOf("/") + 1);
            System.out.println(group);
            System.out.println(id);
            FastDfsApiOpr.delete(group, id);
            return AjaxResult.me().setSuccess(true).setMessage("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败！");
        }
    }
}
