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

import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.model.entity.admin.AppServer;
import com.example.ex.model.entity.admin.BaseEntity;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class AppJvmRepositoryTest extends BaseJpaTest<AppJvm, Integer> {

    @Autowired
    private AppJvmRepository appJvmRepository;
    
    @Override
    protected JpaRepository<AppJvm, Integer> getImplementationRepository() {
        return appJvmRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new AppServer("fo", "172.31.45.24", "34.199.149.130", "fo.dev", "dev-ex-fo.exx.nyc", "dev-ex-cg.exx.nyc", 1, 1, 1, "empy note", 1));
        dependencies.add(new AppServer("bo", "172.31.33.42", "34.203.165.205", "bo.dev", "dev-ex-bo.exx.nyc", "dev-ex-bo2.exx.nyc", 1, 1, 1, "empy note", 1));

        return dependencies;
    }
    
    @Override
    protected AppJvm getNewTestEntity() {
        return new AppJvm("seqr","fo","seqr",1,"8G","nose","test","INFO",Status.ENABLED);
    }

    @Override
    protected void updateEntity(AppJvm entity) {
        entity.setHandle("seqr2");
        entity.setServer("bo");
        entity.setLaunchName("seqr2");
        entity.setLaunchSeq(2);
        entity.setXmem("12BG");
        entity.setJvmthreads("1000");
        entity.setNotes("updated entity");
        entity.setLogWait("DEBUG");
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveAppJvmWithDuplicatedHandle() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setHandle("dup");
        
        try {
            appJvmRepository.save(appJvm);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        appJvm = new AppJvm();
        updateEntity(appJvm);
        appJvm.setHandle("dup");
        
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithNullHandle() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setHandle(null);
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithHandleSizeGreaterThan12() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setHandle(RandomString.make(13));
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithNullServer() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setServer(null);
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithServerSizeGreaterThan12() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setHandle(RandomString.make(13));
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveAppJvmWithDuplicatedLaunchName() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setLaunchName("dup");
        
        try {
            appJvmRepository.save(appJvm);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        appJvm = new AppJvm();
        updateEntity(appJvm);
        appJvm.setLaunchName("dup");
        
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithNullLaunchName() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setLaunchName(null);
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithLaunchNameSizeGreaterThan8() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setLaunchName(RandomString.make(9));
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithNullLaunchSeq() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setLaunchSeq(null);
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithNullXmem() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setXmem(null);
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithXmemSizeGreaterThan8() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setXmem(RandomString.make(9));
        appJvmRepository.save(appJvm);
    }
    
    @Test
    public void shouldSaveAppJvmWithNullJvmthreads() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setJvmthreads(null);
        AppJvm savedAppJvm = appJvmRepository.save(appJvm);
        
        assertThat(savedAppJvm).isEqualTo(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithJvmthreadsSizeGreaterThan12() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setJvmthreads(RandomString.make(13));
        appJvmRepository.save(appJvm);
    }
    
    @Test
    public void shouldSaveAppJvmWithNullNotes() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setNotes(null);
        AppJvm savedAppJvm = appJvmRepository.save(appJvm);
        
        assertThat(savedAppJvm).isEqualTo(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithNotesSizeGreaterThan128() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setNotes(RandomString.make(129));
        appJvmRepository.save(appJvm);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithNullLogWait() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setLogWait(null);
        appJvmRepository.save(appJvm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithLogWaitSizeGreaterThan128() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setLogWait(RandomString.make(129));
        appJvmRepository.save(appJvm);
    }
    
    @Test
    public void shouldSaveAppJvmWithNullStatus() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setStatus(null);
        AppJvm savedAppJvm = appJvmRepository.save(appJvm);
        
        assertThat(savedAppJvm).isEqualTo(appJvm);
    }
    
}