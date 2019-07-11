package com.example.ex.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.DataType;
import org.springframework.stereotype.Service;

import com.example.ex.model.Role;
import com.example.ex.model.User;
import com.example.ex.model.entity.admin.CreditPoolCreditLineCache;
import com.example.ex.model.entity.admin.RefdBrokerSessionMpid;
import com.example.ex.model.entity.cache.CreditMatrixEntry;
import com.example.ex.repository.admin.CustomCacheRepository;
import com.example.ex.repository.admin.RefdBrokerSessionMpidRepository;
import com.example.ex.repository.cache.CacheRepository;
import com.example.ex.service.util.IntegerAttributeGetter;
import com.example.ex.service.util.LongAttributeGetter;
import com.example.ex.service.util.ObjectAttributeGetter;

/**
 * Business layer object for the Cache repository
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class CacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);

    private static final String CREDIT_POOL_ID1 = "CreditPoolId1";
    private static final String CREDIT_POOL_ID = "CreditPoolId";
    private static final String DEAL_CODE_ID = "DealcodeId";
    private static final String DEAL_CODE_TYPE = "DealcodeType";
    private static final String SETTLE_DATE = "SettleDate";
    private static final String UTILIZATION_AMOUNT = "UtilizationAmount";
    private static final String CREDIT_METHOD = "CreditMethod";
    private static final String MNEMONIC = "Mnemonic";
    private static final String REF_DATA_STATUS = "RefDataStatus";
    private static final String CP_DAILY_WARNING_LIMIT = "CPDailyWarningLimit";
    private static final String CP_DAILY_LIMIT = "CPDailyLimit";
    private static final String CP_NOP_WARNING_LIMIT = "CPNOPWarningLimit";
    private static final String CP_NOP_LIMIT = "CPNOPLimit";
    private static final String BROKER_SESSION_MPID = "BrokerSessionMPID";
    private static final String BROKER_SESSION_MPID_ID = "BrokerSessionMPIDID";

    @Value("${ex.redis.path}")
    private String redisPath;

    @Value("${ex.redis.path.refdata}")
    private String refDataPath;

    @Value("${ex.redis.path.refdata.creditpools}")
    private String creditPoolRefDataPath;

    @Value("${ex.redis.path.refdata.dealcodeordercreditpools}")
    private String dealCodeOrderCreditPoolRefDataPath;

    @Value("${ex.redis.path.refdata.dealcodes}")
    private String dealCodeRefDataPath;

    @Value("${ex.redis.path.live}")
    private String livePath;

    @Value("${ex.redis.path.book}")
    private String bookPath;

    @Value("${ex.redis.path.live.creditutilizations}")
    private String creditUtilizationPath;

    @Value("${ex.redis.path.book.bookedorders}")
    private String bookedOrdersPath;

    @Value("#{'${ex.redis.live.cachedkeys}'.split(',')}")
    private List<String> cachedKeys;

    @Value("${ex.redis.path.cache}")
    protected String cachePath;

    @Autowired
    private CacheRepository cacheRepository;
    @Autowired
    private CustomCacheRepository customRepository;
    @Autowired
    private RefdBrokerSessionMpidRepository refdBrokerSessionMpidRepository;
    @Autowired
    private AuthService authService;

    private ObjectAttributeGetter stringGetter = new ObjectAttributeGetter();
    private LongAttributeGetter longGetter = new LongAttributeGetter();
    private IntegerAttributeGetter integerGetter = new IntegerAttributeGetter();

    public void cache(String key, String hashKey, String entity) {
        cacheRepository.cache(key, hashKey, entity);
    }

    public Optional<Object> findById(Integer id) {
        return Optional.empty();
    }

    public void clear(String key) {
        cacheRepository.clear(key);
    }

    public DataType getType(String key) {
        return cacheRepository.getType(key);
    }

    public Collection<String> findAll(String key) {
        if (!cacheRepository.hasKey(key)) {
            String[] splittedKey = key.split(":");
            if (splittedKey.length > 0 && cachedKeys.contains(splittedKey[splittedKey.length - 1])) {
                key = key.replaceFirst(redisPath, cachePath);
            }
        }
        if (cacheRepository.hasKey(key)) {
            return cacheRepository.findValues(key);
        }
        return Collections.emptyList();
    }

    public Set<String> getAllKeys(String prefix) {
        return cacheRepository.getAllKeys(prefix).stream().sorted().collect(Collectors.toSet());
    }

    public Set<CreditPoolCreditLineCache> getCreditPoolCreditLineData() {
        LOGGER.debug("getting data for credit matrix from table creditpool and creditline");
        return customRepository.findCreditPoolCreditLineData();
    }

    private void fillUtilizationAmounts(CreditMatrixEntry entry, CreditPoolCreditLineCache cpcl) {
        entry.setGrantorFirm(cpcl.getGrantorFirm());
        entry.setGranteeFirm(cpcl.getGranteeFirm());

        List<JSONObject> creditUitilizations = getData(redisPath + ":" + livePath + ":" + creditUtilizationPath);

        LOGGER.debug("filtering credit utilizations by {} <{}>", CREDIT_POOL_ID, cpcl.getId());
        List<JSONObject> creditUtils = creditUitilizations.stream()
                .filter(cu -> cpcl.getId().toString().equals(stringGetter.apply(cu, CREDIT_POOL_ID))).sorted((cu1,
                        cu2) -> stringGetter.apply(cu1, SETTLE_DATE).compareTo(stringGetter.apply(cu2, SETTLE_DATE)))
                .collect(Collectors.toList());
        LOGGER.debug("credit utilizations by {} are <{}>", CREDIT_POOL_ID, creditUtils);

        // Typically 4 items
        creditUtils.stream().forEachOrdered(creditUtil -> {
            LOGGER.debug("processing credit utilization <{}>", creditUtil);
            entry.getUtilizationAmounts().add(longGetter.apply(creditUtil, UTILIZATION_AMOUNT));
            entry.getSettleDates().add(stringGetter.apply(creditUtil, SETTLE_DATE));
        });
    }

    public List<CreditMatrixEntry> getUtilizationAmounts() {
        User user = authService.getLoggedUser();
        List<CreditMatrixEntry> listView = new ArrayList<>();

        Set<CreditPoolCreditLineCache> cpcls = getCreditPoolCreditLineData();
        cpcls.stream().forEach(cpcl -> {
            if (authService.isAllowedByFirm(user, cpcl.getGranteeFirm(), cpcl.getGrantorFirm())) {
                CreditMatrixEntry entry = new CreditMatrixEntry();
                fillUtilizationAmounts(entry, cpcl);
                entry.setCreditPoolId(cpcl.getId());
                listView.add(entry);
            }
        });
        return listView;
    }

    public List<CreditMatrixEntry> getCreditMatrix() {
        User user = authService.getLoggedUser();
        List<CreditMatrixEntry> listView = new ArrayList<>();

        List<JSONObject> creditPoolRefData = getData(redisPath + ":" + refDataPath + ":" + creditPoolRefDataPath);
        List<JSONObject> dealCodeOrderCreditPoolRefData = getData(
                redisPath + ":" + refDataPath + ":" + dealCodeOrderCreditPoolRefDataPath);
        List<JSONObject> dealCodeRefData = getData(redisPath + ":" + refDataPath + ":" + dealCodeRefDataPath);

        Set<CreditPoolCreditLineCache> cpcls = getCreditPoolCreditLineData();
        LOGGER.debug("credit pool credit line data for credit matrix is <{}>", cpcls);

        cpcls.stream().forEach(cpcl -> {
            if (authService.isAllowedByFirm(user, cpcl.getGranteeFirm(), cpcl.getGrantorFirm())) {
                LOGGER.debug("processing credit pool credit line data <{}>", cpcl);
                CreditMatrixEntry entry = new CreditMatrixEntry();
                entry.setCreditPoolId(cpcl.getId());

                LOGGER.debug("filtering deal code order credit pool ref data by {} <{}>", CREDIT_POOL_ID1,
                        cpcl.getId());
                Optional<JSONObject> dealCodeOrderCreditPool = dealCodeOrderCreditPoolRefData.stream()
                        .filter(dcocp -> cpcl.getId().toString().equals(stringGetter.apply(dcocp, CREDIT_POOL_ID1)))
                        .findFirst();

                LOGGER.debug("deal code order credit pool ref data by {} is <{}>", CREDIT_POOL_ID1,
                        dealCodeOrderCreditPool);
                if (dealCodeOrderCreditPool.isPresent()) {
                    String dealCodeId = stringGetter.apply(dealCodeOrderCreditPool.get(), DEAL_CODE_ID);
                    LOGGER.debug("filtering deal code ref data by {} <{}>", DEAL_CODE_ID, dealCodeId);
                    if (dealCodeId != null) {
                        Optional<JSONObject> dealCode = dealCodeRefData.stream()
                                .filter(dc -> dealCodeId.equals(stringGetter.apply(dc, DEAL_CODE_ID))).findFirst();
                        LOGGER.debug("deal code ref data by {} is <{}>", DEAL_CODE_ID, dealCode);
                        if (dealCode.isPresent()) {
                            entry.setDealCodeType(stringGetter.apply(dealCode.get(), DEAL_CODE_TYPE));
                        }
                    }
                }

                LOGGER.debug("filtering credit pool ref data by {} <{}>", CREDIT_POOL_ID, cpcl.getId());
                Optional<JSONObject> creditPool = creditPoolRefData.stream()
                        .filter(cp -> cpcl.getId().toString().equals(stringGetter.apply(cp, CREDIT_POOL_ID)))
                        .findFirst();
                LOGGER.debug("credit pool ref data by {} is <{}>", CREDIT_POOL_ID, creditPool);

                fillUtilizationAmounts(entry, cpcl);

                if (creditPool.isPresent()) {
                    entry.setCreditMethod(stringGetter.apply(creditPool.get(), CREDIT_METHOD));
                    entry.setMnemonic(stringGetter.apply(creditPool.get(), MNEMONIC));
                    entry.setRefDataStatus(stringGetter.apply(creditPool.get(), REF_DATA_STATUS));
                    entry.setCpDailyWarningLimit(longGetter.apply(creditPool.get(), CP_DAILY_WARNING_LIMIT));
                    entry.setCpDailyLimit(longGetter.apply(creditPool.get(), CP_DAILY_LIMIT));
                    entry.setCpNopWarningLimit(longGetter.apply(creditPool.get(), CP_NOP_WARNING_LIMIT));
                    entry.setCpNopLimit(longGetter.apply(creditPool.get(), CP_NOP_LIMIT));
                }
                listView.add(entry);
            }
        });
        return listView;
    }

    private List<JSONObject> getData(String key) {
        LOGGER.debug("Getting data from redis, key {}", key);
        List<JSONObject> data = new ArrayList<>();
        findAll(key).stream().forEach(s -> {
            try {
                data.add(new JSONObject(s));
            } catch (JSONException e) {
                LOGGER.error("error while trying to map the key to json object", e);
            }
        });
        return data;
    }

    public String getBookedOrders(String id) {
        List<JSONObject> bookedOrders = getData(redisPath + ":" + bookPath + ":" + bookedOrdersPath + ":" + id);
        JSONArray array = new JSONArray();
        User user = authService.getLoggedUser();
        for (JSONObject bookedOrder : bookedOrders) {
            Integer brokerSessionMpidId = integerGetter.apply(bookedOrder, BROKER_SESSION_MPID_ID);
            Optional<RefdBrokerSessionMpid> bsm = Optional.empty();
            if (brokerSessionMpidId != null) {
                bsm = refdBrokerSessionMpidRepository.findById(brokerSessionMpidId);
            }
            boolean isAllowed = Role.TW.equals(user.getRole()) || (bsm.map(b -> user.getDealCode().getId().equals(b.getDealCodeId())))
                            .orElse(false);
            if (isAllowed) {
                bookedOrder.put(BROKER_SESSION_MPID, bsm.map(RefdBrokerSessionMpid::getMpid).orElse(null));
                array.put(bookedOrder);
            }
        }
        return bookedOrders.toString();
    }

}