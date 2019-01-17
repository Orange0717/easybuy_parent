package cn.itsource.easybuy.controller;

import cn.itsource.easybuy.server.StaticPageServer;
import cn.itsource.easybuy.util.VelocityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StaticPageController implements StaticPageServer {

    @Override
    public void createStaticPage(@RequestBody Map<String, Object> map) {
        // model 数据
        Object model = map.get("model");
        // tmeplatePath==xxx
        String tmeplatePath = (String) map.get("templatePath");
        // staticPagePath = xxx
        String staticPagePath = (String) map.get("staticPagePath");
        //打印测试数据是否可以拿到
        System.out.println(model);
        System.out.println(tmeplatePath);
        System.out.println(staticPagePath);

        //调用util工具进行静态页面生成
        VelocityUtils.staticByTemplate(model, tmeplatePath, staticPagePath);
    }
}
