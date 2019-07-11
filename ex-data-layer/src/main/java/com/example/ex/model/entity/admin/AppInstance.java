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
@Table(name = "appinstance", uniqueConstraints = @UniqueConstraint(columnNames = { "appid" }))
public class AppInstance extends BaseEntity {

    private static final long serialVersionUID = 253405261001622639L;

    @NotNull
    @Size(max = 6)
    @Column(length = 6, name = "appid", unique = true, nullable = false)
    private String appId;

    @NotNull
    @Size(max = 4)
    @Column(length = 4, name = "apptype", nullable = false)
    private String appType;
    
    @NotNull
    @Max(Byte.MAX_VALUE)
    @Column(name = "threadno", nullable = false)
    private Integer threadNumber;
    
    @Size(max = 12)
    @Column(length = 12, name = "jvm1")
    private String firstJvm;
    
    @Size(max = 12)
    @Column(length = 12, name = "jvm2")
    private String secondJvm;
    
    @NotNull
    @Max(Short.MAX_VALUE)
    @Column(name = "seq1", nullable = false)
    private Integer firstSequence;
    
    @Max(Short.MAX_VALUE)
    @Column(name = "seq2")
    private Integer secondSequence;
    
    private Status status;

}