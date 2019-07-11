package com.example.ex.model.entity.cache;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreditMatrixEntry {
    
    private String grantorFirm;
    private String granteeFirm;
    private String dealCodeType;
    private String creditMethod;
    private List<String> settleDates = new ArrayList<>(4);
    private List<Long> utilizationAmounts = new ArrayList<>(4);
    private Integer creditPoolId;
    private String mnemonic;
    private String refDataStatus;
    private Long cpDailyWarningLimit;
    private Long cpDailyLimit;
    private Long cpNopWarningLimit;
    private Long cpNopLimit;

}
