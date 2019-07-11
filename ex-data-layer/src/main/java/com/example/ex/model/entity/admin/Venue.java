package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.ex.model.converter.EnumBooleanConverter;
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
@Table(name = "venue", uniqueConstraints = { @UniqueConstraint(columnNames = { "handle" }), @UniqueConstraint(columnNames = { "mnemonic" }) })
public class Venue extends BaseEntity {

    private static final long serialVersionUID = -8431238865406241304L;

    @NotNull
    @Column(length = 12, nullable = false, unique = true)
    @Size(max = 12)
    private String handle;

    @NotNull
    @Column(length = 16, nullable = false, unique = true)
    @Size(max = 16)
    private String mnemonic;

    @NotNull
    @Column(length = 64, nullable = false)
    @Size(max = 64)
    private String name;

    @NotNull
    @Column(length = 4, nullable = false)
    @Size(max = 4)
    private String country;

    @NotNull
    @Column(length = 16, name = "market_session", nullable = false)
    @Size(max = 16)
    private String marketSession;

    @NotNull
    @Column(length = 4, name = "tape_id", nullable = false)
    @Size(max = 4)
    private String tapeId;

    @NotNull
    @Column(length = 4, name = "oats_id", nullable = false)
    @Size(max = 4)
    private String oatsId;

    @NotNull
    @Column(name = "venue_post_ratio", nullable = false)
    private Float venuePostRatio;

    @NotNull
    @Column(name = "is_ats", nullable = false)
    @Convert(converter = EnumBooleanConverter.class)
    private Boolean isAts;

    @NotNull
    @Column(name = "max_shares_per_order", nullable = false)
    @Max(Integer.MAX_VALUE)
    private Integer maxSharesPerOrder;
    
    @NotNull
    @Column(name = "max_notional_per_order", nullable = false)
    @Max(Integer.MAX_VALUE)
    private Integer maxNotionalPerOrder;

    @NotNull
    @Column(nullable = false)
    @Max(Short.MAX_VALUE)
    private Integer rank;

    @NotNull
    @Column(name= "cost_to_take", nullable = false)
    @Max(Short.MAX_VALUE)
    private Integer costToTake;
    
    @NotNull
    @Column(nullable = false)
    private Status status;
}