package com.example.ex.repository.admin;

public interface BaseRepository<T> {
    
    T findByHandle(String handle);

}
