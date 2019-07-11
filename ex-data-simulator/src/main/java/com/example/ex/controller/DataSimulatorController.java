package com.example.ex.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.DataType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.BookKeys;
import com.example.ex.LiveKeys;
import com.example.ex.RefDataKeys;
import com.example.ex.model.entity.admin.CreditPoolCreditLineCache;
import com.example.ex.service.CacheService;

@RestController
public class DataSimulatorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSimulatorController.class);

    private static boolean started;
    
    @Value("${ex.redis.path}")
    private String redisPath;

    @Value("${ex.redis.path.live}")
    private String livePath;

    @Value("${ex.redis.path.live.creditutilizations}")
    private String creditUtilizationPath;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private BookKeys bookKeys;
    @Autowired
    private LiveKeys liveKeys;
    @Autowired
    private RefDataKeys refDataKeys;

    @GetMapping(value = "/backup")
    public void backup() {
        Set<String> keys = cacheService.getAllKeys("rdis*");
        for (String key : keys) {
            System.out.println(key);
            System.out.println(cacheService.findAll(key));
        }
        
    }

    @GetMapping(value = "/start")
    public static void start() {
        started = true;
    }

    @GetMapping(value = "/stop")
    public static void stop() {
        started = false;
    }

    @Scheduled(fixedDelayString = "${ex.simulator.data.creditutilization.delay}")
    public void updateCreditUtilization() {
        if (started) {
            LOGGER.info("running data simulator");
            Set<CreditPoolCreditLineCache> clcps = cacheService.getCreditPoolCreditLineData();
            String key = redisPath + ":" + livePath + ":" + creditUtilizationPath;
            cacheService.clear(key);
            clcps.forEach(clcp -> {
                List<String> settleDates = Arrays.asList(randomDate(), randomDate(), randomDate(), randomDate());
                List<String> utilAmount = Arrays.asList("100000000", "200000000", "300000000", "400000000");
                Collections.shuffle(settleDates);
                Collections.shuffle(utilAmount);
                for (int i = 0; i < 4; i++) {
                    String cu = "{ \"SequenceTime\": \"08:45:33:039436562\", \"NOPUtilizationAmount\": 123450000, \"UpdateTime\": \"2018.11.08.08.45.33.036\", \"PoolClass\": \"CreditUtilizationInfo\", \"Sequence\": 39724, \"SettleDate\": "
                            + settleDates.get(i) + ", \"CreditPoolId\": " + clcp.getId()
                            + ", \"SymbolId\": 1, \"UtilizationAmount\": " + utilAmount.get(i) + " }";
                    String hashKey = clcp.getId() + "," + settleDates.get(i);
                    cacheService.cache(key, hashKey, cu);
                }
            });
        }
    }

    private String randomDate() {
        String date = "2018";
        Integer month = new Random().nextInt(12);
        if (month < 10) {
            date += "0" + month;
        } else {
            date += month;
        }
        Integer day = new Random().nextInt(29);
        if (day < 10) {
            date += "0" + day;
        } else {
            date += day;
        }
        return date;
    }

}