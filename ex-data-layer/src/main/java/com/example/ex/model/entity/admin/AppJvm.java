package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Table(name = "appjvm", uniqueConstraints = { @UniqueConstraint(columnNames = { "handle" }), @UniqueConstraint(columnNames = { "launch_name" })})
public class AppJvm extends BaseEntity {

    private static final long serialVersionUID = 4150810133357344744L;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, nullable = false, unique = true)
    private String handle;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, nullable = false)
    private String server;

    @NotNull
    @Size(max = 8)
    @Column(length = 8, name = "launch_name", nullable = false, unique = true)
    private String launchName;
    
    @NotNull
    @Max(Short.MAX_VALUE)
    @Column(name = "launch_seq", nullable = false)
    private Integer launchSeq;
    
    @NotNull
    @Size(max = 8)
    @Column(length = 8, nullable = false)
    private String xmem;
    
    @Size(max = 8)
    @Column(length = 8)
    private String jvmthreads;
    
    @Size(max = 128)
    @Column(length = 128)
    private String notes;
    
    @NotNull
    @Size(max = 128)
    @Column(length = 128, name = "logwait_string", nullable = false)
    private String logWait;
    
    private Status status;

}