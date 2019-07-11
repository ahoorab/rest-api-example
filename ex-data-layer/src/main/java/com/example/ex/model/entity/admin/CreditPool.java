package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.example.ex.model.type.CreditMethod;
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
@Table(name = "creditpool", uniqueConstraints = { @UniqueConstraint(columnNames = { "handle" }), @UniqueConstraint(columnNames = { "mnemonic" }) })
@FilterDef(name="firmFilter", parameters=@ParamDef( name="firm", type="string" ) )
@Filter(name="firmFilter", condition="grantor_firm = :firm")
public class CreditPool extends BaseEntity {

    private static final long serialVersionUID = -1803125938028188661L;

    @NotNull
    @Column(length = 12, nullable = false, unique = true)
    @Size(max = 12)
    private String handle;

    @NotNull
    @Column(length = 16, nullable = false, unique = true)
    @Size(max = 16)
    private String mnemonic;

    @NotNull
    @Column(length = 12, name = "grantor_firm", nullable = false)
    @Size(max = 12)
    private String grantorFirm;

    @Column(length = 64)
    @Size(max = 64)
    private String name;
    
    @NotNull
    @Column(name = "credit_method", nullable = false)
    private CreditMethod creditMethod;
    
    @NotNull
    @Column(name = "limit_nop", nullable = false)
    @Max(Integer.MAX_VALUE)
    private Integer limitNop;
    
    @NotNull
    @Column(name = "limit_daily", nullable = false)
    @Max(Integer.MAX_VALUE)
    private Integer limitDaily;

    @NotNull
    @Column(name = "warning_pct", nullable = false)
    @Max(Byte.MAX_VALUE)
    private Integer warningPct;
    
    @NotNull
    @Column(nullable = false)
    private Status status;
}