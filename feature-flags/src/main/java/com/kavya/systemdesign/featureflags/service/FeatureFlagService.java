package com.kavya.systemdesign.featureflags.service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeatureFlagService {
    private final StringRedisTemplate redisTemplate;
    private static final String FLAG_PREFIX = "feature_flag:";

    public boolean isFeatureEnabled(String featureName) {
        String val = redisTemplate.opsForValue().get(FLAG_PREFIX + featureName);
        return "true".equalsIgnoreCase(val);
    }

    public void setFeatureFlag(String featureName, boolean isEnabled) {
        redisTemplate.opsForValue().set(FLAG_PREFIX + featureName, String.valueOf(isEnabled));
    }
}
