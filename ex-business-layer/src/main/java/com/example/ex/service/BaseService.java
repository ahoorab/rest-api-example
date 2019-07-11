package com.example.ex.service;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.exception.EntityAlreadyExistsException;
import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.BaseEntity;

public abstract class BaseService<S extends BaseEntity> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);
    
    public abstract S save(S entity);

    public Optional<S> findById(Integer id) {
        //this is to allow filtering
        List<S> l = getRepository().findAllById(Arrays.asList(id));
        if (!l.isEmpty()) {
            return Optional.ofNullable(l.get(0));
        }
        return Optional.empty();
    }

    public abstract void deleteById(Integer id);

    public List<S> findAll() {
        return getRepository().findAll();
    }
    
    protected abstract JpaRepository<S, Integer> getRepository();
    
    private void checkNull(S entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Cannot validate a null entity");
        }
    }
    
    private void validateUnique(S entity, S found, String... attributes) {
        LOGGER.debug("checking if {} <{}> exists",entity.getClass().getSimpleName(),attributes);
        if (found != null && entity.getId() != found.getId()) {
            StringBuilder buffer = concatenateAttributes(attributes);
            LOGGER.debug("{} {} exists, cannot save", entity.getClass(),buffer);
            throw new EntityAlreadyExistsException(entity.getClass().getSimpleName() + " <" + buffer + "> already exists");
        }
    }

    private <T extends BaseEntity> void validateDependency(T found, Class<T> clazz, String... attributes) {
        LOGGER.debug("checking if fk {} <{}> exists",clazz.getSimpleName(),attributes);
        if (found == null) {
            StringBuilder buffer = concatenateAttributes(attributes);
            LOGGER.debug("{} {} does not exists, throwing exception",clazz.getSimpleName(),buffer);
            throw new EntityNotFoundException(clazz.getSimpleName() + " <" + buffer + "> does not exists");
        }
    }
    
    private StringBuilder concatenateAttributes(String... attributes) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<");
        for (int i = 0; i < attributes.length; i++) {
            buffer.append(attributes[i]);
            if (i < attributes.length - 1) {
                buffer.append(",");
            }
        }
        buffer.append(">");
        return buffer;
    }

    public void validateUnique(Function<String,S> findByAttribute, S entity, String attribute, boolean nullable) {
        if (nullable && attribute == null) {
            return;
        }
        
        checkNull(entity);

        S found = findByAttribute.apply(attribute);
        validateUnique(entity,found,attribute);
    }
    
    public void validateUnique(BiFunction<String,String,S> findByAttribute, S entity, String attribute1, String attribute2) {
        checkNull(entity);
        S found = findByAttribute.apply(attribute1,attribute2);
        validateUnique(entity,found,attribute1,attribute2);
    }
    
    public void validateUnique(BiFunction<Date,String,S> findByAttribute, S entity, Date attribute1, String attribute2) {
        checkNull(entity);
        S found = findByAttribute.apply(attribute1,attribute2);
        validateUnique(entity,found,attribute1.toString(),attribute2);
    }
    
    public void validateUnique(BiFunction<String,Date,S> findByAttribute, S entity, String attribute1, Date attribute2) {
        checkNull(entity);
        S found = findByAttribute.apply(attribute1,attribute2);
        validateUnique(entity,found,attribute1,attribute2.toString());
    }
    
    public <T extends BaseEntity> void validateDependency(Function<String, T> findByAttribute, Class<T> clazz, String attribute, boolean nullable) {
        if (nullable && attribute == null) {
            return;
        }
        T found = findByAttribute.apply(attribute);
        validateDependency(found,clazz,attribute);
    }
    
    public <T extends BaseEntity> void validateDependency(BiFunction<String,String,T> findByAttribute, Class<T> clazz, String attribute1, String attribute2) {
        T found = findByAttribute.apply(attribute1,attribute2);
        validateDependency(found,clazz,attribute1,attribute2);
    }
    
    public void validateUnique(Function<String, S> findByAttribute, S entity, String attribute) {
        validateUnique(findByAttribute,entity,attribute,false);
    }

    public <T extends BaseEntity> void validateDependency(Function<String, T> findByAttribute, Class<T> clazz, String attribute)  {
        validateDependency(findByAttribute,clazz,attribute,false);
    }

    
}