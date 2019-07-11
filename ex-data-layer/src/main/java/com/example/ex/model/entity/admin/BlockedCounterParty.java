package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.example.ex.model.type.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blocked_counterparty", uniqueConstraints = { @UniqueConstraint(columnNames = { "handle" }), @UniqueConstraint(columnNames = { "mnemonic" }) })
@FilterDef(name="dealCodeFilter", parameters=@ParamDef( name="dealcode", type="string" ) )
@Filter(name="dealCodeFilter", condition="dealcode1 = :dealcode or dealCode2 = :dealcode")
public class BlockedCounterParty extends BaseEntity {

    private static final long serialVersionUID = -1803125938028188661L;

    @NotNull
    @Column(length = 12, nullable = false, unique = true)
    @Size(max = 12)
    private String handle;

    @NotNull
    @Column(length = 16, nullable = false, unique = true)
    @Size(max = 16)
    private String mnemonic;

    @Column(length = 12, name = "dealcode1")
    @Size(max = 12)
    private String dealCode1;

    @Column(length = 12, name = "pb_firm1")
    @Size(max = 12)
    private String pbFirm1;
    
    @Column(length = 12, name = "trading_firm1")
    @Size(max = 12)
    private String tradingFirm1;

    @Column(length = 12, name = "dealcode2")
    @Size(max = 12)
    private String dealCode2;

    @Column(length = 12, name = "pb_firm2")
    @Size(max = 12)
    private String pbFirm2;
    
    @Column(length = 12, name = "trading_firm2")
    @Size(max = 12)
    private String tradingFirm2;
     
    @Column(length = 64)
    @Size(max = 64)
    private String notes;
    
    @NotNull
    @Column(nullable = false)
    private Status status;
}