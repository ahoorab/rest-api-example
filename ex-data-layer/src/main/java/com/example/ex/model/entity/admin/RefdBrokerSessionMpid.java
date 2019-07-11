package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.ex.model.type.BrokerType;
import com.example.ex.model.type.InstructionType;
import com.example.ex.model.type.Status;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "refd_broker_session_mpid")
public class RefdBrokerSessionMpid {
    
    private FixSessionMpid fixSessionMpid;
    private FixSession fixSession;
    private Mpid mpid;
    private DealCode dealCode;
    private Firm firm;
    private Integer dropCopySessionId;
    private String nsccNumber;
    private Integer aggregateNotionalRiskGroupId;
    private String allowRouteDirectedOrders;
    private Integer aggregateRoutedNotionalRiskGroupId;
    private String allowRoutableOrders;
    
    public RefdBrokerSessionMpid() {
        this.fixSessionMpid = new FixSessionMpid();
        this.fixSession = new FixSession();
        this.mpid = new Mpid();
        this.dealCode = new DealCode();
        this.firm = new Firm();
    }

    @Column(name="RefDataStatus")
    public Status getRefDataStatus() {
        return fixSessionMpid.getStatus();
    }
    public void setRefDataStatus(Status refDataStatus) {
        fixSessionMpid.setStatus(refDataStatus);
    }
    @Column(name="BrokerSessionMPIDID")
    @Id
    @ApiModelProperty(hidden = true)
    public Integer getBrokerSessionMpidId() {
        return fixSessionMpid.getId();
    }
    public void setBrokerSessionMpidId(Integer brokerSessionMpidId) {
        this.fixSessionMpid.setId(brokerSessionMpidId);
    }
    @Column(name="BrokerSessionID")
    public Integer getBrokerSessionId() {
        return fixSession.getId();
    }
    public void setBrokerSessionId(Integer brokerSessionId) {
        this.fixSession.setId(brokerSessionId);
    }
    @Column(name="MPID")
    public String getMpid() {
        return mpid.getMpid();
    }
    public void setMpid(String mpid) {
        this.mpid.setMpid(mpid);
    }
    @Column(name="MPIDID")
    public Integer getMpidId() {
        return mpid.getId();
    }
    public void setMpidId(Integer mpidId) {
        this.mpid.setId(mpidId);
    }
    @Column(name="BrokerID")
    public Integer getBrokerId() {
        return mpid.getId();
    }
    public void setBrokerId(Integer brokerId) {
        this.mpid.setId(brokerId);
    }
    @Column(name="DealCodeId")
    public Integer getDealCodeId() {
        return dealCode.getId();
    }
    public void setDealCodeId(Integer dealCodeId) {
        this.dealCode.setId(dealCodeId);
    }
    @Column(name="CompanyID")
    public Integer getCompanyId() {
        return firm.getId();
    }
    public void setCompanyId(Integer companyId) {
        this.firm.setId(companyId);
    }
    @Column(name="BrokerPriorityType")
    public BrokerType getBrokerPriorityType() {
        return mpid.getBrokerPriorityType();
    }
    public void setBrokerPriorityType(BrokerType brokerPriorityType) {
        this.mpid.setBrokerPriorityType(brokerPriorityType);
    }
    @Column(name="DropCopySessionID")
    public Integer getDropCopySessionId() {
        return this.dropCopySessionId;
    }
    public void setDropCopySessionId(Integer dropCopySessionId) {
        this.dropCopySessionId = dropCopySessionId;
    }
    @Column(name="NSCCNumber")
    public String getNsccNumber() {
        return nsccNumber;
    }
    public void setNsccNumber(String nsccNumber) {
        this.nsccNumber = nsccNumber;
    }
    @Column(name="SelfTradeGroupType")
    public BrokerType getSelfTradeGroupType() {
        return mpid.getSelfTradeType();
    }
    public void setSelfTradeGroupType(BrokerType selfTradeGroupType) {
        this.mpid.setSelfTradeType(selfTradeGroupType);
    }
    @Column(name="AgencyDefaultAIQGroup")
    public String getAgencyDefaultAiqGroup() {
        return this.fixSessionMpid.getAgencyAiqGroup();
    }
    public void setAgencyDefaultAiqGroup(String agencyDefaultAiqGroup) {
        this.fixSessionMpid.setAgencyAiqGroup(agencyDefaultAiqGroup);
    }
    @Column(name="PrincipalDefaultAIQGroup")
    public String getPrincipalDefaultAiqGroup() {
        return this.fixSessionMpid.getPrincipalAiqGroup();
    }
    public void setPrincipalDefaultAiqGroup(String principalDefaultAiqGroup) {
        this.fixSessionMpid.setPrincipalAiqGroup(principalDefaultAiqGroup);
    }
    @Column(name="AggregateNotionalRiskGroupID")
    public Integer getAggregateNotionalRiskGroupId() {
        return aggregateNotionalRiskGroupId;
    }
    public void setAggregateNotionalRiskGroupId(Integer aggregateNotionalRiskGroupId) {
        this.aggregateNotionalRiskGroupId = aggregateNotionalRiskGroupId;
    }
    @Column(name="MinQtyInstruction")
    public InstructionType getMinqtyInstruction() {
        return fixSessionMpid.getMinqtyInstruction();
    }
    public void setMinqtyInstruction(InstructionType minqtyInstruction) {
        this.fixSessionMpid.setMinqtyInstruction(minqtyInstruction);
    }
    @Column(name="AllowRouteDirectedOrders")
    public String getAllowRouteDirectedOrders() {
        return allowRouteDirectedOrders;
    }
    public void setAllowRouteDirectedOrders(String allowRouteDirectedOrders) {
        this.allowRouteDirectedOrders = allowRouteDirectedOrders;
    }
    @Column(name="AggregateRoutedNotionalRiskGroupID")
    public Integer getAggregateRoutedNotionalRiskGroupId() {
        return aggregateRoutedNotionalRiskGroupId;
    }
    public void setAggregateRoutedNotionalRiskGroupId(Integer aggregateRoutedNotionalRiskGroupId) {
        this.aggregateRoutedNotionalRiskGroupId = aggregateRoutedNotionalRiskGroupId;
    }
    @Column(name="AllowRoutableOrders")
    public String getAllowRoutableOrders() {
        return allowRoutableOrders;
    }
    public void setAllowRoutableOrders(String allowRoutableOrders) {
        this.allowRoutableOrders = allowRoutableOrders;
    }
    
}
