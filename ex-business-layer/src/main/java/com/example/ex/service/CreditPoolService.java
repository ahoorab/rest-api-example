package com.example.ex.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.CreditLine;
import com.example.ex.model.entity.admin.CreditPool;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.repository.admin.CreditPoolRepository;

/**
 * Business layer object for the Credit Pool entity
 * 
 * It covers basic operations inherited from JpaRepository / Crud Repository
 * and applies business validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class CreditPoolService extends BaseService<CreditPool> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditPoolService.class);

    @Autowired
    private CreditPoolRepository creditPoolRepository;
    @Autowired
    private CreditLineService creditLineService;
    @Autowired
    private FirmService firmService;

    @Override
    public CreditPool save(CreditPool creditPool) {
        super.validateUnique(creditPoolRepository::findByHandle,creditPool,creditPool.getHandle());
        super.validateUnique(creditPoolRepository::findByMnemonic,creditPool,creditPool.getMnemonic());

        super.validateDependency(firmService::findByHandle,Firm.class,creditPool.getGrantorFirm());

        LOGGER.debug("Saving creditpool <{}>",creditPool);
        return creditPoolRepository.save(creditPool);
    }

    @Override
    public void deleteById(Integer id) {
        LOGGER.debug("checking if creditpool with id <{}> exists",id);
        Optional<CreditPool> creditPool = creditPoolRepository.findById(id);
        if (!creditPool.isPresent()) {
            LOGGER.debug("creditpool with id <{}> does not exists, throwing an exception",id);
            throw new EntityNotFoundException("Currency Pair with id <" + id + "> was not found");
        }
        
        List<CreditLine> creditLines = creditLineService.findByCreditPool(creditPool.get().getHandle());
        if (!creditLines.isEmpty()) {
            throw new EntityReferenceException("Credit Pool with id <" + id + "> is being referenced by credit lines");
        }

        creditPoolRepository.deleteById(id);
    }
    
    public CreditPool findByHandle(String handle) {
        return creditPoolRepository.findByHandle(handle);
    }
    
    public List<CreditPool> findByFirm(String handle) {
        return creditPoolRepository.findByGrantorFirm(handle);
    }

    public CreditPool findByHandleAndGrantorFirm(String handle, String grantorFirmHandle) {
        return creditPoolRepository.findByHandleAndGrantorFirm(handle, grantorFirmHandle);
    }

    @Override
    protected JpaRepository<CreditPool, Integer> getRepository() {
        return creditPoolRepository;
    }

}