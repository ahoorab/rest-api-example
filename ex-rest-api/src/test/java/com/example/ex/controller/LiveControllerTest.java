package com.example.ex.controller;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.ex.configuration.ControllerTestConfiguration;
import com.example.ex.configuration.WebSocketsConfig;
import com.example.ex.service.CacheService;

@WebMvcTest
public class LiveControllerTest extends ControllerTestConfiguration {
    
    @MockBean
    private SimpMessagingTemplate template;
    @Autowired
    private WebSocketsConfig webSocketsConfig;
    @Autowired
    protected ThreadPoolTaskExecutor webSocketsTaskExecutor;

    @Test
    public void shouldNotPublishCreditMatrixUpdatesWhenNoUsersConnected() throws Exception {
        LiveController controller = Mockito.spy(LiveController.class);
        controller.webSocketsConfig = webSocketsConfig;
        controller.webSocketsTaskExecutor = Mockito.mock(ThreadPoolTaskExecutor.class);
        Mockito.doCallRealMethod().when(controller).publishCreditMatrixDataUpdates();

        controller.publishCreditMatrixDataUpdates();

        Mockito.verify(controller.webSocketsTaskExecutor, Mockito.times(0)).execute(Mockito.any(Runnable.class));
        Mockito.verify(template, Mockito.times(0)).convertAndSend(Mockito.eq(LiveController.WEB_SOCKET_CREDIT_MATRIX),Mockito.any(List.class));
        Mockito.verify(cacheService, Mockito.times(0)).getUtilizationAmounts();
    }
    
    @Test
    public void shouldPublishCreditMatrixUpdatesWhenUsersConnected() throws Exception {
        LiveController controller = Mockito.spy(LiveController.class);
        controller.webSocketsConfig = Mockito.mock(WebSocketsConfig.class);
        controller.template = Mockito.mock(SimpMessagingTemplate.class);
        controller.cacheService = Mockito.mock(CacheService.class);
        controller.tradeReportsKey = "";
        controller.webSocketsTaskExecutor = webSocketsTaskExecutor;

        Mockito.doCallRealMethod().when(controller).publishCreditMatrixDataUpdates();
        Mockito.doCallRealMethod().when(controller).publishUpdates(Mockito.anyString(), Mockito.any());
        Mockito.when(controller.webSocketsConfig.getTopicCount(LiveController.WEB_SOCKET_CREDIT_MATRIX)).thenReturn(1);

        webSocketsTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        webSocketsTaskExecutor.setAwaitTerminationSeconds(5);
        controller.publishCreditMatrixDataUpdates();
        webSocketsTaskExecutor.shutdown();

        Mockito.verify(controller.webSocketsConfig,Mockito.times(1)).getTopicCount(LiveController.WEB_SOCKET_CREDIT_MATRIX);
        Mockito.verify(controller.template, Mockito.times(1)).convertAndSend(Mockito.eq(LiveController.WEB_SOCKET_CREDIT_MATRIX),Mockito.any(List.class));
        Mockito.verify(controller.cacheService, Mockito.times(1)).getUtilizationAmounts();
    }
    
}