package com.example.ex.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.AppInstance;
import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.model.entity.admin.FixSessionMpid;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.repository.admin.FixSessionRepository;

/**
 * Business layer object for the Fix Session entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class FixSessionService extends BaseService<FixSession> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FixSessionService.class);

    @Autowired
    private FixSessionRepository fixSessionRepository;
    @Autowired
    private AppInstanceService appInstanceService;
    @Autowired
    private MpidService mpidService;
    @Autowired
    private FixSessionMpidService fixSessionMpidService;
    
    @Override
    public FixSession save(FixSession fixSession) {
        super.validateUnique(fixSessionRepository::findByHandle,fixSession,fixSession.getHandle());
        
        super.validateDependency(appInstanceService::findByAppId,AppInstance.class,fixSession.getAppInstance());
        super.validateDependency(mpidService::findByHandle,Mpid.class,fixSession.getMpid());

        return fixSessionRepository.save(fixSession);
    }
    
    @Override
    public void deleteById(Integer id) {
        LOGGER.debug("checking if fix session id <{}> exists",id);
        Optional<FixSession> fixSession = this.findById(id);
        if (!fixSession.isPresent()) {
            LOGGER.debug("fix session id <{}> does not exists, cannot delete",id);
            throw new EntityNotFoundException("fixSession with id <" + id + "> was not found");
        }

        String fixSessionHandle = fixSession.get().getHandle();
        LOGGER.debug("checking if fix session handle <{}> is referenced by fix session mpids",fixSessionHandle);
        Set<FixSessionMpid> fixSessionMpids = fixSessionMpidService.findByBrokerSession(fixSessionHandle);
        if (!fixSessionMpids.isEmpty()) {
            LOGGER.debug("fix session <{}> is being referenced by fix session mpids, cannot delete fix session",id);
            throw new EntityReferenceException("fix session <" + id + "> is being referenced by fix session mpids");
        }

        fixSessionRepository.deleteById(id);
    }

    public Set<FixSession> findByAppInstance(String appId) {
        LOGGER.debug("looking for all fix session for app instance appid <{}> ",appId);
        return fixSessionRepository.findByAppInstance(appId);
    }

    public Set<FixSession> findByMpid(String handle) {
        LOGGER.debug("looking for all fix session for mpid handle <{}> ",handle);
        return fixSessionRepository.findByMpid(handle);
    }

    public FixSession findByHandle(String handle) {
        LOGGER.debug("looking for all fix session by handle <{}> ",handle);
        return fixSessionRepository.findByHandle(handle);
    }

    @Override
    protected JpaRepository<FixSession, Integer> getRepository() {
        return fixSessionRepository;
    }

}