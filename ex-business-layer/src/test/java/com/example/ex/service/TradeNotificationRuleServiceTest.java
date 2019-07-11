package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.exception.EntityAlreadyExistsException;
import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.model.entity.admin.FixSessionMpid;
import com.example.ex.model.entity.admin.TradeNotificationRule;
import com.example.ex.model.type.NotificationType;
import com.example.ex.model.type.RoleType;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.TradeNotificationRuleRepository;

@RunWith(SpringRunner.class)
public class TradeNotificationRuleServiceTest {

    @TestConfiguration
    static class TradeNotificationRuleServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public TradeNotificationRuleService tradeNotificationRuleService() {
            return new TradeNotificationRuleService();
        }
    }

    @MockBean
    private TradeNotificationRuleRepository tradeNotificationRuleRepository;
    @Autowired
    private TradeNotificationRuleService tradeNotificationRuleService;
    @Autowired
    private FirmService firmService;
    @Autowired
    private FixSessionService fixSessionService;
    @Autowired
    private FixSessionMpidService fixSessionMpidService;
    
    private TradeNotificationRule getTestTradeNotificationRule() {
        return new TradeNotificationRule("hera-dc", "Heraeus trades", NotificationType.DROPCOPY, "tdbank", RoleType.TRADING, "mpid", "hera-dc", Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveTradeNotificationRuleWithDuplicatedHandle() {
        TradeNotificationRule existingTradeNotificationRule = getTestTradeNotificationRule();
        existingTradeNotificationRule.setId(1);
        
        Mockito.when(tradeNotificationRuleRepository.findByHandle(existingTradeNotificationRule.getHandle())).thenReturn(existingTradeNotificationRule);

        TradeNotificationRule tradeNotificationRule = getTestTradeNotificationRule();
        tradeNotificationRuleService.save(tradeNotificationRule);
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveTradeNotificationRuleWithDuplicatedRole_NotificationType_FixSession_Firm_OnlyForFixSessionMpid() {
        TradeNotificationRule existingTradeNotificationRule = getTestTradeNotificationRule();
        existingTradeNotificationRule.setId(1);
        
        Mockito.when(tradeNotificationRuleRepository.findByRoleAndNotificationTypeAndFixSessionAndFirmAndOnlyForFixSessionMpid(existingTradeNotificationRule.getRole(),
                existingTradeNotificationRule.getNotificationType(), existingTradeNotificationRule.getFixSession(),
                existingTradeNotificationRule.getFirm(), existingTradeNotificationRule.getOnlyForFixSessionMpid())).thenReturn(existingTradeNotificationRule);
        Mockito.when(firmService.findByHandle(existingTradeNotificationRule.getFirm())).thenReturn(new Firm());
        Mockito.when(fixSessionService.findByHandle(existingTradeNotificationRule.getFixSession())).thenReturn(new FixSession());
        Mockito.when(fixSessionMpidService.findByHandle(existingTradeNotificationRule.getOnlyForFixSessionMpid())).thenReturn(new FixSessionMpid());

        TradeNotificationRule tradeNotificationRule = getTestTradeNotificationRule();
        tradeNotificationRuleService.save(tradeNotificationRule);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveTradeNotificationRuleWithFirmNotFound() {
        TradeNotificationRule tradeNotificationRule = getTestTradeNotificationRule();
        tradeNotificationRule.setId(1);
        
        Mockito.when(tradeNotificationRuleRepository.findByHandle(tradeNotificationRule.getHandle())).thenReturn(tradeNotificationRule);
        Mockito.when(firmService.findByHandle(tradeNotificationRule.getFirm())).thenReturn(null);
        Mockito.when(fixSessionService.findByHandle(tradeNotificationRule.getFixSession())).thenReturn(new FixSession());
        Mockito.when(fixSessionMpidService.findByHandle(tradeNotificationRule.getOnlyForFixSessionMpid())).thenReturn(new FixSessionMpid());

        tradeNotificationRuleService.save(tradeNotificationRule);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveTradeNotificationRuleWithFixSessionNotFound() {
        TradeNotificationRule tradeNotificationRule = getTestTradeNotificationRule();
        tradeNotificationRule.setId(1);
        
        Mockito.when(tradeNotificationRuleRepository.findByHandle(tradeNotificationRule.getHandle())).thenReturn(tradeNotificationRule);
        Mockito.when(firmService.findByHandle(tradeNotificationRule.getFirm())).thenReturn(new Firm());
        Mockito.when(fixSessionService.findByHandle(tradeNotificationRule.getFixSession())).thenReturn(null);
        Mockito.when(fixSessionMpidService.findByHandle(tradeNotificationRule.getOnlyForFixSessionMpid())).thenReturn(new FixSessionMpid());

        tradeNotificationRuleService.save(tradeNotificationRule);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveTradeNotificationRuleWithFixSessionMpidNotFound() {
        TradeNotificationRule tradeNotificationRule = getTestTradeNotificationRule();
        tradeNotificationRule.setId(1);
        
        Mockito.when(tradeNotificationRuleRepository.findByHandle(tradeNotificationRule.getHandle())).thenReturn(tradeNotificationRule);
        Mockito.when(firmService.findByHandle(tradeNotificationRule.getFirm())).thenReturn(new Firm());
        Mockito.when(fixSessionService.findByHandle(tradeNotificationRule.getFixSession())).thenReturn(new FixSession());
        Mockito.when(fixSessionMpidService.findByHandle(tradeNotificationRule.getOnlyForFixSessionMpid())).thenReturn(null);

        tradeNotificationRuleService.save(tradeNotificationRule);
    }
    
    @Test
    public void shouldSaveTradeNotificationRule() {
        TradeNotificationRule tradeNotificationRule = getTestTradeNotificationRule();

        Mockito.when(tradeNotificationRuleRepository.save(tradeNotificationRule)).thenReturn(tradeNotificationRule);
        Mockito.when(firmService.findByHandle(tradeNotificationRule.getFirm())).thenReturn(new Firm());
        Mockito.when(fixSessionService.findByHandle(tradeNotificationRule.getFixSession())).thenReturn(new FixSession());
        Mockito.when(fixSessionMpidService.findByHandle(tradeNotificationRule.getOnlyForFixSessionMpid())).thenReturn(new FixSessionMpid());

        TradeNotificationRule savedTradeNotificationRule = tradeNotificationRuleService.save(tradeNotificationRule);
        
        assertThat(savedTradeNotificationRule).isEqualTo(tradeNotificationRule);
    }
    
    @Test
    public void shouldSaveTradeNotificationRuleWithoutOnlyForFixSessionMpid() {
        TradeNotificationRule tradeNotificationRule = getTestTradeNotificationRule();
        tradeNotificationRule.setOnlyForFixSessionMpid(null);

        Mockito.when(tradeNotificationRuleRepository.save(tradeNotificationRule)).thenReturn(tradeNotificationRule);
        Mockito.when(firmService.findByHandle(tradeNotificationRule.getFirm())).thenReturn(new Firm());
        Mockito.when(fixSessionService.findByHandle(tradeNotificationRule.getFixSession())).thenReturn(new FixSession());

        TradeNotificationRule savedTradeNotificationRule = tradeNotificationRuleService.save(tradeNotificationRule);
        
        assertThat(savedTradeNotificationRule).isEqualTo(tradeNotificationRule);
    }
    
    @Test
    public void shouldDeleteTradeNotificationRule() {
        TradeNotificationRule tradeNotificationRule = getTestTradeNotificationRule();
        tradeNotificationRule.setId(1);

        Mockito.when(tradeNotificationRuleRepository.findById(tradeNotificationRule.getId())).thenReturn(Optional.of(tradeNotificationRule));

        tradeNotificationRuleService.deleteById(tradeNotificationRule.getId());
        
        Mockito.verify(tradeNotificationRuleRepository, Mockito.times(1)).deleteById(tradeNotificationRule.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingTradeNotificationRule() {
        Mockito.when(tradeNotificationRuleRepository.findById(1)).thenReturn(Optional.empty());
        tradeNotificationRuleService.deleteById(1);
    }
    
}