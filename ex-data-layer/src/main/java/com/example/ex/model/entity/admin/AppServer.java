package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appserver", uniqueConstraints = { @UniqueConstraint(columnNames = { "handle" }),
        @UniqueConstraint(columnNames = { "ip_internal" }), @UniqueConstraint(columnNames = { "hostname" }) })
public class AppServer extends BaseEntity {

    private static final long serialVersionUID = -2503678331209744152L;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, unique = true, nullable = false)
    private String handle;

    @NotNull
    @Size(max = 16)
    @Column(length = 16, name = "ip_internal", nullable = false, unique = true)
    private String internalIp;

    @Size(max = 16)
    @Column(length = 16, name = "ip_external")
    private String externalIp;

    @NotNull
    @Size(max = 32)
    @Column(length = 32, nullable = false, unique = true)
    private String hostname;

    @Size(max = 32)
    @Column(length = 32)
    private String uri;

    @Size(max = 32)
    @Column(length = 32, name = "uri_cg")
    private String uriCg;

    @NotNull
    @Max(Byte.MAX_VALUE)
    @Column(name = "is_core", nullable = false)
    private Integer isCore;

    @NotNull
    @Max(Byte.MAX_VALUE)
    @Column(name = "is_edge", nullable = false)
    private Integer isEdge;

    @NotNull
    @Max(Byte.MAX_VALUE)
    @Column(name = "is_bo", nullable = false)
    private Integer isBo;

    @Size(max = 128)
    @Column(length = 128)
    private String notes;

    @NotNull
    @Max(Byte.MAX_VALUE)
    @Column(name = "is_active", nullable = false)
    private Integer isActive;
}