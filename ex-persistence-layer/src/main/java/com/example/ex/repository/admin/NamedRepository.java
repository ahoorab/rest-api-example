package com.example.ex.repository.admin;

import com.example.ex.model.entity.admin.BaseEntity;

public interface NamedRepository<T extends BaseEntity> {

    T findByName(String name);

}
