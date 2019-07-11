package com.example.ex.repository.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class FirmRepositoryTest extends BaseJpaTest<Firm, Integer> {

    @Autowired
    private FirmRepository firmRepository;

    @Override
    protected JpaRepository<Firm, Integer> getImplementationRepository() {
        return firmRepository;
    }

    @Override
    protected Firm getNewTestEntity() {
        return new Firm("exx", "EXX", "Example Markets", 127, 0, 0, 0, 0, 0, 0, Status.ENABLED);
    }

    @Override
    protected void updateEntity(Firm entity) {
        entity.setHandle("exx1");
        entity.setMnemonic("EXX1");
        entity.setName("Example Markets1");
        entity.setFirmType(1);
        entity.setIsHub(1);
        entity.setIsPrincipal(1);
        entity.setIsPb(1);
        entity.setIsSubpb(1);
        entity.setIsPbClient(1);
        entity.setIsSubpbClient(1);
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveFirmWithDuplicatedHandle() {
        Firm firm = getNewTestEntity();
        firm.setHandle("duplicated");
        try {
            firmRepository.save(firm);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        firm = new Firm();
        updateEntity(firm);
        firm.setHandle("duplicated");
        firmRepository.save(firm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithNullHandle() {
        Firm firm = getNewTestEntity();
        firm.setHandle(null);

        firmRepository.save(firm);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithHandleSizeGreaterThan12() {
        Firm firm = getNewTestEntity();
        firm.setHandle(RandomString.make(13));
        firmRepository.save(firm);
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveFirmWithDuplicatedMnemonic() {
        Firm firm = getNewTestEntity();
        firm.setMnemonic("duplicated");
        try {
            firmRepository.save(firm);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        firm = new Firm();
        updateEntity(firm);
        firm.setMnemonic("duplicated");
        firmRepository.save(firm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithNullMnemonic() {
        Firm firm = getNewTestEntity();
        firm.setMnemonic(null);

        firmRepository.save(firm);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithMnemonicSizeGreaterThan16() {
        Firm firm = getNewTestEntity();
        firm.setMnemonic(RandomString.make(17));
        firmRepository.save(firm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithNullName() {
        Firm firm = getNewTestEntity();
        firm.setName(null);

        firmRepository.save(firm);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithNameSizeGreaterThan64() {
        Firm firm = getNewTestEntity();
        firm.setName(RandomString.make(65));
        firmRepository.save(firm);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFirmWithNullFirmType() {
        Firm firm = getNewTestEntity();
        firm.setFirmType(null);

        firmRepository.save(firm);
    }
    
    @Test
    public void shouldSaveFirmWithNullHub() {
        Firm firm = getNewTestEntity();
        firm.setIsHub(null);

        Firm savedFirm = firmRepository.save(firm);
        
        assertThat(savedFirm).isEqualTo(firm);
    }
    
    @Test
    public void shouldSaveFirmWithNullPrincipal() {
        Firm firm = getNewTestEntity();
        firm.setIsPrincipal(null);

        Firm savedFirm = firmRepository.save(firm);
        
        assertThat(savedFirm).isEqualTo(firm);
    }
    
    @Test
    public void shouldSaveFirmWithNullPb() {
        Firm firm = getNewTestEntity();
        firm.setIsPb(null);

        Firm savedFirm = firmRepository.save(firm);
        
        assertThat(savedFirm).isEqualTo(firm);
    }
    
    @Test
    public void shouldSaveFirmWithNullSubpb() {
        Firm firm = getNewTestEntity();
        firm.setIsSubpb(null);

        Firm savedFirm = firmRepository.save(firm);
        
        assertThat(savedFirm).isEqualTo(firm);
    }
    
    @Test
    public void shouldSaveFirmWithNullSubpbClient() {
        Firm firm = getNewTestEntity();
        firm.setIsSubpbClient(null);

        Firm savedFirm = firmRepository.save(firm);
        
        assertThat(savedFirm).isEqualTo(firm);
    }
    
    @Test
    public void shouldSaveFirmWithNullStatus() {
        Firm firm = getNewTestEntity();
        firm.setStatus(null);

        Firm savedFirm = firmRepository.save(firm);
        
        assertThat(savedFirm).isEqualTo(firm);
    }

}