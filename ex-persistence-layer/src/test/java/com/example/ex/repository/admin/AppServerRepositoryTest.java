package com.example.ex.repository.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.AppServer;

import net.bytebuddy.utility.RandomString;

public class AppServerRepositoryTest extends BaseJpaTest<AppServer, Integer> {

    @Autowired
    private AppServerRepository appServerRepository;

    @Override
    protected JpaRepository<AppServer, Integer> getImplementationRepository() {
        return appServerRepository;
    }

    @Override
    protected AppServer getNewTestEntity() {
        return new AppServer("fo", "172.31.45.24", "34.199.149.130", "fo.dev", "dev-ex-fo.exx.nyc",
                "dev-ex-cg.exx.nyc", 1, 1, 1, "empy note", 1);
    }

    @Override
    protected void updateEntity(AppServer entity) {
        entity.setHandle("bo");
        entity.setInternalIp("172.31.33.42");
        entity.setExternalIp("34.203.165.205");
        entity.setHostname("bo.dev");
        entity.setUri("dev-ex-bo.exx.nyc");
        entity.setUriCg("dev-ex-bo2.exx.nyc");
        entity.setIsCore(0);
        entity.setIsEdge(0);
        entity.setIsBo(0);
        entity.setNotes("test note");
        entity.setIsActive(0);
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveAppServerWithDuplicatedHandle() {
        AppServer appServer = getNewTestEntity();
        appServer.setHandle("dup");
        
        try {
            appServerRepository.save(appServer);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        appServer = new AppServer();
        updateEntity(appServer);
        appServer.setHandle("dup");
        
        appServerRepository.save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithNullHandle() {
        AppServer appServer = getNewTestEntity();
        appServer.setHandle(null);
        appServerRepository.save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithHandleSizeGreaterThan12() {
        AppServer appServer = getNewTestEntity();
        appServer.setHandle(RandomString.make(13));
        appServerRepository.save(appServer);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveAppServerWithDuplicatedInternalIp() {
        AppServer appServer = getNewTestEntity();
        appServer.setInternalIp("dup");
        
        try {
            appServerRepository.save(appServer);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        appServer = new AppServer();
        updateEntity(appServer);
        appServer.setInternalIp("dup");
        
        appServerRepository.save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithNullInternalIp() {
        AppServer appServer = getNewTestEntity();
        appServer.setInternalIp(null);
        appServerRepository.save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithInternalIpSizeGreaterThan16() {
        AppServer appServer = getNewTestEntity();
        appServer.setInternalIp(RandomString.make(17));
        appServerRepository.save(appServer);
    }
    
    @Test
    public void shouldSaveAppServerWithNullExternalIp() {
        AppServer appServer = getNewTestEntity();
        appServer.setExternalIp(null);
        
        AppServer savedAppServer = appServerRepository.save(appServer);
        
        assertThat(savedAppServer).isEqualTo(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithExternalIpSizeGreaterThan16() {
        AppServer appServer = getNewTestEntity();
        appServer.setExternalIp(RandomString.make(17));
        appServerRepository.save(appServer);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveAppServerWithDuplicatedHostname() {
        AppServer appServer = getNewTestEntity();
        appServer.setHostname("dup");
        
        try {
            appServerRepository.save(appServer);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        appServer = new AppServer();
        updateEntity(appServer);
        appServer.setHostname("dup");
        
        appServerRepository.save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithNullHostname() {
        AppServer appServer = getNewTestEntity();
        appServer.setHostname(null);
        appServerRepository.save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithHostnameSizeGreaterThan32() {
        AppServer appServer = getNewTestEntity();
        appServer.setHandle(RandomString.make(33));
        appServerRepository.save(appServer);
    }
    
    @Test
    public void shouldSaveAppServerWithNullUri() {
        AppServer appServer = getNewTestEntity();
        appServer.setUri(null);
        
        AppServer savedAppServer = appServerRepository.save(appServer);
        
        assertThat(savedAppServer).isEqualTo(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithUriSizeGreaterThan32() {
        AppServer appServer = getNewTestEntity();
        appServer.setUri(RandomString.make(33));
        appServerRepository.save(appServer);
    }
    
    @Test
    public void shouldSaveAppServerWithNullUriCg() {
        AppServer appServer = getNewTestEntity();
        appServer.setUriCg(null);
        
        AppServer savedAppServer = appServerRepository.save(appServer);
        
        assertThat(savedAppServer).isEqualTo(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithUriCgSizeGreaterThan32() {
        AppServer appServer = getNewTestEntity();
        appServer.setUriCg(RandomString.make(33));
        appServerRepository.save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithNullIsCore() {
        AppServer appServer = getNewTestEntity();
        appServer.setIsCore(null);
        appServerRepository.save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithNullIsEdge() {
        AppServer appServer = getNewTestEntity();
        appServer.setIsEdge(null);
        appServerRepository.save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithNullIsBo() {
        AppServer appServer = getNewTestEntity();
        appServer.setIsBo(null);
        appServerRepository.save(appServer);
    }
    
    @Test
    public void shouldSaveAppServerWithNullNotes() {
        AppServer appServer = getNewTestEntity();
        appServer.setNotes(null);
        
        AppServer savedAppServer = appServerRepository.save(appServer);
        
        assertThat(savedAppServer).isEqualTo(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithNotesSizeGreaterThan128() {
        AppServer appServer = getNewTestEntity();
        appServer.setNotes(RandomString.make(129));
        appServerRepository.save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithNullIsActive() {
        AppServer appServer = getNewTestEntity();
        appServer.setIsActive(null);
        appServerRepository.save(appServer);
    }
    
}