package com.example.ex.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.CreditLine;
import com.example.ex.model.entity.admin.CreditPool;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.repository.admin.CreditLineRepository;

/**
 * Business layer object for the Credit Line entity
 * 
 * It covers basic operations inherited from JpaRepository / Crud Repository
 * and applies business validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class CreditLineService extends BaseService<CreditLine> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditLineService.class);

    @Autowired
    private CreditLineRepository creditLineRepository;
    @Autowired
    private CreditPoolService creditPoolService;
    @Autowired
    private FirmService firmService;

    @Override
    public CreditLine save(CreditLine creditLine) {
        super.validateUnique(creditLineRepository::findByHandle,creditLine,creditLine.getHandle());

        super.validateUnique(creditLineRepository::findByGrantorFirmAndGranteeFirm,creditLine,creditLine.getGrantorFirm(),creditLine.getGranteeFirm());

        super.validateDependency(firmService::findByHandle,Firm.class,creditLine.getGrantorFirm());
        super.validateDependency(firmService::findByHandle,Firm.class,creditLine.getGranteeFirm());
        super.validateDependency(creditPoolService::findByHandle,CreditPool.class,creditLine.getCreditPool());
        super.validateDependency(creditPoolService::findByHandleAndGrantorFirm,CreditPool.class,creditLine.getCreditPool(),creditLine.getGrantorFirm());
        
        LOGGER.debug("Saving creditline <{}>",creditLine);
        return creditLineRepository.save(creditLine);
    }

    @Override
    public void deleteById(Integer id) {
        LOGGER.debug("checking if creditline with id <{}> exists",id);
        Optional<CreditLine> creditLine = creditLineRepository.findById(id);
        if (!creditLine.isPresent()) {
            LOGGER.debug("creditline with id <{}> does not exists, throwing an exception",id);
            throw new EntityNotFoundException("Credit Line with id <" + id + "> was not found");
        }
        creditLineRepository.deleteById(id);
    }
    
    public List<CreditLine> findByCreditPool(String handle) {
        return creditLineRepository.findByCreditPool(handle);
    }

    public Set<CreditLine> findByFirm(String handle) {
        Set<CreditLine> creditLines = new HashSet<>();
        creditLines.addAll(creditLineRepository.findByGranteeFirm(handle));
        creditLines.addAll(creditLineRepository.findByGrantorFirm(handle));
        return creditLines;
    }
    
    public Set<String> getGrantorFirms() {
        return creditLineRepository.findDistinctByGrantorFirm();
    }

    public Set<String> getGranteeFirms() {
        return creditLineRepository.findDistinctByGranteeFirm();
    }

    @Override
    protected JpaRepository<CreditLine, Integer> getRepository() {
        return creditLineRepository;
    }
    
}