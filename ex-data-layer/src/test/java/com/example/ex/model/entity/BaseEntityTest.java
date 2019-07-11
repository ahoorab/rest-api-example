package com.example.ex.model.entity;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;

public abstract class BaseEntityTest {
    
   protected Validator validator;
    
   @Before
   public void setUp() {
       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
       validator = factory.getValidator();
   }

}
