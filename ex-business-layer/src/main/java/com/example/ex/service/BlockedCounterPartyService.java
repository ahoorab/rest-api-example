package com.example.ex.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.BlockedCounterParty;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.repository.admin.BlockedCounterPartyRepository;

/**
 * Data access object for the Blocked Counter Party entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Service
public class BlockedCounterPartyService extends BaseService<BlockedCounterParty> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockedCounterPartyService.class);

    @Autowired
    private BlockedCounterPartyRepository blockedCounterPartyRepository;
    @Autowired
    private FirmService firmService;
    @Autowired
    private DealCodeService dealCodeService;

    @Override
    public BlockedCounterParty save(BlockedCounterParty blockedCounterParty) {
        super.validateUnique(blockedCounterPartyRepository::findByHandle,blockedCounterParty,blockedCounterParty.getHandle());
        super.validateUnique(blockedCounterPartyRepository::findByMnemonic,blockedCounterParty,blockedCounterParty.getMnemonic());

        super.validateDependency(firmService::findByHandle,Firm.class,blockedCounterParty.getPbFirm1());
        super.validateDependency(firmService::findByHandle,Firm.class,blockedCounterParty.getPbFirm2());
        super.validateDependency(firmService::findByHandle,Firm.class,blockedCounterParty.getTradingFirm1());
        super.validateDependency(firmService::findByHandle,Firm.class,blockedCounterParty.getTradingFirm2());

        super.validateDependency(dealCodeService::findByHandle,DealCode.class,blockedCounterParty.getDealCode1(),true);
        super.validateDependency(dealCodeService::findByHandle,DealCode.class,blockedCounterParty.getDealCode2(),true);

        return blockedCounterPartyRepository.save(blockedCounterParty);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<BlockedCounterParty> blockedCounterParty = blockedCounterPartyRepository.findById(id);
        if (!blockedCounterParty.isPresent()) {
            throw new EntityNotFoundException("BlockedCounterParty with id <" + id + "> was not found");
        }
        blockedCounterPartyRepository.deleteById(id);
    }

    public BlockedCounterParty findByHandle(String handle) {
        return blockedCounterPartyRepository.findByHandle(handle);
    }

    public List<BlockedCounterParty> findByFirm(String handle) {
        List<BlockedCounterParty> bcps = blockedCounterPartyRepository.findByPbFirm1(handle);
        bcps.addAll(blockedCounterPartyRepository.findByPbFirm2(handle));
        bcps.addAll(blockedCounterPartyRepository.findByTradingFirm1(handle));
        bcps.addAll(blockedCounterPartyRepository.findByTradingFirm2(handle));
        return bcps;
    }
    
    public List<BlockedCounterParty> findByDealCode(String handle) {
        List<BlockedCounterParty> bcps = blockedCounterPartyRepository.findByDealCode1(handle);
        bcps.addAll(blockedCounterPartyRepository.findByDealCode2(handle));
        return bcps;
    }

    @Override
    protected JpaRepository<BlockedCounterParty, Integer> getRepository() {
        return blockedCounterPartyRepository;
    }

}