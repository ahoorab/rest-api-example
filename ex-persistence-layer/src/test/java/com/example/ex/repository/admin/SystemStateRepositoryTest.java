package com.example.ex.repository.admin;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.BaseEntity;
import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.entity.admin.SystemState;
import com.example.ex.model.type.Status;
import com.example.ex.model.type.SystemStatus;
import com.example.ex.repository.admin.SystemStateRepository;

import net.bytebuddy.utility.RandomString;

public class SystemStateRepositoryTest extends BaseJpaTest<SystemState, Integer> {

    @Autowired
    private SystemStateRepository systemStateRepository;

    @Override
    protected JpaRepository<SystemState, Integer> getImplementationRepository() {
        return systemStateRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new Currency("CAD", 1, true, 0, 1, 1, Status.ENABLED));
        dependencies.add(new Currency("USD", 1, true, 0, 1, 1, Status.ENABLED));

        return dependencies;
    }
    
    @Override
    protected SystemState getNewTestEntity() {
        return new SystemState("USD",new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),SystemStatus.NONE,new Time(System.currentTimeMillis()),new Time(System.currentTimeMillis()));
    }

    @Override
    protected void updateEntity(SystemState entity) {
        entity.setBaseCcy("CAD");
        entity.setTradeDate(new Date(System.currentTimeMillis()));
        entity.setPrevTradeDate(new Date(System.currentTimeMillis()));
        entity.setSystemState(SystemStatus.OFF);
        entity.setMarketOpenTime(new Time(System.currentTimeMillis()));
        entity.setMarketCloseTime(new Time(System.currentTimeMillis()));
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveSystemStateWithNullBaseCcy() {
        SystemState systemState = getNewTestEntity();
        systemState.setBaseCcy(null);
        systemStateRepository.save(systemState);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveSystemStateWithBaseCcySizeGreaterThan3() {
        SystemState systemState = getNewTestEntity();
        systemState.setBaseCcy(null);
        systemState.setBaseCcy(RandomString.make(4));
        systemStateRepository.save(systemState);
    }
    
    @Test
    public void shouldSaveSystemStateWithNullTradeDate() {
        SystemState systemState = getNewTestEntity();
        systemState.setTradeDate(null);
        SystemState savedSystemState = systemStateRepository.save(systemState);
        
        assertThat(savedSystemState).isEqualTo(systemState);
    }

    @Test
    public void shouldSaveSystemStateWithNullPrevTradeDate() {
        SystemState systemState = getNewTestEntity();
        systemState.setPrevTradeDate(null);
        SystemState savedSystemState = systemStateRepository.save(systemState);
        
        assertThat(savedSystemState).isEqualTo(systemState);
    }

    @Test
    public void shouldSaveSystemStateWithNullSystemState() {
        SystemState systemState = getNewTestEntity();
        systemState.setSystemState(null);
        SystemState savedSystemState = systemStateRepository.save(systemState);
        
        assertThat(savedSystemState).isEqualTo(systemState);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveSystemStateWithNullMarketOpenTime() {
        SystemState systemState = getNewTestEntity();
        systemState.setMarketOpenTime(null);
        systemStateRepository.save(systemState);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveSystemStateWithNullMarketCloseTime() {
        SystemState systemState = getNewTestEntity();
        systemState.setMarketCloseTime(null);
        systemStateRepository.save(systemState);
    }
}