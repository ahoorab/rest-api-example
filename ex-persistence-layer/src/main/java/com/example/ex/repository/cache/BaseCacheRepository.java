package com.example.ex.repository.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class BaseCacheRepository<T> {
    
    private RedisTemplate<String, T> redisTemplate;
    private HashOperations<String, String, T> hashOperations;
    private ListOperations<String, T> listOperations;

    @Autowired
    public BaseCacheRepository(RedisTemplate<String, T> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
    }

    public Map<String, T> findAll(String key) {
        return hashOperations.entries(key);
    }
    
    public List<T> findValues(String key) {
        if (DataType.LIST.equals(getType(key))) {
            return listOperations.range(key, 0, -1);
        }
        return hashOperations.values(key);
    }

    public Set<String> getAllKeys(String prefix) {
        return redisTemplate.keys(prefix+"*");
    }

    public void cache(String key, String hashKey, T entity) {
        hashOperations.put(key, hashKey, entity);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public DataType getType(String key) {
        return redisTemplate.type(key);
    }

    public void clear(String key) {
        hashOperations.entries(key).keySet().forEach(hashKey -> hashOperations.delete(key, hashKey));
    }

}