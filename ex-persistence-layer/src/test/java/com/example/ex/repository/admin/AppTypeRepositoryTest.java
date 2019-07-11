package com.example.ex.repository.admin;

import static org.junit.Assert.fail;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.AppType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class AppTypeRepositoryTest extends BaseJpaTest<AppType, Integer> {
     
    @Autowired
    private AppTypeRepository appTypeRepository;
    
    @Override
    protected JpaRepository<AppType,Integer> getImplementationRepository() {
        return appTypeRepository;
    }

    @Override
    protected AppType getNewTestEntity() {
        return new AppType("ADMN",1,1,0,Status.ENABLED);
    }

    @Override
    protected void updateEntity(AppType entity) {
        entity.setName("RDIS");
        entity.setIsSymbolBased(0);
        entity.setIsSessionBased(0);
        entity.setNeedRefChannelOne(1);
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveAppTypeWithDuplicatedName() {
        AppType appType = getNewTestEntity();
        appType.setName("dup");
        
        try {
            appTypeRepository.save(appType);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        appType = new AppType();
        updateEntity(appType);
        appType.setName("dup");
        
        appTypeRepository.save(appType);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppTypeWithNullName() {
        AppType appType = getNewTestEntity();
        appType.setName(null);
        appTypeRepository.save(appType);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppTypeWithNameSizeGreaterThan4() {
        AppType appType = getNewTestEntity();
        appType.setName(RandomString.make(5));
        appTypeRepository.save(appType);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppTypeWithNullIsSymbolBased() {
        AppType appType = getNewTestEntity();
        appType.setIsSymbolBased(null);
        appTypeRepository.save(appType);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppTypeWithNullIsSessionBased() {
        AppType appType = getNewTestEntity();
        appType.setIsSessionBased(null);
        appTypeRepository.save(appType);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppTypeWithNullNeedRefChannel() {
        AppType appType = getNewTestEntity();
        appType.setNeedRefChannelOne(null);
        appTypeRepository.save(appType);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppTypeWithNullStatus() {
        AppType appType = getNewTestEntity();
        appType.setStatus(null);
        appTypeRepository.save(appType);
    }

}