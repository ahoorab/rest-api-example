package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.model.Role;
import com.example.ex.model.User;
import com.example.ex.model.entity.admin.CreditPoolCreditLineCache;
import com.example.ex.model.entity.cache.CreditMatrixEntry;
import com.example.ex.model.type.CreditMethod;
import com.example.ex.model.type.DcType;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.CustomCacheRepository;
import com.example.ex.repository.cache.CacheRepository;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
public class CacheServiceTest {

    @Value("${ex.redis.path}")
    private String redisPath;
    
    @Value("${ex.redis.path.live}")
    private String livePath;

    @Value("${ex.redis.path.cache}")
    private String cachePath;

    @Value("${ex.redis.path.live.creditutilizations}")
    private String creditUtilizationPath;

    @Value("${ex.redis.path.refdata}")
    private String refDataPath;
    
    @Value("${ex.redis.path.refdata.creditpools}")
    private String creditPoolRefDataPath;
    
    @Value("${ex.redis.path.refdata.dealcodeordercreditpools}")
    private String dealCodeOrderCreditPoolRefDataPath;

    @Value("${ex.redis.path.refdata.dealcodes}")
    private String dealCodeRefDataPath;

    @TestConfiguration
    static class CacheServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public CacheService cacheService() {
            return new CacheService();
        }
        @Bean
        public AuthService authService() {
            return Mockito.mock(AuthService.class);
        }
    }

    @MockBean
    private CacheRepository cacheRepository;
    @MockBean
    private CustomCacheRepository customRepository;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private AuthService authService;
    
    @Before
    public void before() {
        Mockito.when(customRepository.findCreditPoolCreditLineData()).thenReturn(getCreditPoolCreditLines());
        Mockito.when(cacheRepository.findValues(redisPath + ":" + livePath + ":" + creditUtilizationPath)).thenReturn(getCreditUtilizations());
        Mockito.when(cacheRepository.findValues(redisPath + ":" + refDataPath + ":" + creditPoolRefDataPath)).thenReturn(getCreditPoolRefData());
        Mockito.when(cacheRepository.findValues(redisPath + ":" + refDataPath + ":" + dealCodeOrderCreditPoolRefDataPath)).thenReturn(getDealCodeOrderCreditPoolRefData());
        Mockito.when(cacheRepository.findValues(redisPath + ":" + refDataPath + ":" + dealCodeRefDataPath)).thenReturn(getDealCodeRefData());
        Mockito.when(cacheRepository.hasKey(Mockito.anyString())).thenReturn(true);
        User user = new User();
        user.setRole(Role.TW);
        Mockito.when(authService.getLoggedUser()).thenReturn(user);
        Mockito.when(authService.isAllowedByFirm(Mockito.any(User.class), Mockito.any(), Mockito.any())).thenReturn(true);
    }
    
    @Test
    public void shouldNoLookForCacheKey() {
        String key = redisPath + ":" + livePath + ":nonCacheKey";
        cacheService.findAll(key);
        Mockito.verify(cacheRepository, Mockito.times(1)).findValues(key);
        Mockito.verify(cacheRepository, Mockito.times(2)).hasKey(key);
    }
    
    @Test
    public void shouldNoLookForNonExistingKey() {
        String key = "nonExistingKey";
        String cachedKey = cachePath + ":" + livePath + ":" + key;
        key = redisPath + ":" + livePath + ":" + key;

        Mockito.when(cacheRepository.hasKey(key)).thenReturn(false);
        Mockito.when(cacheRepository.hasKey(cachedKey)).thenReturn(false);

        Collection<String> values = cacheService.findAll(key);

        Mockito.verify(cacheRepository, Mockito.times(2)).hasKey(key);
        Mockito.verify(cacheRepository, Mockito.times(0)).findValues(key);
        Mockito.verify(cacheRepository, Mockito.times(0)).hasKey(cachedKey);
        Mockito.verify(cacheRepository, Mockito.times(0)).findValues(cachedKey);
        assertThat(Collections.emptyList()).isEqualTo(values);
    }
    
    @Test
    public void shouldLookForCacheKey() {
        String key = "cachedkey";
        String cachedKey = cachePath + ":" + livePath + ":" + key;
        key = redisPath + ":" + livePath + ":" + key;

        Mockito.when(cacheRepository.hasKey(key)).thenReturn(false);
        Mockito.when(cacheRepository.hasKey(cachedKey)).thenReturn(true);

        cacheService.findAll(key);

        Mockito.verify(cacheRepository, Mockito.times(1)).hasKey(key);
        Mockito.verify(cacheRepository, Mockito.times(0)).findValues(key);
        Mockito.verify(cacheRepository, Mockito.times(1)).hasKey(cachedKey);
        Mockito.verify(cacheRepository, Mockito.times(1)).findValues(cachedKey);
    }

    @Test
    public void testGetUtilizationAmounts() {
        List<CreditMatrixEntry> data = cacheService.getUtilizationAmounts();
        List<CreditMatrixEntry> expectedData = getCreditMatrixData();
        assertThat(expectedData).containsExactlyInAnyOrder(data.toArray(new CreditMatrixEntry[0]));
    }
    
    @Test
    public void testGetCreditMatrix() {
        List<CreditMatrixEntry> matrix = cacheService.getCreditMatrix();
        List<CreditMatrixEntry> expectedData = getCreditMatrix();
        assertThat(expectedData).containsExactlyInAnyOrder(matrix.toArray(new CreditMatrixEntry[0]));
    }
    
    private Set<CreditPoolCreditLineCache> getCreditPoolCreditLines() {
        Set<CreditPoolCreditLineCache> cpcls = new HashSet<>();
        CreditPoolCreditLineCache cpcl = new CreditPoolCreditLineCache("scotia", "rbct", "100000000", "300000000", "75", Status.ENABLED);
        cpcl.setId(4);
        cpcls.add(cpcl);

        cpcl = new CreditPoolCreditLineCache("tdbank", "rbct", "100000000", "300000000", "75", Status.ENABLED);
        cpcl.setId(6);
        cpcls.add(cpcl);

        cpcl = new CreditPoolCreditLineCache("scotia", "hera", "100000000", "300000000", "75", Status.ENABLED);
        cpcl.setId(7);
        cpcls.add(cpcl);
        
        cpcl = new CreditPoolCreditLineCache("scotia", "frst", "100000000", "300000000", "75", Status.ENABLED);
        cpcl.setId(8);
        cpcls.add(cpcl);
        
        cpcl = new CreditPoolCreditLineCache("tdbank", "grn1", "200000000", "600000000", "75", Status.ENABLED);
        cpcl.setId(9);
        cpcls.add(cpcl);
        
        cpcl = new CreditPoolCreditLineCache("rbct", "scotia", "100000000", "300000000", "75", Status.ENABLED);
        cpcl.setId(10);
        cpcls.add(cpcl);
        
        cpcl = new CreditPoolCreditLineCache("rbct", "tdbank", "100000000", "300000000", "75", Status.ENABLED);
        cpcl.setId(11);
        cpcls.add(cpcl);
        
        cpcl = new CreditPoolCreditLineCache("frst", "scnd", "5000000", "15000000", "75", Status.ENABLED);
        cpcl.setId(13);
        cpcls.add(cpcl);
        
        cpcl = new CreditPoolCreditLineCache("scotia", "kitco", "200000000", "100000000", "75", Status.ENABLED);
        cpcl.setId(14);
        cpcls.add(cpcl);
        
        cpcl = new CreditPoolCreditLineCache("kitco", "scotia", "15000000", "5000000", "75", Status.ENABLED);
        cpcl.setId(15);
        cpcls.add(cpcl);
        return cpcls;
    }
        
    private List<CreditMatrixEntry> getCreditMatrix() {
        List<CreditMatrixEntry> creditMatrix = new ArrayList<>();
        creditMatrix.add(new CreditMatrixEntry("scotia","rbct",DcType.PRINCIPAL.toString(),CreditMethod.NET_SHORTS.toString(),Arrays.asList("20111026","20121026","20131026","20141026"),Arrays.asList(1L,2L,3L,4L),4,"scot-rbc",Status.ENABLED.toString(),225000000L,300000000L,75000000L,100000000L));
        creditMatrix.add(new CreditMatrixEntry("tdbank","rbct",DcType.PRINCIPAL.toString(),CreditMethod.NET_SHORTS.toString(),Arrays.asList("21181026","22181026","23181026","24181026"),Arrays.asList(1L,2L,3L,4L),6,"scot-pb",Status.ENABLED.toString(),225000000L,300000000L,75000000L,100000000L));
        creditMatrix.add(new CreditMatrixEntry("scotia","hera",DcType.PRINCIPAL.toString(),CreditMethod.NET_SHORTS.toString(),Arrays.asList("20181021","20181022","20181023","20181024"),Arrays.asList(1L,2L,3L,4L),7,"rbc-ib",Status.ENABLED.toString(),225000000L,300000000L,75000000L,100000000L));
        creditMatrix.add(new CreditMatrixEntry("scotia","frst",DcType.PRINCIPAL.toString(),CreditMethod.NET_SHORTS.toString(),Arrays.asList("10181026","20181026","30181026","40181026"),Arrays.asList(1L,2L,3L,4L),8,"tdbk-grn",Status.ENABLED.toString(),45000000L,60000000L,15000000L,20000000L));
        creditMatrix.add(new CreditMatrixEntry("tdbank","grn1",DcType.PRINCIPAL.toString(),CreditMethod.NET_SHORTS.toString(),Collections.emptyList(),Collections.emptyList(),9,"frst-scd",Status.ENABLED.toString(),11250000L,15000000L,3750000L,5000000L));
        creditMatrix.add(new CreditMatrixEntry("rbct","scotia",DcType.PRINCIPAL.toString(),CreditMethod.NET_SHORTS.toString(),Arrays.asList("20180121","20180122","20180123","20180124"),Arrays.asList(1L,2L,3L,4L),10,"scot-ktc",Status.ENABLED.toString(),75000000L,100000000L,150000000L,200000000L));
        creditMatrix.add(new CreditMatrixEntry("rbct","tdbank",DcType.PRINCIPAL.toString(),CreditMethod.NET_SHORTS.toString(),Arrays.asList("20180121","20180122","20180123","20180124"),Arrays.asList(1L,2L,3L,4L),11,"ktco-sco",Status.ENABLED.toString(),3750000L,5000000L,11250000L,15000000L));
        creditMatrix.add(new CreditMatrixEntry("frst","scnd",DcType.PRINCIPAL.toString(),CreditMethod.NET_SHORTS.toString(),Collections.emptyList(),Collections.emptyList(),13,"scot-tdb",Status.ENABLED.toString(),225000000L,300000000L,75000000L,100000000L));
        creditMatrix.add(new CreditMatrixEntry("scotia","kitco",null,null,Collections.emptyList(),Collections.emptyList(),14,null,null,null,null,null,null));
        creditMatrix.add(new CreditMatrixEntry("kitco","scotia",null,null,Collections.emptyList(),Collections.emptyList(),15,null,null,null,null,null,null));
        return creditMatrix;
    }
    
    private List<CreditMatrixEntry> getCreditMatrixData() {
        List<CreditMatrixEntry> creditMatrixData = getCreditMatrix();
        creditMatrixData.stream().forEach(entry -> {
            entry.setDealCodeType(null);
            entry.setCreditMethod(null);
            entry.setMnemonic(null);
            entry.setRefDataStatus(null);
            entry.setCpDailyLimit(null);
            entry.setCpDailyWarningLimit(null);
            entry.setCpNopLimit(null);
            entry.setCpNopWarningLimit(null);
        });
        return creditMatrixData;
    }
    
    private List<String> getCreditUtilizations() {
        List<String> creditUtilizations = new ArrayList<>();
        creditUtilizations.add("{ SequenceTime: \"12:18:01:939406405\", NOPUtilizationAmount: 123450001, UpdateTime: \"2018.11.01.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122081, SettleDate: 20180121, CreditPoolId: 11, SymbolId: 1, UtilizationAmount: 1 }");
        creditUtilizations.add("{ SequenceTime: \"12:19:02:939406405\", NOPUtilizationAmount: 123450002, UpdateTime: \"2018.11.02.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122082, SettleDate: 20180122, CreditPoolId: 11, SymbolId: 2, UtilizationAmount: 2 }");
        creditUtilizations.add("{ SequenceTime: \"12:18:03:939406405\", NOPUtilizationAmount: 123450003, UpdateTime: \"2018.11.03.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122083, SettleDate: 20180123, CreditPoolId: 11, SymbolId: 3, UtilizationAmount: 3  }");
        creditUtilizations.add("{ SequenceTime: \"12:18:04:939406405\", NOPUtilizationAmount: 123450004, UpdateTime: \"2018.11.04.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122084, SettleDate: 20180124, CreditPoolId: 11, SymbolId: 4, UtilizationAmount: 4 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:04:070614134\", NOPUtilizationAmount: 22076098004, UpdateTime: \"2018.11.04.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122634, SettleDate: 20181024, CreditPoolId: 7, SymbolId: 4, UtilizationAmount: 4 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:03:070614134\", NOPUtilizationAmount: 22076098003, UpdateTime: \"2018.11.03.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122633, SettleDate: 20181023, CreditPoolId: 7, SymbolId: 3, UtilizationAmount: 3 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:02:070614134\", NOPUtilizationAmount: 22076098002, UpdateTime: \"2018.11.02.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122632, SettleDate: 20181022, CreditPoolId: 7, SymbolId: 2, UtilizationAmount: 2 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:01:070614134\", NOPUtilizationAmount: 22076098001, UpdateTime: \"2018.11.01.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122631, SettleDate: 20181021, CreditPoolId: 7, SymbolId: 1, UtilizationAmount: 1 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:02:939405416\", NOPUtilizationAmount: 626450000002, UpdateTime: \"2018.11.02.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122082, SettleDate: 20180122, CreditPoolId: 10, SymbolId: 2, UtilizationAmount: 2 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:01:939405416\", NOPUtilizationAmount: 626450000001, UpdateTime: \"2018.11.01.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122081, SettleDate: 20180121, CreditPoolId: 10, SymbolId: 1, UtilizationAmount: 1 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:04:939405416\", NOPUtilizationAmount: 626450000004, UpdateTime: \"2018.11.04.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122084, SettleDate: 20180124, CreditPoolId: 10, SymbolId: 4, UtilizationAmount: 4 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:03:939405416\", NOPUtilizationAmount: 626450000003, UpdateTime: \"2018.11.03.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122083, SettleDate: 20180123, CreditPoolId: 10, SymbolId: 3, UtilizationAmount: 3 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:01:070615452\", NOPUtilizationAmount: 3156098401, UpdateTime: \"2018.11.01.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122631, SettleDate: 20111026, CreditPoolId: 4, SymbolId: 1, UtilizationAmount: 1 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:04:070615452\", NOPUtilizationAmount: 3156098404, UpdateTime: \"2018.11.04.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122634, SettleDate: 20141026, CreditPoolId: 4, SymbolId: 4, UtilizationAmount: 4 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:03:070615452\", NOPUtilizationAmount: 3156098403, UpdateTime: \"2018.11.03.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122633, SettleDate: 20131026, CreditPoolId: 4, SymbolId: 3, UtilizationAmount: 3 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:02:070615452\", NOPUtilizationAmount: 3156098402, UpdateTime: \"2018.11.02.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122632, SettleDate: 20121026, CreditPoolId: 4, SymbolId: 2, UtilizationAmount: 2 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:03:939404383\", NOPUtilizationAmount: 16914150003, UpdateTime: \"2018.11.03.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122083, SettleDate: 30181026, CreditPoolId: 8, SymbolId: 3, UtilizationAmount: 3 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:04:939404383\", NOPUtilizationAmount: 16914150004, UpdateTime: \"2018.11.04.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122084, SettleDate: 40181026, CreditPoolId: 8, SymbolId: 4, UtilizationAmount: 4 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:01:939404383\", NOPUtilizationAmount: 16914150001, UpdateTime: \"2018.11.01.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122081, SettleDate: 10181026, CreditPoolId: 8, SymbolId: 1, UtilizationAmount: 1 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:02:939404383\", NOPUtilizationAmount: 16914150002, UpdateTime: \"2018.11.02.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122082, SettleDate: 20181026, CreditPoolId: 8, SymbolId: 2, UtilizationAmount: 2 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:02:939403253\", NOPUtilizationAmount: 18948358002, UpdateTime: \"2018.11.02.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122082, SettleDate: 20281026, CreditPoolId: 5, SymbolId: 2, UtilizationAmount: 2 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:03:939403253\", NOPUtilizationAmount: 18948358003, UpdateTime: \"2018.11.03.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122083, SettleDate: 20381026, CreditPoolId: 5, SymbolId: 3, UtilizationAmount: 3 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:04:939403253\", NOPUtilizationAmount: 18948358004, UpdateTime: \"2018.11.04.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122084, SettleDate: 20481026, CreditPoolId: 5, SymbolId: 4, UtilizationAmount: 4 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:01:939403253\", NOPUtilizationAmount: 18948358001, UpdateTime: \"2018.11.01.11.18.37.935\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122081, SettleDate: 20181026, CreditPoolId: 5, SymbolId: 1, UtilizationAmount: 1 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:04:070612761\", NOPUtilizationAmount: 44028115604, UpdateTime: \"2018.11.04.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122634, SettleDate: 24181026, CreditPoolId: 6, SymbolId: 4, UtilizationAmount: 4 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:02:070612761\", NOPUtilizationAmount: 44028115602, UpdateTime: \"2018.11.02.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122632, SettleDate: 22181026, CreditPoolId: 6, SymbolId: 2, UtilizationAmount: 2 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:03:070612761\", NOPUtilizationAmount: 44028115603, UpdateTime: \"2018.11.03.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122633, SettleDate: 23181026, CreditPoolId: 6, SymbolId: 3, UtilizationAmount: 3 }");
        creditUtilizations.add("{ SequenceTime: \"11:18:01:070612761\", NOPUtilizationAmount: 44028115601, UpdateTime: \"2018.11.01.11.18.43.068\", PoolClass: \"CreditUtilizationInfo\", Sequence: 122631, SettleDate: 21181026, CreditPoolId: 6, SymbolId: 1, UtilizationAmount: 1 }");
        return creditUtilizations;
    }

    private List<String> getCreditPoolRefData() {
        List<String> creditPoolRefData = new ArrayList<>();
        creditPoolRefData.add("{ SequenceTime: \"07:25:31:035134466\", CPDailyLimit: 300000000, CPDailyWarningLimit: 225000000, CPNOPWarningLimit: 75000000, Mnemonic: \"tdbk-rbc\", CreditMethod: \"NetShorts\", PoolClass: \"CreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 80, CreditPoolId: 5, CPNOPLimit: 100000000, SymbolId: 1 }");
        creditPoolRefData.add("{ SequenceTime: \"07:25:31:059641469\", CPDailyLimit: 5000000, CPDailyWarningLimit: 3750000, CPNOPWarningLimit: 11250000, Mnemonic: \"ktco-sco\", CreditMethod: \"NetShorts\", PoolClass: \"CreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 86, CreditPoolId: 11, CPNOPLimit: 15000000, SymbolId: 1 }");
        creditPoolRefData.add("{ SequenceTime: \"07:25:31:035077911\", CPDailyLimit: 300000000, CPDailyWarningLimit: 225000000, CPNOPWarningLimit: 75000000, Mnemonic: \"scot-tdb\", CreditMethod: \"NetShorts\", PoolClass: \"CreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 78, CreditPoolId: 13, CPNOPLimit: 100000000, SymbolId: 1 }");
        creditPoolRefData.add("{ SequenceTime: \"07:25:31:059598906\", CPDailyLimit: 15000000, CPDailyWarningLimit: 11250000, CPNOPWarningLimit: 3750000, Mnemonic: \"frst-scd\", CreditMethod: \"NetShorts\", PoolClass: \"CreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 84, CreditPoolId: 9, CPNOPLimit: 5000000, SymbolId: 1 }");
        creditPoolRefData.add("{ SequenceTime: \"07:25:31:035180400\", CPDailyLimit: 300000000, CPDailyWarningLimit: 225000000, CPNOPWarningLimit: 75000000, Mnemonic: \"rbc-ib\", CreditMethod: \"NetShorts\", PoolClass: \"CreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 82, CreditPoolId: 7, CPNOPLimit: 100000000, SymbolId: 1 }");
        creditPoolRefData.add("{ SequenceTime: \"07:25:31:035111193\", CPDailyLimit: 300000000, CPDailyWarningLimit: 225000000, CPNOPWarningLimit: 75000000, Mnemonic: \"scot-rbc\", CreditMethod: \"NetShorts\", PoolClass: \"CreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 79, CreditPoolId: 4, CPNOPLimit: 100000000, SymbolId: 1 }");
        creditPoolRefData.add("{ SequenceTime: \"07:25:31:035160206\", CPDailyLimit: 300000000, CPDailyWarningLimit: 225000000, CPNOPWarningLimit: 75000000, Mnemonic: \"scot-pb\", CreditMethod: \"NetShorts\", PoolClass: \"CreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 81, CreditPoolId: 6, CPNOPLimit: 100000000, SymbolId: 1 }");
        creditPoolRefData.add("{ SequenceTime: \"07:25:31:059564233\", CPDailyLimit: 60000000, CPDailyWarningLimit: 45000000, CPNOPWarningLimit: 15000000, Mnemonic: \"tdbk-grn\", CreditMethod: \"NetShorts\", PoolClass: \"CreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 83, CreditPoolId: 8, CPNOPLimit: 20000000, SymbolId: 1 }");
        creditPoolRefData.add("{ SequenceTime: \"07:25:31:059622781\", CPDailyLimit: 100000000, CPDailyWarningLimit: 75000000, CPNOPWarningLimit: 150000000, Mnemonic: \"scot-ktc\", CreditMethod: \"NetShorts\", PoolClass: \"CreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 85, CreditPoolId: 10, CPNOPLimit: 200000000, SymbolId: 1 }");
        return creditPoolRefData;
    }

    private List<String> getDealCodeOrderCreditPoolRefData() {
        List<String> dealCodeOrderCreditPoolRefData = new ArrayList<>();
        dealCodeOrderCreditPoolRefData.add("{ SequenceTime: \"07:25:31:062040709\", CreditPoolId4: 0, CreditPoolId1: 4, CreditPoolId3: 0, CreditPoolId2: 0, PoolClass: \"DealcodeOrderCreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 97, DealcodeId: 205, SymbolId: 1 }");
        dealCodeOrderCreditPoolRefData.add("{ SequenceTime: \"07:25:31:085977839\", CreditPoolId4: 0, CreditPoolId1: 6, CreditPoolId3: 0, CreditPoolId2: 0, PoolClass: \"DealcodeOrderCreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 99, DealcodeId: 207, SymbolId: 1 }");
        dealCodeOrderCreditPoolRefData.add("{ SequenceTime: \"07:25:31:085932615\", CreditPoolId4: 0, CreditPoolId1: 7, CreditPoolId3: 0, CreditPoolId2: 0, PoolClass: \"DealcodeOrderCreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 98, DealcodeId: 206, SymbolId: 1 }");
        dealCodeOrderCreditPoolRefData.add("{ SequenceTime: \"07:25:31:061995317\", CreditPoolId4: 0, CreditPoolId1: 8, CreditPoolId3: 0, CreditPoolId2: 0, PoolClass: \"DealcodeOrderCreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 95, DealcodeId: 203, SymbolId: 1 }");
        dealCodeOrderCreditPoolRefData.add("{ SequenceTime: \"07:25:31:086014768\", CreditPoolId4: 0, CreditPoolId1: 9, CreditPoolId3: 0, CreditPoolId2: 0, PoolClass: \"DealcodeOrderCreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 101, DealcodeId: 209, SymbolId: 1 }");
        dealCodeOrderCreditPoolRefData.add("{ SequenceTime: \"07:25:31:062017968\", CreditPoolId4: 0, CreditPoolId1: 10, CreditPoolId3: 0, CreditPoolId2: 0, PoolClass: \"DealcodeOrderCreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 96, DealcodeId: 204, SymbolId: 1 }");
        dealCodeOrderCreditPoolRefData.add("{ SequenceTime: \"07:25:31:086030967\", CreditPoolId4: 0, CreditPoolId1: 11, CreditPoolId3: 0, CreditPoolId2: 9, PoolClass: \"DealcodeOrderCreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 102, DealcodeId: 210, SymbolId: 1 }");
        dealCodeOrderCreditPoolRefData.add("{ SequenceTime: \"07:25:31:085998957\", CreditPoolId4: 0, CreditPoolId1: 13, CreditPoolId3: 0, CreditPoolId2: 0, PoolClass: \"DealcodeOrderCreditPoolRefData\", RefDataStatus: \"Enabled\", Sequence: 100, DealcodeId: 208, SymbolId: 1 }");
        return dealCodeOrderCreditPoolRefData;
    }
    
    private List<String> getDealCodeRefData() {
        List<String> dealCodeRefData = new ArrayList<>();
        dealCodeRefData.add("{ SequenceTime: \"07:25:31:061950281\", PBFirmId: 3, AccountId: 0, FirmId: 9, Sequence: 93, SubAccountId: 0, DealcodeType: \"Principal\", Mnemonic: \"HERA\", SubPBFirmId: 0, PoolClass: \"DealcodeRefData\", RefDataStatus: \"Enabled\", DealcodeId: 209, SymbolId: 1 }");
        dealCodeRefData.add("{ SequenceTime: \"07:25:31:059717970\", PBFirmId: 0, AccountId: 0, FirmId: 5, Sequence: 89, SubAccountId: 0, DealcodeType: \"Principal\", Mnemonic: \"RBC\", SubPBFirmId: 0, PoolClass: \"DealcodeRefData\", RefDataStatus: \"Enabled\", DealcodeId: 205, SymbolId: 1 }");
        dealCodeRefData.add("{ SequenceTime: \"07:25:31:059660782\", PBFirmId: 0, AccountId: 0, FirmId: 3, Sequence: 87, SubAccountId: 0, DealcodeType: \"Principal\", Mnemonic: \"SCOT\", SubPBFirmId: 0, PoolClass: \"DealcodeRefData\", RefDataStatus: \"Enabled\", DealcodeId: 203, SymbolId: 1 }");
        dealCodeRefData.add("{ SequenceTime: \"07:25:31:061848098\", PBFirmId: 0, AccountId: 0, FirmId: 6, Sequence: 90, SubAccountId: 0, DealcodeType: \"Principal\", Mnemonic: \"KTCO\", SubPBFirmId: 0, PoolClass: \"DealcodeRefData\", RefDataStatus: \"Enabled\", DealcodeId: 206, SymbolId: 1 }");
        dealCodeRefData.add("{ SequenceTime: \"07:25:31:061926709\", PBFirmId: 4, AccountId: 0, FirmId: 8, Sequence: 92, SubAccountId: 0, DealcodeType: \"Principal\", Mnemonic: \"GRNL\", SubPBFirmId: 0, PoolClass: \"DealcodeRefData\", RefDataStatus: \"Enabled\", DealcodeId: 208, SymbolId: 1 }");
        dealCodeRefData.add("{ SequenceTime: \"07:25:31:059677711\", PBFirmId: 0, AccountId: 0, FirmId: 4, Sequence: 88, SubAccountId: 0, DealcodeType: \"Principal\", Mnemonic: \"TDBK\", SubPBFirmId: 0, PoolClass: \"DealcodeRefData\", RefDataStatus: \"Enabled\", DealcodeId: 204, SymbolId: 1 }");
        dealCodeRefData.add("{ SequenceTime: \"07:25:31:061900593\", PBFirmId: 3, AccountId: 0, FirmId: 7, Sequence: 91, SubAccountId: 0, DealcodeType: \"Principal\", Mnemonic: \"FRST\", SubPBFirmId: 0, PoolClass: \"DealcodeRefData\", RefDataStatus: \"Enabled\", DealcodeId: 207, SymbolId: 1 }");
        dealCodeRefData.add("{ SequenceTime: \"07:25:31:061973164\", PBFirmId: 16, AccountId: 0, FirmId: 15, Sequence: 94, SubAccountId: 0, DealcodeType: \"Principal\", Mnemonic: \"SCND\", SubPBFirmId: 7, PoolClass: \"DealcodeRefData\", RefDataStatus: \"Enabled\", DealcodeId: 210, SymbolId: 1 }");
        return dealCodeRefData;
    }
}