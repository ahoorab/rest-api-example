package com.example.ex.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.ex.model.Role;
import com.example.ex.model.User;
import com.example.ex.service.AuthService;

import lombok.AccessLevel;
import lombok.Setter;

@Aspect
@Configuration
public class DataFilterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataFilterAspect.class);

    @Setter(AccessLevel.PACKAGE)
    @PersistenceContext(unitName = "admin")
    private EntityManager entityManager;
    @Autowired
    private AuthService authService;

    @Before("execution(* com.example.ex.service.*.*(..)) && !execution(* com.example.ex.service.*.getFromCache*(..)) && !execution(* com.example.ex.service.AuthService.*(..))()")
    public void before(JoinPoint joinPoint) {
        User user = authService.getLoggedUser();
        if (user!= null && !Role.TW.equals(user.getRole())) {
            LOGGER.debug("logged user role {}, so enabling firmFilter and dealCodeFilter", user);
            Session session = entityManager.unwrap(Session.class);

            Filter firmFilter = session.enableFilter("firmFilter");
            firmFilter.setParameter("firm", user.getDealCode().getFirm());

            Filter firmFilterRole = session.enableFilter("firmFilter"+user.getRole());
            firmFilterRole.setParameter("firm", user.getDealCode().getFirm());

            Filter dealCodeFilter = session.enableFilter("dealCodeFilter");
            dealCodeFilter.setParameter("dealcode", user.getDealCode().getHandle());
        }
    }
}