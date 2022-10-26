package top.xc27.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    StringRedisTemplate redisTemplate;

    public void setString(String key, String value,int seconds) {
        redisTemplate.opsForValue().set(key, value,seconds,TimeUnit.SECONDS);
    }

    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean hasKey(String code) {return redisTemplate.hasKey(code);}

    public void clean(String key) {
        redisTemplate.delete(key);
    }
}
