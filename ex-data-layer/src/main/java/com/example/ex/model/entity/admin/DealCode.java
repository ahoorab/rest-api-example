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

import com.example.ex.model.type.DcType;
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
@Table(name = "dealcode",uniqueConstraints={@UniqueConstraint(columnNames={"handle"}), @UniqueConstraint(columnNames={"mnemonic"})})
@FilterDef(name="firmFilterCP", parameters=@ParamDef( name="firm", type="string" ) )
@FilterDef(name="firmFilterTP", parameters=@ParamDef( name="firm", type="string" ) )
@Filter(name="firmFilterCP", condition="firm = :firm or pb_firm = :firm or subpb_firm = :firm")
@Filter(name="firmFilterTP", condition="firm = :firm")
public class DealCode extends BaseEntity {

    private static final long serialVersionUID = 4330085339952674517L;

    @NotNull
    @Size(max = 12)
    @Column(unique = true, length = 12, nullable = false)
    private String handle;

    @Size(max = 64)
    @Column(length = 64)
    private String name;

    @NotNull
    @Size(max = 16)
    @Column(unique = true, length = 16, nullable = false)
    private String mnemonic;

    @Size(max = 80)
    @Column(length = 80)
    private String description;

    @NotNull
    @Column(name = "dc_type", nullable = false)
    private DcType dcType;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, nullable = false)
    private String firm;

    @Size(max = 12)
    @Column(name = "pb_firm", length = 12)
    private String pbFirm;

    @Size(max = 12)
    @Column(name = "subpb_firm", length = 12)
    private String subPbFirm;

    @Size(max = 300)
    @Column(name = "ledger_cp_id", length = 300)
    private String ledgerCpId;

    @Size(max = 300)
    @Column(name = "ledger_tp_account_id", length = 300)
    private String ledgerTpAccountId;

    @NotNull
    @Column(nullable = false)
    private Status status;

}