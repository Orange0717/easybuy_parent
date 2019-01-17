package cn.itsource.easybuy.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis使用连接池：工具方法从连接池获取连接、释放链接回连接池
 */
public enum RedisPoolUtil {
    //枚举单例
    instance;
    private static JedisPool pool = null;

    //初始化连接池
    static {
        //创建连接池：先创建连接池配置对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //设置闲时最大连接数(最小连接数)
        poolConfig.setMaxIdle(2);
        //设置最大连接数
        poolConfig.setMaxTotal(20);
        //设置最大等待时间(从连接池获取连接的最大等待时间)
        poolConfig.setMaxWaitMillis(5*1000);
        //设置在每次获取连接的时候进行测试连接是否连接畅通
        poolConfig.setTestOnBorrow(true);
        //根据配置对象创建连接池：timeout是配置通过连接从Redis获取数据的最大等待时间
        pool = new JedisPool(poolConfig, "127.0.0.1", 6379, 5*1000, "123456");
    }

    //获取连接的方法
    public Jedis getResource(){
        return pool.getResource();
    }

    //关闭连接的方法
    public void closeResource(Jedis jedis){
        if (jedis != null) {
            jedis.close();
        }
    }

    //添加数据到缓存中的方法
    public void set(String key,String value){
        Jedis jedis = getResource();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }

    //从缓存中获取数据的方法
    public String get(String key){
        Jedis jedis = getResource();
        try {
            String data = jedis.get(key);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResource(jedis);
        }
    }
}
