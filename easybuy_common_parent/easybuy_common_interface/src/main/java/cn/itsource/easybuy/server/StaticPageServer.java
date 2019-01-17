package cn.itsource.easybuy.server;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * 使用feign进行服务的负载均衡调用：暴露生成静态页面服务的接口
 */
@FeignClient(value = "EASYBUY-COMMON",fallbackFactory =StaticPageServerHystrixFallbackFactory.class )
public interface StaticPageServer {

    /**
     * @param map
     * map中应该有三个key-value
     * Object model数据
     * String templatePath模板路径
     * String staticPagePath生成的页面的路径
     */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    void createStaticPage(@RequestBody Map<String,Object> map);
}
