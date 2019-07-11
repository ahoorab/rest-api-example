package com.example.ex.repository.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.AppInstance;
import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.model.entity.admin.AppServer;
import com.example.ex.model.entity.admin.AppType;
import com.example.ex.model.entity.admin.BaseEntity;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class AppInstanceRepositoryTest extends BaseJpaTest<AppInstance, Integer> {

    @Autowired
    protected AppInstanceRepository appInstanceRepository;
    
    @Override
    protected JpaRepository<AppInstance, Integer> getImplementationRepository() {
        return appInstanceRepository;
    }

    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new AppType("ADMN",1,1,0,Status.ENABLED));
        dependencies.add(new AppType("RDIS",1,1,0,Status.ENABLED));
        dependencies.add(new AppServer("fo", "172.31.45.24", "34.199.149.130", "fo.dev", "dev-ex-fo.exx.nyc", "dev-ex-cg.exx.nyc", 1, 1, 1, "empy note", 1));
        dependencies.add(new AppJvm("SEQR","fo","SEQR",1,"8G","nose","test","INFO",Status.ENABLED));
        dependencies.add(new AppJvm("SEQR2","fo","SEQR2",1,"8G","nose","test","INFO",Status.ENABLED));
        dependencies.add(new AppJvm("EXCH","fo","EXCH",1,"8G","nose","test","INFO",Status.ENABLED));

        return dependencies;
    }
    
    @Override
    protected AppInstance getNewTestEntity() {
        return new AppInstance("CLGW01","ADMN",-1,"SEQR","SEQR2",10,15,Status.ENABLED);
    }

    @Override
    protected void updateEntity(AppInstance entity) {
        entity.setAppId("BOOT01");
        entity.setAppType("RDIS");
        entity.setThreadNumber(1);
        entity.setFirstJvm("EXCH");
        entity.setSecondJvm("SEQR");
        entity.setFirstSequence(127);
        entity.setSecondSequence(0);
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveAppInstanceWithDuplicatedAppId() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setAppId("dup");
        
        try {
            appInstanceRepository.save(appInstance);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        appInstance = new AppInstance();
        updateEntity(appInstance);
        appInstance.setAppId("dup");
        
        appInstanceRepository.save(appInstance);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithNullAppId() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setAppId(null);
        appInstanceRepository.save(appInstance);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithAppIdSizeGreaterThan6() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setAppId(RandomString.make(7));
        appInstanceRepository.save(appInstance);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithNullAppType() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setAppType(null);
        appInstanceRepository.save(appInstance);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithAppTypeSizeGreaterThan4() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setAppType(RandomString.make(5));
        appInstanceRepository.save(appInstance);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithNullThreadNumber() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setThreadNumber(null);
        appInstanceRepository.save(appInstance);
    }
    
    @Test
    public void shouldSaveAppInstanceWithNullFirstJvm() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setFirstJvm(null);
        
        AppInstance savedAppInstance = appInstanceRepository.save(appInstance);
        
        assertThat(savedAppInstance).isEqualTo(appInstance);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithFirstJvmGreaterThan12() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setFirstJvm(RandomString.make(13));
        appInstanceRepository.save(appInstance);
    }
    
    @Test
    public void shouldSaveAppInstanceWithNullSecondJvm() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setSecondJvm(null);
        
        AppInstance savedAppInstance = appInstanceRepository.save(appInstance);
        
        assertThat(savedAppInstance).isEqualTo(appInstance);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithSecondJvmGreaterThan12() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setSecondJvm(RandomString.make(13));
        appInstanceRepository.save(appInstance);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithNullFirstSequence() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setFirstSequence(null);
        appInstanceRepository.save(appInstance);
    }
    
    @Test
    public void shouldSaveAppInstanceWithNullSecondSequence() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setSecondSequence(null);
        
        AppInstance savedAppInstance = appInstanceRepository.save(appInstance);
        
        assertThat(savedAppInstance).isEqualTo(appInstance);
    }
    
    @Test
    public void shouldSaveAppInstanceWithNullStatus() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setStatus(null);
        AppInstance savedAppInstance = appInstanceRepository.save(appInstance);
        
        assertThat(savedAppInstance).isEqualTo(appInstance);
    }

}