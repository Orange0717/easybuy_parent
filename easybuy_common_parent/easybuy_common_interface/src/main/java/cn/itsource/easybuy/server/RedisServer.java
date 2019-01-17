package cn.itsource.easybuy.server;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用feign进行服务的负载均衡调用：暴露缓存服务的接口
 */
@FeignClient(value = "EASYBUY-COMMON",fallbackFactory =RedisServerHystrixFallbackFactory.class )
public interface RedisServer {

    @RequestMapping(value = "/redis",method = RequestMethod.POST)
    void setRedisCache(@RequestParam("key") String key, @RequestParam("value") String value);

    @RequestMapping(value = "/redis",method = RequestMethod.GET)
    String getRedisCache(@RequestParam("key") String key);
}
