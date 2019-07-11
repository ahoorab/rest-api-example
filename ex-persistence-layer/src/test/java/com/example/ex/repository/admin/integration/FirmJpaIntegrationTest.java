package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.Firm;
import com.example.ex.repository.admin.BaseJpaTest;
import com.example.ex.repository.admin.FirmRepositoryTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class FirmJpaIntegrationTest extends FirmRepositoryTest {

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithFirmTypeGreaterThan127() {
        Firm firm = getNewTestEntity();
        firm.setFirmType(Byte.MAX_VALUE+1);
        getImplementationRepository().save(firm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithHubGreaterThan127() {
        Firm firm = getNewTestEntity();
        firm.setIsHub(Byte.MAX_VALUE+1);
        getImplementationRepository().save(firm);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithPrincipalGreaterThan127() {
        Firm firm = getNewTestEntity();
        firm.setIsPrincipal(Byte.MAX_VALUE+1);
        getImplementationRepository().save(firm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithPbGreaterThan127() {
        Firm firm = getNewTestEntity();
        firm.setIsPb(Byte.MAX_VALUE+1);
        getImplementationRepository().save(firm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithSubpbGreaterThan127() {
        Firm firm = getNewTestEntity();
        firm.setIsSubpb(Byte.MAX_VALUE+1);
        getImplementationRepository().save(firm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithSubpbClientGreaterThan127() {
        Firm firm = getNewTestEntity();
        firm.setIsSubpbClient(Byte.MAX_VALUE+1);
        getImplementationRepository().save(firm);
    }
    
}