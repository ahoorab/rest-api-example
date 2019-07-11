package com.example.ex.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.FixSessionMpid;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.repository.admin.MpidRepository;

/**
 * Business layer object for the Mpid entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class MpidService extends BaseService<Mpid> {
    
    public static final String CACHE = "mpids";

    private static final Logger LOGGER = LoggerFactory.getLogger(MpidService.class);

    @Autowired
    private MpidRepository mpidRepository;
    @Autowired
    private DealCodeService dealCodeService;
    @Autowired
    private FixSessionMpidService fixSessionMpidService;

    @Override
    public Mpid save(Mpid mpid) {
        super.validateUnique(mpidRepository::findByHandle,mpid,mpid.getHandle());

        super.validateDependency(mpidRepository::findByHandle,Mpid.class,mpid.getParentMpid(),true);
        super.validateDependency(dealCodeService::findByHandle,DealCode.class,mpid.getDealCode());

        return mpidRepository.save(mpid);
    }
    
    @Override
    public void deleteById(Integer id) {
        Optional<Mpid> mpid = this.findById(id);
        if (!mpid.isPresent()) {
            throw new EntityNotFoundException("Parent mpid with id <" + id + "> was not found");
        }
        
        String mpidHandle = mpid.get().getHandle();
        LOGGER.debug("checking if mpid handle <{}> is referenced by fix session mpids",mpidHandle);
        Set<FixSessionMpid> fixSessionMpids = fixSessionMpidService.findByMpid(mpidHandle);
        if (!fixSessionMpids.isEmpty()) {
            LOGGER.debug("Mpid <{}> is being referenced by fix session mpids, cannot delete mpid",id);
            throw new EntityReferenceException("mpid <" + id + "> is being referenced by fix session mpids");
        }
        
        mpidRepository.deleteById(id);
    }

    public List<Mpid> findByDealCode(String handle) {
        return mpidRepository.findByDealCode(handle);
    }

    @Cacheable(CACHE)
    public List<Mpid> getFromCacheByDealCode(String handle) {
        return mpidRepository.findByDealCode(handle);
    }

    public Mpid findByHandle(String handle) {
        return mpidRepository.findByHandle(handle);
    }

    @Override
    protected JpaRepository<Mpid, Integer> getRepository() {
        return mpidRepository;
    }

}