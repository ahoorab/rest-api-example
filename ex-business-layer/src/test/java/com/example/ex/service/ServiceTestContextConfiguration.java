package com.example.ex.service;

import org.mockito.Mockito;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

import com.example.ex.repository.admin.AppInstanceRepository;
import com.example.ex.repository.admin.AppJvmRepository;
import com.example.ex.repository.admin.AppServerRepository;
import com.example.ex.repository.admin.AppTypeRepository;
import com.example.ex.repository.admin.BlockedCounterPartyRepository;
import com.example.ex.repository.admin.CreditLineRepository;
import com.example.ex.repository.admin.CreditPoolRepository;
import com.example.ex.repository.admin.CurrencyPairRepository;
import com.example.ex.repository.admin.CurrencyPairSettleDateRepository;
import com.example.ex.repository.admin.CurrencyRepository;
import com.example.ex.repository.admin.DealCodeRepository;
import com.example.ex.repository.admin.FirmRepository;
import com.example.ex.repository.admin.FixSessionMpidRepository;
import com.example.ex.repository.admin.FixSessionRepository;
import com.example.ex.repository.admin.HolidayRepository;
import com.example.ex.repository.admin.MpidRepository;
import com.example.ex.repository.admin.RefdBrokerSessionMpidRepository;
import com.example.ex.repository.admin.ScheduledEventRepository;
import com.example.ex.repository.admin.SystemStateRepository;
import com.example.ex.repository.admin.TradeNotificationRuleRepository;
import com.example.ex.repository.admin.VenueRepository;

public class ServiceTestContextConfiguration {
    @Bean
    public HolidayService holidayService() {
        return Mockito.mock(HolidayService.class);
    }

    @Bean
    public CurrencyPairSettleDateService currencyPairSettleDateService() {
        return Mockito.mock(CurrencyPairSettleDateService.class);
    }

    @Bean
    public SystemStateService systemStateService() {
        return Mockito.mock(SystemStateService.class);
    }

    @Bean
    public CurrencyPairService currencyPairService() {
        return Mockito.mock(CurrencyPairService.class);
    }

    @Bean
    public CreditLineService creditLineService() {
        return Mockito.mock(CreditLineService.class);
    }

    @Bean
    public CurrencyService currencyService() {
        return Mockito.mock(CurrencyService.class);
    }

    @Bean
    public BlockedCounterPartyService blockedCounterPartyService() {
        return Mockito.mock(BlockedCounterPartyService.class);
    }

    @Bean
    public DealCodeService dealCodeService() {
        return Mockito.mock(DealCodeService.class);
    }

    @Bean
    public MpidService mpidService() {
        return Mockito.mock(MpidService.class);
    }

    @Bean
    public FixSessionMpidService fixSessionMpidService() {
        return Mockito.mock(FixSessionMpidService.class);
    }

    @Bean
    public FixSessionService fixSessionService() {
        return Mockito.mock(FixSessionService.class);
    }

    @Bean
    public AppInstanceService appInstanceService() {
        return Mockito.mock(AppInstanceService.class);
    }

    @Bean
    public AppJvmService appJvmService() {
        return Mockito.mock(AppJvmService.class);
    }

    @Bean
    public AppServerService appServerService() {
        return Mockito.mock(AppServerService.class);
    }

    @Bean
    public AppTypeService appTypeService() {
        return Mockito.mock(AppTypeService.class);
    }

    @Bean
    public FirmService firmService() {
        return Mockito.mock(FirmService.class);
    }
    
    @Bean
    public CreditPoolService creditPoolService() {
        return Mockito.mock(CreditPoolService.class);
    }

    @Bean
    public TradeNotificationRuleRepository tradeNotificationRuleRepository() {
        return Mockito.mock(TradeNotificationRuleRepository.class);
    }
    
    @Bean
    public BlockedCounterPartyRepository blockedCounterPartyRepository() {
        return Mockito.mock(BlockedCounterPartyRepository.class);
    }

    @Bean
    public DealCodeRepository dealCodeRepository() {
        return Mockito.mock(DealCodeRepository.class);
    }

    @Bean
    public MpidRepository mpidRepository() {
        return Mockito.mock(MpidRepository.class);
    }

    @Bean
    public FixSessionMpidRepository fixSessionMpidRepository() {
        return Mockito.mock(FixSessionMpidRepository.class);
    }

    @Bean
    public FixSessionRepository fixSessionRepository() {
        return Mockito.mock(FixSessionRepository.class);
    }

    @Bean
    public AppInstanceRepository appInstanceRepository() {
        return Mockito.mock(AppInstanceRepository.class);
    }

    @Bean
    public AppJvmRepository appJvmRepository() {
        return Mockito.mock(AppJvmRepository.class);
    }

    @Bean
    public AppServerRepository appServerRepository() {
        return Mockito.mock(AppServerRepository.class);
    }

    @Bean
    public AppTypeRepository appTypeRepository() {
        return Mockito.mock(AppTypeRepository.class);
    }

    @Bean
    public FirmRepository firmRepository() {
        return Mockito.mock(FirmRepository.class);
    }

    @Bean
    public CreditPoolRepository creditPooLRepository() {
        return Mockito.mock(CreditPoolRepository.class);
    }
    
    @Bean
    public CreditLineRepository creditLineRepository() {
        return Mockito.mock(CreditLineRepository.class);
    }

    @Bean
    public CurrencyRepository currencyRepository() {
        return Mockito.mock(CurrencyRepository.class);
    }

    @Bean
    public CurrencyPairRepository currencyPairRepository() {
        return Mockito.mock(CurrencyPairRepository.class);
    }

    @Bean
    public SystemStateRepository systemStateRepository() {
        return Mockito.mock(SystemStateRepository.class);
    }

    @Bean
    public CurrencyPairSettleDateRepository currencyPairSettleDateRepository() {
        return Mockito.mock(CurrencyPairSettleDateRepository.class);
    }

    @Bean
    public HolidayRepository holidayRepository() {
        return Mockito.mock(HolidayRepository.class);
    }

    @Bean
    public ScheduledEventRepository scheduledEventRepository() {
        return Mockito.mock(ScheduledEventRepository.class);
    }

    @Bean
    public VenueRepository venueRepository() {
        return Mockito.mock(VenueRepository.class);
    }

    @Bean
    public RefdBrokerSessionMpidRepository refdBrokerSessionMpidRepository() {
        return Mockito.mock(RefdBrokerSessionMpidRepository.class);
    }

    @Bean
    public CacheManager cacheManager() {
        return Mockito.mock(CacheManager.class);
    }

}
