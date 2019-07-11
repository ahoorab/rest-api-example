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
@Table(name = "currencypair",uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class CurrencyPair extends BaseEntity {

    private static final long serialVersionUID = 6419911774167519284L;

    @NotNull
    @Size(max = 6)
    @Column(length = 6, nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(max = 3)
    @Column(length = 3, nullable = false)
    private String ccy1;

    @NotNull
    @Size(max = 3)
    @Column(length = 3, nullable = false)
    private String ccy2;

    @NotNull
    @Column(name = "is_tradeable", nullable = false)
    @Convert(converter = EnumBooleanConverter.class)
    private Boolean isTradeable;

    @NotNull
    @Column(name = "days_to_spot", nullable = false)
    @Max(Byte.MAX_VALUE)
    private Integer daysToSpot;

    @NotNull
    @Column(name = "rate_precision", nullable = false)
    @Max(Byte.MAX_VALUE)
    private Integer ratePrecision;

    @NotNull
    @Column(name = "is_decimalized", nullable = false)
    @Convert(converter = EnumBooleanConverter.class)
    private Boolean isDecimalized;

    @NotNull
    @Column(name = "is_cls", nullable = false)
    @Convert(converter = EnumBooleanConverter.class)
    private Boolean isCls;

    @NotNull
    @Column(nullable = false)
    private Status status;

    @NotNull
    @Column(name = "is_enable_collars", nullable = false)
    @Convert(converter = EnumBooleanConverter.class)
    private Boolean isEnableCollars;

    @NotNull
    @Column(name = "prev_close", nullable = false)
    private Double prevClose;

    @NotNull
    @Column(name = "avg_spread", nullable = false)
    private Double avgSpread;

    @NotNull
    @Column(name = "avg_order_size", nullable = false)
    @Max(Integer.MAX_VALUE)
    private Integer avgOrderSize;

    @NotNull
    @Column(precision = 3, name = "avg_trade_size", nullable = false)
    @Max(Integer.MAX_VALUE)
    private Integer avgTradeSize;

}