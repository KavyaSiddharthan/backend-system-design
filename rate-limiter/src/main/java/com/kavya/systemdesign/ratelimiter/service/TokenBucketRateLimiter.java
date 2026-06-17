package com.kavya.systemdesign.ratelimiter.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TokenBucketRateLimiter {
    private final StringRedisTemplate redisTemplate;
    private final RedisScript<Long> rateLimitScript;

    @Value("${rate-limiter.capacity}")
    private String capacity;
    @Value("${rate-limiter.refill-tokens}")
    private String refillTokens;
    @Value("${rate-limiter.refill-period-seconds}")
    private String refillPeriodSeconds;

    public boolean isAllowed(String key) {
        String nowStr = String.valueOf(Instant.now().getEpochSecond());
        Long result = redisTemplate.execute(
                rateLimitScript,
                Collections.singletonList("rate_limit:" + key),
                capacity, refillTokens, refillPeriodSeconds, "1", nowStr
        );
        return result != null && result == 1L;
    }
}
