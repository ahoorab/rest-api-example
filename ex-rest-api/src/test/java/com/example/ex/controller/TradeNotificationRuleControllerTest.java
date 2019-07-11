package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.model.entity.admin.TradeNotificationRule;
import com.example.ex.model.type.NotificationType;
import com.example.ex.model.type.RoleType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TradeNotificationRuleControllerTest extends AdminControllerTest<TradeNotificationRule> {

    @Override
    public void initController() {
        super.baseUri = TradeNotificationRuleController.BASE_URI;
        super.service = super.tradeNotificationRuleService;
        super.clazz = TradeNotificationRule.class;
    }

    @Override
    public List<TradeNotificationRule> getListOfEntities() {
        List<TradeNotificationRule> tradeNotificationRules = new ArrayList<>();
        tradeNotificationRules.add(new TradeNotificationRule("hera-dc", "Heraeus trades", NotificationType.DROPCOPY, "tdbank", RoleType.TRADING, "mpid", "hera-dc", Status.ENABLED));
        tradeNotificationRules.add(new TradeNotificationRule("scot-dc", "Scotia trades", NotificationType.NONE, "firm", RoleType.BROKER, "mpid", "scot-dc", Status.DISABLED));
        return tradeNotificationRules;
    }

    @Override
    public TradeNotificationRule getNewEntity() {
        return new TradeNotificationRule("test", "TEST", NotificationType.DROPCOPY, "test", RoleType.NONE, "test", "test-dc", Status.DISABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(tradeNotificationRule,containsString("Size.tradeNotificationRule.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullName() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setName(null);
        super.shouldReturnBadRequestWhenCreate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(tradeNotificationRule,containsString("Size.tradeNotificationRule.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullNotificationType() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setNotificationType(null);
        super.shouldReturnBadRequestWhenCreate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.notificationType"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCountry() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        byte[] json = super.jsonReplace(tradeNotificationRule, "notificationType", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,tradeNotificationRule,containsString("NotificationType` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullFirm() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setFirm(null);
        super.shouldReturnBadRequestWhenCreate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.firm"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidFirmSize() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(tradeNotificationRule,containsString("Size.tradeNotificationRule.firm"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullRole() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setRole(null);
        super.shouldReturnBadRequestWhenCreate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.role"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidRole() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        byte[] json = super.jsonReplace(tradeNotificationRule, "role", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,tradeNotificationRule,containsString("RoleType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullOnlyForFixSessionMpid() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setOnlyForFixSessionMpid(null);
        super.shouldReturnCreated(tradeNotificationRule);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidOnlyForFixSessionMpidSize() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setOnlyForFixSessionMpid(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(tradeNotificationRule,containsString("Size.tradeNotificationRule.onlyForFixSessionMpid"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        tradeNotificationRule.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.status"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewEntity();
        byte[] json = super.jsonReplace(tradeNotificationRule, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,tradeNotificationRule,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(tradeNotificationRule,containsString("Size.tradeNotificationRule.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullName() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setName(null);
        super.shouldReturnBadRequestWhenUpdate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(tradeNotificationRule,containsString("Size.tradeNotificationRule.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullNotificationType() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setNotificationType(null);
        super.shouldReturnBadRequestWhenUpdate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.notificationType"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCountry() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        byte[] json = super.jsonReplace(tradeNotificationRule, "notificationType", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,tradeNotificationRule,containsString("NotificationType` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullFirm() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setFirm(null);
        super.shouldReturnBadRequestWhenUpdate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.firm"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidFirmSize() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(tradeNotificationRule,containsString("Size.tradeNotificationRule.firm"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullRole() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setRole(null);
        super.shouldReturnBadRequestWhenUpdate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.role"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidRole() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        byte[] json = super.jsonReplace(tradeNotificationRule, "role", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,tradeNotificationRule,containsString("RoleType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullOnlyForFixSessionMpid() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setOnlyForFixSessionMpid(null);
        
        Mockito.when(service.findById(tradeNotificationRule.getId())).thenReturn(Optional.of(tradeNotificationRule));

        super.shouldReturnUpdated(tradeNotificationRule);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidOnlyForFixSessionMpidSize() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setOnlyForFixSessionMpid(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(tradeNotificationRule,containsString("Size.tradeNotificationRule.onlyForFixSessionMpid"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        tradeNotificationRule.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(tradeNotificationRule,containsString("NotNull.tradeNotificationRule.status"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        TradeNotificationRule tradeNotificationRule = getNewUpdateEntity();
        byte[] json = super.jsonReplace(tradeNotificationRule, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,tradeNotificationRule,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
 
}