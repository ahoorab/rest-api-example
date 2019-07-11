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
@Table(name = "apptype", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class AppType extends BaseEntity {

    private static final long serialVersionUID = -3997964151638815486L;

    @NotNull
    @Size(max = 4)
    @Column(length = 4, unique = true, nullable = false)
    private String name;

    @NotNull
    @Max(Byte.MAX_VALUE)
    @Column(name = "is_symbol_based", nullable = false)
    private Integer isSymbolBased;

    @NotNull
    @Max(Byte.MAX_VALUE)
    @Column(name = "is_session_based", nullable = false)
    private Integer isSessionBased;

    @NotNull
    @Max(Byte.MAX_VALUE)
    @Column(name = "need_refchannel_one", nullable = false)
    private Integer needRefChannelOne;

    @NotNull
    @Column(nullable = false)
    private Status status;
}