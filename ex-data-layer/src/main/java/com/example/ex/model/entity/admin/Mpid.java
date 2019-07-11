package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.example.ex.model.type.BrokerType;
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
@Table(name = "mpid",uniqueConstraints=@UniqueConstraint(columnNames={"handle"}))
@FilterDef(name="dealCodeFilter", parameters=@ParamDef( name="dealcode", type="string" ) )
@Filter(name="dealCodeFilter", condition="dealcode = :dealcode")
public class Mpid extends BaseEntity {

    private static final long serialVersionUID = -4616844610816479399L;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, nullable = false, unique = true)
    private String handle;

    @NotNull
    @Size(max = 64)
    @Column(length = 64, nullable = false)
    private String name;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, nullable = false)
    private String mpid;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, name="dealcode", nullable = false)
    private String dealCode;

    @Size(max = 12)
    @Column(length = 12, name="parent_mpid")
    private String parentMpid;

    @NotNull
    @Column(name = "broker_priority_type", nullable = false)
    private BrokerType brokerPriorityType;

    @NotNull
    @Column(name = "self_trade_group_type", nullable = false)
    private BrokerType selfTradeType;
    
    @NotNull
    @Column(name = "is_finra_member", nullable = false)
    @Min(0) @Max(1)
    private Integer isFinraMember;

    @NotNull
    @Column(precision = 19, nullable = false)
    @Max(Long.MAX_VALUE)
    private Long crd;
    
    @NotNull
    @Size(max = 2)
    @Column(length = 2, nullable = false)
    private String country;
    
    @NotNull
    @Column(nullable = false)
    private Status status;
}