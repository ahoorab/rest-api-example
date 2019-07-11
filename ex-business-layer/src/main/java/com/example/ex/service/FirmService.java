package com.example.ex.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.BlockedCounterParty;
import com.example.ex.model.entity.admin.CreditLine;
import com.example.ex.model.entity.admin.CreditPool;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.repository.admin.FirmRepository;

/**
 * Data access object for the Firm entity
 * 
 * It covers basic operations inherited from JpaRepository
 * and applies business validations
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Service
public class FirmService extends BaseService<Firm> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FirmService.class);
    
    @Autowired
    private FirmRepository firmRepository;
    @Autowired
    private DealCodeService dealCodeService;
    @Autowired
    private BlockedCounterPartyService blockedCounterParty;
    @Autowired
    private CreditPoolService creditPoolService;
    @Autowired
    private CreditLineService creditLineService;

    @Override
    public Firm save(Firm firm) {
        super.validateUnique(firmRepository::findByHandle,firm,firm.getHandle());
        super.validateUnique(firmRepository::findByMnemonic,firm,firm.getMnemonic());

        return firmRepository.save(firm);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Firm> firm = firmRepository.findById(id);
        if (!firm.isPresent()) {
            throw new EntityNotFoundException("Firm with id <" + id + "> was not found");
        }

        Set<DealCode> dealCodes = dealCodeService.findByFirm(firm.get().getHandle());
        if (!dealCodes.isEmpty()) {
            throw new EntityReferenceException("Firm with id <" + id + "> is being referenced by Deal Codes");
        }

        List<BlockedCounterParty> bcps = blockedCounterParty.findByFirm(firm.get().getHandle());
        if (!bcps.isEmpty()) {
            throw new EntityReferenceException("Firm with id <" + id + "> is being referenced by Blocked Counter Parties");
        }
        
        List<CreditPool> cps = creditPoolService.findByFirm(firm.get().getHandle());
        if (!cps.isEmpty()) {
            throw new EntityReferenceException("Firm with id <" + id + "> is being referenced by Credit Pools ");
        }

        Set<CreditLine> cls = creditLineService.findByFirm(firm.get().getHandle());
        if (!cls.isEmpty()) {
            throw new EntityReferenceException("Firm with id <" + id + "> is being referenced by Credit Lines ");
        }
        
        firmRepository.deleteById(id);
    }

    public Firm findByHandle(String handle) {
        return firmRepository.findByHandle(handle);
    }

    @Override
    protected JpaRepository<Firm, Integer> getRepository() {
        return firmRepository;
    }
}
