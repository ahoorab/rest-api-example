package com.example.ex.model.entity.ts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fixmessage_statustobroker")
public class FixMessageStatusToBroker extends FixMessage {

    @Column(name = "ordershares")
    private Integer orderShares;
    
    @Column(name = "orderlimit")
    private Long orderLimit;
    
    @Column
    private String tif;
    
    @Column(name = "ordertype")
    private String orderType;
    
    @Column(name = "minqty")
    private Integer minqTy;
    
    @Column(name = "maxfloor")
    private Integer maxFloor;
    
    @Column(name = "orderroute")
    private String orderRoute;
    
    @Column(name = "ordercapacity")
    private String orderCapacity;
    
    @Column(name = "expiretime")
    private Long expireTime;
    
    @Column(name = "execid")
    private String execId;
    
    @Column(name = "filledprice")
    private Long filledPrice;
    
    @Column(name = "filledshares")
    private Integer filledShares;
    
    @Column
    private Integer leaves;
    
    @Column(name = "exectranstype")
    private String execTransType;
    
    @Column(name = "exectype")
    private String execType;
    
    @Column(name = "statusreason")
    private String statusReason;
    
    @Column(name = "ordercollarlimit")
    private Long orderCollarLimit;
    
}