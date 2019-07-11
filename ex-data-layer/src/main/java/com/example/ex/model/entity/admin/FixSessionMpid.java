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

import com.example.ex.model.type.InstructionType;
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
@Table(name = "fixsessionmpid",uniqueConstraints=@UniqueConstraint(columnNames={"handle"}))
@FilterDef(name = "dealCodeFilter", parameters = @ParamDef(name = "dealcode", type = "string"))
@Filter(name = "dealCodeFilter", condition = "mpid in (select m.handle from mpid m where m.dealcode = :dealcode)")
public class FixSessionMpid extends BaseEntity {

    private static final long serialVersionUID = -9106371304747347906L;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, nullable = false, unique = true)
    private String handle;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, name = "brokersession", nullable = false)
    private String brokerSession;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, name="mpid", nullable = false)
    private String mpid;
    
    @NotNull
    @Size(max = 2)
    @Column(length = 2, name="agency_default_aiq_group", nullable = false)
    private String agencyAiqGroup;

    @NotNull
    @Size(max = 2)
    @Column(length = 2, name="principal_default_aiq_group", nullable = false)
    private String principalAiqGroup;

    @NotNull
    @Column(name="minqty_instruction", nullable = false)
    private InstructionType minqtyInstruction;
    
    @NotNull
    @Column(nullable = false)
    private Status status;

}