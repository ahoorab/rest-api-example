package com.example.ex.model.entity.admin;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
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
@Table(name = "ccypair_settledate",uniqueConstraints=@UniqueConstraint(columnNames={"ccypair","trade_date"}))
public class CurrencyPairSettleDate extends BaseEntity {

    private static final long serialVersionUID = -4616844610816479399L;

    @NotNull
    @Size(max = 6)
    @Column(length = 6, name="ccypair", nullable = false)
    private String currencyPair;

    @NotNull
    @Column(name="trade_date",nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST", lenient = OptBoolean.FALSE)
    private Date tradeDate;

    @Column(name="settle_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST", lenient = OptBoolean.FALSE)
    private Date settleDate;

    @NotNull
    @Column(name = "holiday_count", nullable = false)
    @Max(Byte.MAX_VALUE)
    private Integer holidayCount;
    
    @Size(max = 64)
    @Column(length = 64)
    private String notes;
    
    private Status status;
    
    @NotNull
    @Column(name = "update_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "EST", lenient = OptBoolean.FALSE)
    private Timestamp updateTime;
    
}