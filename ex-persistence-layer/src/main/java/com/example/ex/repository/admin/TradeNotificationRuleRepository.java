package com.example.ex.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.TradeNotificationRule;
import com.example.ex.model.type.NotificationType;
import com.example.ex.model.type.RoleType;

/**
 * Data access object for the Trade Notification Rue entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface TradeNotificationRuleRepository extends JpaRepository<TradeNotificationRule, Integer> {
    
    TradeNotificationRule findByHandle(String handle);

    TradeNotificationRule findByRoleAndNotificationTypeAndFixSessionAndFirmAndOnlyForFixSessionMpid(RoleType role, NotificationType type, String fixSessionHandle, String firmHandle, String fixSessionMpidHandle);

    List<TradeNotificationRule> findByFirm(String handle);

    List<TradeNotificationRule> findByFixSession(String handle);

    List<TradeNotificationRule> findByOnlyForFixSessionMpid(String handle);
}