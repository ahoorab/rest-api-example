package com.example.ex.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.model.Role;
import com.example.ex.model.User;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.service.AuthService;
import com.example.ex.service.ServiceTestContextConfiguration;

@RunWith(SpringRunner.class)
public class DataFilterAspectTest {
    
    @TestConfiguration
    static class DataFilterTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public DataFilterAspect dataFilterAspect() {
            return new DataFilterAspect();
        }
    }
    
    @Autowired
    private DataFilterAspect dataFilterAspect;
    @MockBean
    private AuthService authService;
    @MockBean(name="admin")
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Before
    public void init() {
        entityManager = Mockito.mock(EntityManager.class);
        dataFilterAspect.setEntityManager(entityManager);
    }
    
    @Test
    public void shouldNotEnableFiltersForTW() {
        User exx = new User();
        exx.setRole(Role.TW);

        Mockito.when(authService.getLoggedUser()).thenReturn(exx);
        
        dataFilterAspect.before(null);
        
        Mockito.verify(entityManagerFactory, Mockito.times(0)).unwrap(Session.class);
    }
    
    @Test
    public void shouldEnableFiltersForCP() {
        shouldEnableFiltersFor(Role.CP);
    }

    @Test
    public void shouldEnableFiltersForTP() {
        shouldEnableFiltersFor(Role.TP);
    }

    public void shouldEnableFiltersFor(Role role) {
        User exx = new User();
        DealCode dealCode = new DealCode();
        exx.setRole(role);
        exx.setDealCode(dealCode);
        Session session = Mockito.mock(Session.class);
        Filter filter = Mockito.mock(Filter.class);

        Mockito.when(authService.getLoggedUser()).thenReturn(exx);
        Mockito.when(entityManager.unwrap(Session.class)).thenReturn(session);
        Mockito.when(session.enableFilter(Mockito.anyString())).thenReturn(filter);
        
        dataFilterAspect.before(null);
        
        Mockito.verify(entityManager, Mockito.times(1)).unwrap(Session.class);
        Mockito.verify(session, Mockito.times(1)).enableFilter("firmFilter");
        Mockito.verify(session, Mockito.times(1)).enableFilter("firmFilter"+exx.getRole());
        Mockito.verify(session, Mockito.times(1)).enableFilter("dealCodeFilter");
    }

}