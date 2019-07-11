package com.example.ex.model.entity.ts;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.example.ex.model.converter.NanoEpochConverter;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
public abstract class FixMessage {
    
    @Id
    @ApiModelProperty(hidden = true)
    private Long sequence;
    
    @Column(name = "sequencetime")
    @Convert(converter = NanoEpochConverter.class)
    private String sequenceTime;
    
    @Column(name = "appseq")
    private Long appSeq;
    
    @Column(name = "channelid")
    private Long channelId;
    
    @Column(name = "symbolid")
    private Integer symbolId;
    
    @Column(name = "poolclass")
    private String poolClass;
    
    @Column(name = "apptype")
    private String appType;
    
    @Column(name = "appid")
    private Integer appId;
    
    @Column(name = "entrytime")
    @Convert(converter = NanoEpochConverter.class)
    private String entryTime;
    
    @Column(name = "orderside")
    private String orderSide;
    
    @Column(name = "brokersessionmpidid")
    private Integer brokerSessionMpidId;
    
    @Column(name = "venuesessionid")
    private Integer venueSessionId;
    
    @Column(name = "orderid")
    private Long orderId;
    
    @Column(name = "clordid")
    private String clordId;

    @Column(name = "origclordid")
    private String origClordId;
    
    @Column
    private String symbol;
    
    @Column(name = "orderstatus")
    private String orderStatus;
    
    @Column(name = "cancelrequestsource")
    private String cancelRequestSource;

    @Column(name = "refsequence")
    private Long refSequence;
 
}