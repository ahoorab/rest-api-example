package com.example.ex.model.entity.admin;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for persistence mapping. In order to re use code, each entity
 * should extend from here.
 * 
 * No constructor nor setter will be provided to set the ID. JPA should do it.
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3694939912453009291L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;
}
