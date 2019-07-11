package com.example.ex.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.example.ex.redis.queue.RedisMessagePublisher;
import com.example.ex.redis.queue.RedisMessageSubscriber;
import com.example.ex.service.AppInstanceService;
import com.example.ex.service.AppJvmService;
import com.example.ex.service.AppServerService;
import com.example.ex.service.AppTypeService;
import com.example.ex.service.BlockedCounterPartyService;
import com.example.ex.service.CacheService;
import com.example.ex.service.CreditLineService;
import com.example.ex.service.CreditPoolService;
import com.example.ex.service.CurrencyPairService;
import com.example.ex.service.CurrencyPairSettleDateService;
import com.example.ex.service.CurrencyService;
import com.example.ex.service.DealCodeService;
import com.example.ex.service.FirmService;
import com.example.ex.service.FixSessionMpidService;
import com.example.ex.service.FixSessionService;
import com.example.ex.service.HolidayService;
import com.example.ex.service.MpidService;
import com.example.ex.service.ScheduledEventService;
import com.example.ex.service.SystemStateService;
import com.example.ex.service.TimeSeriesService;
import com.example.ex.service.TradeNotificationRuleService;
import com.example.ex.service.VenueService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public abstract class ControllerTestConfiguration extends RestTestsConfiguration {

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected FirmService firmService;
    @MockBean
    protected CurrencyService currencyService;
    @MockBean
    protected AppServerService appServerService;
    @MockBean
    protected AppTypeService appTypeService;
    @MockBean
    protected AppJvmService appJvmService;
    @MockBean
    protected AppInstanceService appInstanceService;
    @MockBean
    protected HolidayService holidayService;
    @MockBean
    protected DealCodeService dealCodeService;
    @MockBean
    protected CurrencyPairService currencyPairService;
    @MockBean
    protected CurrencyPairSettleDateService currencyPairSettleDateService;
    @MockBean
    protected SystemStateService systemStateService;
    @MockBean
    protected MpidService mpidService;
    @MockBean
    protected FixSessionService fixSessionService;
    @MockBean
    protected FixSessionMpidService fixSessionMpidService;
    @MockBean
    protected ScheduledEventService scheduledEventService;
    @MockBean
    protected VenueService venueService;
    @MockBean
    protected TradeNotificationRuleService tradeNotificationRuleService;
    @MockBean
    protected BlockedCounterPartyService blockedCounterPartyService;
    @MockBean
    protected CreditPoolService creditPoolService;
    @MockBean
    protected CacheService cacheService;
    @MockBean
    protected CreditLineService creditLineService;
    @MockBean
    protected TimeSeriesService timeSeriesService;
    @MockBean
    private RedisTemplate<String, String> redisTemplate;
    @MockBean
    private RedisMessagePublisher redisMessagePublisher;
    @MockBean
    private RedisMessageSubscriber redisMessageSubscriber;
    @MockBean
    private ConfigurableJWTProcessor<SecurityContext> configurableJWTProcessor;

    protected static final String JWTS = Jwts.builder().setSubject("testUsername")
            .signWith(SignatureAlgorithm.HS512, "ThisIsASecret").compact();

}
