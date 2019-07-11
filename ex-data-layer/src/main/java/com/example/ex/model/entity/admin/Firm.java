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

import com.example.ex.model.type.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="firm",uniqueConstraints={@UniqueConstraint(columnNames={"handle"}), @UniqueConstraint(columnNames={"mnemonic"})})
@FilterDef(name="firmFilter", parameters=@ParamDef( name="firm", type="string" ) )
@Filter(name="firmFilter", condition="handle = :firm")
public class Firm extends BaseEntity {

    private static final long serialVersionUID = -7388601124870214988L;

    @NotNull
    @Column(length=12,nullable=false, unique = true)
    @Size(max=12)
    private String handle;

    @NotNull
    @Column(length=16,nullable=false, unique = true)
    @Size(max=16)
    private String mnemonic;
    
    @NotNull
    @Column(length=64,nullable=false)
    @Size(max=64)
    private String name;
    
    @NotNull
    @Column(name = "firm_type", nullable=false)
    @Max(Byte.MAX_VALUE)
    private Integer firmType;
    
    @Column(name = "is_hub")
    @Min(0) @Max(1)
    private Integer isHub;
    
    @Column(name = "is_principal")
    @Min(0) @Max(1)
    private Integer isPrincipal;
    
    @Column(name = "is_pb")
    @Min(0) @Max(1)
    private Integer isPb;
    
    @Column(name = "is_subpb")
    @Min(0) @Max(1)
    private Integer isSubpb;
    
    @Column(name = "is_pb_client")
    @Min(0) @Max(1)
    private Integer isPbClient;
    
    @Column(name = "is_subpb_client")
    @Min(0) @Max(1)
    private Integer isSubpbClient;
    
    private Status status;
}