package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.exception.BusinessValidationException;
import com.example.ex.exception.EntityAlreadyExistsException;
import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.BlockedCounterParty;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.model.type.BrokerType;
import com.example.ex.model.type.DcType;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.DealCodeRepository;

@RunWith(SpringRunner.class)
public class DealCodeServiceTest {

    @TestConfiguration
    static class DealCodeServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public DealCodeService dealCodeService() {
            return new DealCodeService();
        }
    }

    @MockBean
    private DealCodeRepository dealCodeRepository;
    @Autowired
    private DealCodeService dealCodeService;
    @Autowired
    private FirmService firmService;
    @Autowired
    private MpidService mpidService;
    @Autowired
    private BlockedCounterPartyService blockedCounterPartyService;
    @Autowired
    private CacheManager cacheManager;
    
    private DealCode getTestDealCode() {
        return new DealCode("exx","Example","EXXX",null,DcType.EXX,"exx","exx","exx",null,null,Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveDealCodeWithDuplicatedHandle() {
        DealCode existingDc = getTestDealCode();
        existingDc.setId(1);
        
        Mockito.when(firmService.findByHandle("exx")).thenReturn(new Firm());
        Mockito.when(dealCodeRepository.findByHandle(existingDc.getHandle())).thenReturn(existingDc);

        DealCode dc = getTestDealCode();
        dealCodeService.save(dc);
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveDealCodeWithDuplicatedMnemonic() {
        DealCode existingDc = getTestDealCode();
        existingDc.setId(1);
        
        Mockito.when(firmService.findByHandle("exx")).thenReturn(new Firm());
        Mockito.when(dealCodeRepository.findByMnemonic(existingDc.getMnemonic())).thenReturn(existingDc);

        DealCode dc = getTestDealCode();
        dealCodeService.save(dc);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveDealCodeWithoutFirm() {
        DealCode dc = getTestDealCode();
        dc.setFirm(null);
        dealCodeService.save(dc);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveDealCodeWithInvalidFirm() {
        DealCode dc = getTestDealCode();
        dc.setFirm("xxx");
        dealCodeService.save(dc);
    }
    
    @Test
    public void shouldSaveDealCodeWithNullPbFirm() {
        DealCode dc = getTestDealCode();
        dc.setPbFirm(null);

        Mockito.when(firmService.findByHandle("exx")).thenReturn(new Firm());
        Mockito.when(dealCodeRepository.save(dc)).thenReturn(dc);
        DealCode savedDc = dealCodeService.save(dc);
        
        assertThat(savedDc).isEqualTo(dc);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveDealCodeWithInvalidPbFirm() {
        DealCode dc = getTestDealCode();
        dc.setPbFirm("xxx");

        Mockito.when(firmService.findByHandle("exx")).thenReturn(new Firm());
        dealCodeService.save(dc);
    }

    @Test
    public void shouldSaveDealCodeWithNullSubPbFirm() {
        DealCode dc = getTestDealCode();
        dc.setSubPbFirm(null);

        Mockito.when(firmService.findByHandle("exx")).thenReturn(new Firm());
        Mockito.when(dealCodeRepository.save(dc)).thenReturn(dc);
        DealCode savedDc = dealCodeService.save(dc);
        
        assertThat(savedDc).isEqualTo(dc);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveDealCodeWithInvalidSubPbFirm() {
        DealCode dc = getTestDealCode();
        dc.setSubPbFirm("xxx");

        Mockito.when(firmService.findByHandle("exx")).thenReturn(new Firm());
        dealCodeService.save(dc);
    }
    
    @Test
    public void shouldSaveDealCodeWithAllFirmsHandle() {
        DealCode dc = getTestDealCode();

        Mockito.when(firmService.findByHandle("exx")).thenReturn(new Firm());
        Mockito.when(dealCodeRepository.save(dc)).thenReturn(dc);

        DealCode savedDc = dealCodeService.save(dc);
        
        assertThat(savedDc).isEqualTo(dc);
    }
    
    @Test
    public void shouldUpdatePair() {
        DealCode existingDc = getTestDealCode();
        existingDc.setId(1);
        
        Mockito.when(firmService.findByHandle("exx")).thenReturn(new Firm());

        Mockito.when(dealCodeRepository.save(existingDc)).thenReturn(existingDc);
        Mockito.when(dealCodeRepository.findByHandle(existingDc.getHandle())).thenReturn(existingDc);
        Mockito.when(dealCodeRepository.findByMnemonic(existingDc.getMnemonic())).thenReturn(existingDc);
        
        DealCode savedDealCode = dealCodeService.save(existingDc);
 
        assertThat(savedDealCode).isEqualTo(existingDc);
    }
    
    @Test
    public void shouldReturnDealCodesByFirm() {
        List<DealCode> dlcs1 = new ArrayList<>();
        dlcs1.add(new DealCode("exx","Example","EXXX","",DcType.EXX,"exx",null,null,"ledger","ledgerAccount",Status.ENABLED));
        dlcs1.add(new DealCode("scotia","ScotiaBank","SCOT",null,DcType.PRINCIPAL,"scotia","exx",null,"ledger","ledgerAccount",Status.ENABLED));

        List<DealCode> dlcs2 = new ArrayList<>();
        dlcs2.add(new DealCode("exx","Example","EXXX","",DcType.EXX,"exx",null,null,"ledger","ledgerAccount",Status.ENABLED));

        List<DealCode> dlcs3 = new ArrayList<>();
        dlcs3.add(new DealCode("test2","test2","test2",null,DcType.PRINCIPAL,"test2",null,"exx","ledger","ledgerAccount",Status.ENABLED));
        dlcs3.add(new DealCode("test3","test3","test3",null,DcType.PRINCIPAL,"exx","exx","exx","ledger","ledgerAccount",Status.ENABLED));

        Firm firm = new Firm();

        firm.setHandle("exx");

        Mockito.when(dealCodeRepository.findByFirm(firm.getHandle())).thenReturn(dlcs1);
        Mockito.when(dealCodeRepository.findByPbFirm(firm.getHandle())).thenReturn(dlcs2);;
        Mockito.when(dealCodeRepository.findBySubPbFirm(firm.getHandle())).thenReturn(dlcs3);;
        
        Set<DealCode> dealCodes = dealCodeService.findByFirm(firm.getHandle());
 
        Set<DealCode> expectedDealCodes = new HashSet<>();
        expectedDealCodes.addAll(dlcs2);
        expectedDealCodes.addAll(dlcs3);
        expectedDealCodes.addAll(dlcs1);

        assertThat(dealCodes).containsExactlyInAnyOrder(expectedDealCodes.toArray(new DealCode[4]));
    }
    
    @Test(expected=BusinessValidationException.class)
    public void shouldNotSaveDealCodeWithPbClientAndNoPbFirm() {
        DealCode dc = getTestDealCode();
        dc.setDcType(DcType.PBCLIENT);
        dc.setPbFirm(null);

        Mockito.when(firmService.findByHandle("exx")).thenReturn(new Firm());

        dealCodeService.save(dc);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingDealCode() {
        DealCode dc = getTestDealCode();
        dc.setId(1);

        Mockito.when(dealCodeRepository.findById(dc.getId())).thenReturn(Optional.empty());

        dealCodeService.deleteById(dc.getId());
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteADealCodeReferencedByMpids() {
        DealCode dc = getTestDealCode();
        dc.setId(1);
        
        Mpid mpid = new Mpid("first","First eagle","FRST","frst","scotia",BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED);
        List<Mpid> mpids = new ArrayList<>();
        mpids.add(mpid);

        Mockito.when(dealCodeRepository.findById(dc.getId())).thenReturn(Optional.of(dc));
        Mockito.when(mpidService.findByDealCode(dc.getHandle())).thenReturn(mpids);
        Mockito.when(blockedCounterPartyService.findByDealCode(dc.getHandle())).thenReturn(new ArrayList<>());
        List<DealCode> list = new ArrayList<>(); 
        list.add(dc);
        Mockito.when(dealCodeRepository.findAllById(Arrays.asList(dc.getId()))).thenReturn(list);

        dealCodeService.deleteById(dc.getId());
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteADealCodeReferencedByBlockedCounterParties() {
        List<BlockedCounterParty> bcps = new ArrayList<>();
        bcps.add(new BlockedCounterParty());

        DealCode dc = getTestDealCode();
        dc.setId(1);

        Mockito.when(dealCodeRepository.findById(dc.getId())).thenReturn(Optional.of(dc));
        Mockito.when(mpidService.findByDealCode(dc.getHandle())).thenReturn(new ArrayList<>());
        Mockito.when(blockedCounterPartyService.findByDealCode(dc.getHandle())).thenReturn(bcps);
        List<DealCode> list = new ArrayList<>(); 
        list.add(dc);
        Mockito.when(dealCodeRepository.findAllById(Arrays.asList(dc.getId()))).thenReturn(list);

        dealCodeService.deleteById(dc.getId());
    }
    
    @Test
    public void shouldDeleteADealCode() {
        DealCode dc = getTestDealCode();
        dc.setId(1);
        
        Mockito.when(dealCodeRepository.findById(dc.getId())).thenReturn(Optional.of(dc));
        Mockito.when(mpidService.findByDealCode(dc.getHandle())).thenReturn(new ArrayList<>());
        Mockito.when(blockedCounterPartyService.findByDealCode(dc.getHandle())).thenReturn(new ArrayList<>());
        List<DealCode> list = new ArrayList<>(); 
        list.add(dc);
        Mockito.when(dealCodeRepository.findAllById(Arrays.asList(dc.getId()))).thenReturn(list);

        dealCodeService.deleteById(dc.getId());
        
        verify(dealCodeRepository, times(1)).deleteById(dc.getId());
    }
    
    @Test
    public void shouldNotUpdateCachedDealcode() {
        DealCode dc = getTestDealCode();
        
        List<DealCode> dealCodes = new ArrayList<>();
        dealCodes.add(dc);

        Map<String,DealCode> cacheImplementation = new ConcurrentHashMap<>();
        cacheImplementation.put(dc.getHandle(), dc);
        
        Mockito.when(dealCodeRepository.findAll()).thenReturn(dealCodes);
        Cache cache = Mockito.mock(Cache.class);
        Mockito.when(cacheManager.getCache(DealCodeService.CACHE)).thenReturn(cache);
        ValueWrapper wrapper = Mockito.mock(ValueWrapper.class);
        Mockito.when(cache.get(dc.getHandle())).thenReturn(wrapper);
        Mockito.when(wrapper.get()).thenReturn(dc);
        Mockito.when(cache.getNativeCache()).thenReturn(cacheImplementation);

        dealCodeService.cacheEvict();
        
        verify(cache, times(0)).put(Mockito.any(), Mockito.any());
        verify(cache, times(0)).evict(Mockito.any());
    }
    
    @Test
    public void shouldUpdateCachedDealcode() {
        DealCode dc = getTestDealCode();
        DealCode updatedDc = getTestDealCode();
        updatedDc.setDescription("new");
        
        List<DealCode> dealCodes = new ArrayList<>();
        dealCodes.add(updatedDc);

        Map<String,DealCode> cacheImplementation = new ConcurrentHashMap<>();
        cacheImplementation.put(dc.getHandle(), dc);
        
        Mockito.when(dealCodeRepository.findAll()).thenReturn(dealCodes);
        Cache cache = Mockito.mock(Cache.class);
        Mockito.when(cacheManager.getCache(DealCodeService.CACHE)).thenReturn(cache);
        ValueWrapper wrapper = Mockito.mock(ValueWrapper.class);
        Mockito.when(cache.get(dc.getHandle())).thenReturn(wrapper);
        Mockito.when(wrapper.get()).thenReturn(dc);
        Mockito.when(cache.getNativeCache()).thenReturn(cacheImplementation);

        dealCodeService.cacheEvict();
        
        verify(cache, times(1)).put(updatedDc.getHandle(), updatedDc);
        verify(cache, times(1)).put(dc.getHandle(), updatedDc);
        verify(cache, times(0)).evict(Mockito.any());
    }
    
    @Test
    public void shouldEvictDealcode() {
        DealCode dc = getTestDealCode();
        DealCode updatedDc = getTestDealCode();
        updatedDc.setHandle("new");
        
        List<DealCode> dealCodes = new ArrayList<>();
        dealCodes.add(updatedDc);

        Map<String,DealCode> cacheImplementation = new ConcurrentHashMap<>();
        cacheImplementation.put(dc.getHandle(), dc);
        
        Mockito.when(dealCodeRepository.findAll()).thenReturn(dealCodes);
        Cache cache = Mockito.mock(Cache.class);
        Mockito.when(cacheManager.getCache(DealCodeService.CACHE)).thenReturn(cache);
        ValueWrapper wrapper = Mockito.mock(ValueWrapper.class);
        Mockito.when(cache.get(dc.getHandle())).thenReturn(wrapper);
        Mockito.when(wrapper.get()).thenReturn(dc);
        Mockito.when(cache.getNativeCache()).thenReturn(cacheImplementation);

        dealCodeService.cacheEvict();
        
        verify(cache, times(0)).put(updatedDc.getHandle(), updatedDc);
        verify(cache, times(0)).put(dc.getHandle(), updatedDc);
        verify(cache, times(0)).evict(updatedDc.getHandle());
        verify(cache, times(1)).evict(dc.getHandle());
    }
    
}