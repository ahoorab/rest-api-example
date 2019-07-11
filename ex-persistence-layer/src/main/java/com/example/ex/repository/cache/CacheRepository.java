package com.example.ex.repository.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CacheRepository extends BaseCacheRepository<String> {

    public CacheRepository(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

}   
