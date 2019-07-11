package com.example.ex.configuration;

import javax.persistence.EntityManagerFactory;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
import com.example.ex.repository.configuration.AdminDatabaseConfig;
import com.example.ex.repository.configuration.TimeSeriesDatabaseConfig;
import com.example.ex.repository.ts.FixMessageStatusToBrokerRepository;
import com.example.ex.rest.AuthClient;
import com.example.ex.service.AuthService;

@Configuration
public class ControllerTestBeansConfiguration {
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
    public FixMessageStatusToBrokerRepository fixMessageStatusToBrokerRepository() {
        return Mockito.mock(FixMessageStatusToBrokerRepository.class);
    }

    @Bean
    public AdminDatabaseConfig adminDatabaseConfig() {
        return Mockito.mock(AdminDatabaseConfig.class);
    }

    @Bean
    public TimeSeriesDatabaseConfig timeSeriesDatabaseConfig() {
        return Mockito.mock(TimeSeriesDatabaseConfig.class);
    }
    
    @Bean
    public AuthClient authClient() {
        return Mockito.mock(AuthClient.class);
    }

    @Bean
    public AuthService authService() {
        return Mockito.mock(AuthService.class);
    }

    @Bean
    public EntityManagerFactory admin() {
        return Mockito.mock(EntityManagerFactory.class);
    }

}
