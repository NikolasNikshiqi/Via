package org.spring.via.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class BlacklistTokens {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public BlacklistTokens( RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    public void blacklistToken(String token, Date expiration){
        long ttl = expiration.getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set(
            "blackl:" + token,
                "invalid",
                ttl,
                TimeUnit.MILLISECONDS
        );
    }

    public boolean isBlacklisted(String token){
        return redisTemplate.hasKey("blackl:" + token);
    }

}
