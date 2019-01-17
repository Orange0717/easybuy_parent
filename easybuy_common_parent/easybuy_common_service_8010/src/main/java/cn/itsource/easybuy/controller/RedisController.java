package cn.itsource.easybuy.controller;

import cn.itsource.easybuy.server.RedisServer;
import cn.itsource.easybuy.util.RedisPoolUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController implements RedisServer {

    @RequestMapping(value = "/redis",method = RequestMethod.POST)
    @Override
    public void setRedisCache(@RequestParam("key")String key,@RequestParam("value") String value) {
        RedisPoolUtil.instance.set(key, value);
    }

    @RequestMapping(value = "/redis",method = RequestMethod.GET)
    @Override
    public String getRedisCache(@RequestParam("key")String key) {
        return RedisPoolUtil.instance.get(key);
    }
}
