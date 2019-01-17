package cn.itsource.easybuy.server;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component  //这个注解表示会被spring管理，它的层级在服务端入口类之下(创建项目要规范)，默认会被扫描
public class StaticPageServerHystrixFallbackFactory implements FallbackFactory<StaticPageServer> {

    //实现的接口FallbackFactory指定了接口UserService，表示这个类是给UserService同一配置托底方法
    @Override
    public StaticPageServer create(Throwable throwable) {
        return new StaticPageServer() {
            @Override
            public void createStaticPage(Map<String, Object> map) {
                //System.out.println("服务熔断");
            }
        };
    }
}
