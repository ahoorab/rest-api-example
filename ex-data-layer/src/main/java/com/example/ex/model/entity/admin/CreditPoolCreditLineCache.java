package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.example.ex.model.type.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditPoolCreditLineCache extends BaseEntity {

    private static final long serialVersionUID = 1891863539376753510L;

    @Column(name = "grantor_firm")
    private String grantorFirm;

    @Column(name = "grantee_firm")
    private String granteeFirm;
    
    @Column(name = "limit_nop")
    private String limitNop;
    
    @Column(name = "limit_daily")
    private String limitDaily;
    
    @Column(name = "warning_pct")
    private String warningPct;
    
    private Status status;

}
