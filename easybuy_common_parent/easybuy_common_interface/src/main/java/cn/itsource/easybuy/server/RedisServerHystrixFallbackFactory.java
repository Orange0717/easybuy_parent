package cn.itsource.easybuy.server;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component  //这个注解表示会被spring管理，它的层级在服务端入口类之下(创建项目要规范)，默认会被扫描
public class RedisServerHystrixFallbackFactory implements FallbackFactory<RedisServer> {

    //实现的接口FallbackFactory指定了接口UserService，表示这个类是给UserService同一配置托底方法
    @Override
    public RedisServer create(Throwable throwable) {
        return new RedisServer() {
            @Override
            public void setRedisCache(String key, String value) {
                //System.out.println("服务熔断");
            }

            @Override
            public String getRedisCache(String key) {
                return "服务熔断......";
            }
        };
    }
}