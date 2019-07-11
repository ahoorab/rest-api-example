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

import com.example.ex.model.type.CreditLineType;
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
@Table(name = "creditline", uniqueConstraints = { @UniqueConstraint(columnNames = { "handle" }), @UniqueConstraint(columnNames = { "grantor_firm", "grantee_firm" }) })
@FilterDef(name="firmFilter", parameters=@ParamDef( name="firm", type="string" ) )
@Filter(name="firmFilter", condition="grantor_firm = :firm")
public class CreditLine extends BaseEntity {

    private static final long serialVersionUID = 5634232422838075956L;

    @NotNull
    @Column(length = 12, nullable = false, unique = true)
    @Size(max = 12)
    private String handle;

    @Column(length = 64)
    @Size(max = 64)
    private String name;

    @NotNull
    @Column(name = "creditline_type", nullable = false)
    private CreditLineType type;

    @NotNull
    @Column(length = 12, name = "grantor_firm", nullable = false)
    @Size(max = 12)
    private String grantorFirm;

    @NotNull
    @Column(length = 12, name = "grantee_firm", nullable = false)
    @Size(max = 12)
    private String granteeFirm;

    @NotNull
    @Column(length = 12, name = "creditpool", nullable = false)
    @Size(max = 12)
    private String creditPool;

    private Status status;
}