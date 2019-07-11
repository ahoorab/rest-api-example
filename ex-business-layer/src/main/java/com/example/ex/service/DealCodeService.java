package com.example.ex.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.ex.exception.BusinessValidationException;
import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.BlockedCounterParty;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.model.type.DcType;
import com.example.ex.repository.admin.DealCodeRepository;

/**
 * Business layer object for the DealCode entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class DealCodeService extends BaseService<DealCode> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DealCodeService.class);

    public static final String CACHE = "dealcodes";
    
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private DealCodeRepository dealCodeRepository;
    @Autowired
    private FirmService firmService;
    @Autowired
    private MpidService mpidService;
    @Autowired
    private BlockedCounterPartyService blockedCounterParty;

    @Override
    public DealCode save(DealCode dealCode) {
        super.validateUnique(dealCodeRepository::findByHandle,dealCode,dealCode.getHandle());
        super.validateUnique(dealCodeRepository::findByMnemonic,dealCode,dealCode.getMnemonic());
        
        super.validateDependency(firmService::findByHandle,Firm.class,dealCode.getFirm());
        super.validateDependency(firmService::findByHandle,Firm.class,dealCode.getPbFirm(),true);
        super.validateDependency(firmService::findByHandle,Firm.class,dealCode.getSubPbFirm(),true);

        if (DcType.PBCLIENT.equals(dealCode.getDcType()) && dealCode.getPbFirm() == null) {
            throw new BusinessValidationException("PB Firm can not be null if PB Client is selected");
        }

        return dealCodeRepository.save(dealCode);
    }
    
    @Override
    public void deleteById(Integer id) {
        Optional<DealCode> dc = this.findById(id);
        if (!dc.isPresent()) {
            throw new EntityNotFoundException("Deal Code with id <" + id + "> was not found");
        }

        List<Mpid> mpids = mpidService.findByDealCode(dc.get().getHandle());
        if (!mpids.isEmpty()) {
            throw new EntityReferenceException("Deal code with id <" + id + "> is being referenced by Mpids");
        }
        
        List<BlockedCounterParty> bcps = blockedCounterParty.findByDealCode(dc.get().getHandle());
        if (!bcps.isEmpty()) {
            throw new EntityReferenceException("Deal code with id <" + id + "> is being referenced by Blocked Counter Parties");
        }
        
        dealCodeRepository.deleteById(id);
    }

    public DealCode findByHandle(String handle) {
        return dealCodeRepository.findByHandle(handle);
    }
    
    @Cacheable(CACHE)
    public DealCode getFromCache(String handle) {
        return dealCodeRepository.findByHandle(handle);
    }

    @Scheduled(fixedRate = 60000)
    public void cacheEvict() {
        List<DealCode> all = findAll();
        Set<String> handles = new HashSet<>();
        Cache cache = cacheManager.getCache(CACHE);
        for (DealCode dc : all) {
            ValueWrapper wrapper = cache.get(dc.getHandle());
            if (wrapper != null) {
                DealCode cachedDC = (DealCode) wrapper.get();
                if (!dc.equals(cachedDC)) {
                    LOGGER.debug("updating dealcode cache with object {}", dc);
                    cacheManager.getCache(CACHE).put(dc.getHandle(), dc);
                }
            }
            handles.add(dc.getHandle());
        }

        @SuppressWarnings("unchecked")
        Map<String,DealCode> implementation = (Map<String, DealCode>) cache.getNativeCache();
        for (String handle : implementation.keySet()) {
            if (!handles.contains(handle)) {
                LOGGER.debug("removing dealcode <{}> from cache", handle);
                cache.evict(handle);
            }
        }

    }

    public Set<DealCode> findByFirm(String handle) {
        Set<DealCode> dealCodes = new HashSet<>();
        dealCodes.addAll(dealCodeRepository.findByFirm(handle));
        dealCodes.addAll(dealCodeRepository.findByPbFirm(handle));
        dealCodes.addAll(dealCodeRepository.findBySubPbFirm(handle));
        return dealCodes;
    }

    @Override
    protected JpaRepository<DealCode, Integer> getRepository() {
        return dealCodeRepository;
    }
}