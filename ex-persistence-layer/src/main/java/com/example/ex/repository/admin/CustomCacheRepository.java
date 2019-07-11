package com.example.ex.repository.admin;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ex.model.entity.admin.CreditPoolCreditLineCache;

public interface CustomCacheRepository extends JpaRepository<CreditPoolCreditLineCache, Integer> {

    // this will be removed once we have data available in redis
    @Query(value = "select cl.grantor_firm, "
            + "cl.grantee_firm, "
            + "cp.id, "
            + "cp.limit_nop, "
            + "cp.limit_daily, "
            + "cp.warning_pct, "
            + "cp.`status` "
       + "from creditpool cp, "
            + "creditline cl "
      + "where cl.creditpool = cp.handle", nativeQuery = true)
    Set<CreditPoolCreditLineCache> findCreditPoolCreditLineData();

}
