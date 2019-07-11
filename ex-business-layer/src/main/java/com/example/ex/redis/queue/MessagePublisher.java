package com.example.ex.redis.queue;

public interface MessagePublisher {

    void publish(final String message);
}