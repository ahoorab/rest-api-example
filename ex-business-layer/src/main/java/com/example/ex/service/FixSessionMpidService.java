package com.example.ex.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.model.entity.admin.FixSessionMpid;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.repository.admin.FixSessionMpidRepository;

/**
 * Business layer object for the Fix Session Mpid entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class FixSessionMpidService extends BaseService<FixSessionMpid> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixSessionMpidService.class);

    @Autowired
    private FixSessionMpidRepository fixSessionMpidRepository;
    @Autowired
    private FixSessionService fixSessionService;
    @Autowired
    private MpidService mpidService;

    @Override
    public FixSessionMpid save(FixSessionMpid fixSessionMpid) {
        super.validateUnique(fixSessionMpidRepository::findByHandle,fixSessionMpid,fixSessionMpid.getHandle());

        super.validateDependency(fixSessionService::findByHandle,FixSession.class,fixSessionMpid.getBrokerSession());
        super.validateDependency(mpidService::findByHandle,Mpid.class,fixSessionMpid.getMpid());

        return fixSessionMpidRepository.save(fixSessionMpid);
    }
    
    @Override
    public void deleteById(Integer id) {
        LOGGER.debug("checking if fix session mpid id <{}> exists",id);
        Optional<FixSessionMpid> fixSessionMpid = this.findById(id);
        if (!fixSessionMpid.isPresent()) {
            LOGGER.debug("fix session mpid id <{}> does not exists, cannot delete",id);
            throw new EntityNotFoundException("FixSessionMpid with id <" + id + "> was not found");
        }
        fixSessionMpidRepository.deleteById(id);
    }

    public Set<FixSessionMpid> findByBrokerSession(String handle) {
        LOGGER.debug("looking for all fix session mpids for fix session handle <{}>",handle);
        return fixSessionMpidRepository.findByBrokerSession(handle);
    }

    public Set<FixSessionMpid> findByMpid(String handle) {
        LOGGER.debug("looking for all fix session mpids for mpid handle <{}> ",handle);
        return fixSessionMpidRepository.findByMpid(handle);
    }

    public FixSessionMpid findByHandle(String handle) {
        LOGGER.debug("looking for fix session mpid by handle <{}> ",handle);
        return fixSessionMpidRepository.findByHandle(handle);
    }

    @Override
    protected JpaRepository<FixSessionMpid, Integer> getRepository() {
        return fixSessionMpidRepository;
    }

}