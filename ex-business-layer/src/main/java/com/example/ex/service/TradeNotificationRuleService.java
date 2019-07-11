package com.example.ex.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityAlreadyExistsException;
import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.model.entity.admin.FixSessionMpid;
import com.example.ex.model.entity.admin.TradeNotificationRule;
import com.example.ex.model.type.NotificationType;
import com.example.ex.model.type.RoleType;
import com.example.ex.repository.admin.TradeNotificationRuleRepository;

/**
 * Data access object for the Trade Notificaion Rule entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Service
public class TradeNotificationRuleService extends BaseService<TradeNotificationRule> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeNotificationRuleService.class);

    @Autowired
    private TradeNotificationRuleRepository tradeNotificationRepository;
    @Autowired
    private FirmService firmService;
    @Autowired
    private FixSessionService fixSessionService;
    @Autowired
    private FixSessionMpidService fixSessionMpidService;

    @Override
    public TradeNotificationRule save(TradeNotificationRule tradeNotification) {
        super.validateUnique(tradeNotificationRepository::findByHandle,tradeNotification,tradeNotification.getHandle());
        
        String firmHandle = tradeNotification.getFirm();
        String fixSessionHandle = tradeNotification.getFixSession();
        String fixSessionMpidHandle = tradeNotification.getOnlyForFixSessionMpid();
        
        super.validateDependency(firmService::findByHandle,Firm.class,firmHandle);
        super.validateDependency(fixSessionService::findByHandle,FixSession.class,fixSessionHandle);
        super.validateDependency(fixSessionMpidService::findByHandle,FixSessionMpid.class,fixSessionMpidHandle,true);

        RoleType role = tradeNotification.getRole();
        NotificationType type = tradeNotification.getNotificationType();

        LOGGER.debug("checking if unique constraint Role <{}>, notification type <{}>, fix session <{}>, firm <{}>, fix session mpi handle <{}> exists",
                role, type, fixSessionHandle, firmHandle, fixSessionMpidHandle);

        TradeNotificationRule tnr = tradeNotificationRepository.findByRoleAndNotificationTypeAndFixSessionAndFirmAndOnlyForFixSessionMpid(role,type,fixSessionHandle,firmHandle,fixSessionMpidHandle);

        if (tnr != null && tradeNotification.getId() != tnr.getId()) {

            LOGGER.debug("trade notification rule with unique constraint Role <{}>, notification type <{}>, fix session <{}>, firm <{}>, fix session mpi handle <{}> "
                    + "already exists, cannot save trade notification", role, type, fixSessionHandle, firmHandle, fixSessionMpidHandle);

            throw new EntityAlreadyExistsException("TradeNotificationRule with Role <" + role + ">, notification type <" + type + ">, fix session <"
                            + fixSessionHandle + ">, firm <" + firmHandle + ">, fix session mpi handle <"
                            + fixSessionMpidHandle + "> already exists, cannot save trade notification");
        }

        return tradeNotificationRepository.save(tradeNotification);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<TradeNotificationRule> tradeNotification = tradeNotificationRepository.findById(id);
        if (!tradeNotification.isPresent()) {
            throw new EntityNotFoundException("TradeNotificationRule with id <" + id + "> was not found");
        }
        tradeNotificationRepository.deleteById(id);
    }

    public TradeNotificationRule findByHandle(String handle) {
        return tradeNotificationRepository.findByHandle(handle);
    }

    @Override
    protected JpaRepository<TradeNotificationRule, Integer> getRepository() {
        return tradeNotificationRepository;
    }

}