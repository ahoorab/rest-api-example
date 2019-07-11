package com.example.ex.model.entity.admin;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.example.ex.model.converter.TimeDeserializer;
import com.example.ex.model.type.SystemStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "system_state")
public class SystemState extends BaseEntity {

    private static final long serialVersionUID = 6419911774167519284L;

    @NotNull
    @Size(max = 3)
    @Column(length = 3, name = "base_ccy", nullable = false)
    private String baseCcy;

    @Column(name="trade_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST", lenient = OptBoolean.FALSE)
    private Date tradeDate;

    @Column(name="prev_trade_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST", lenient = OptBoolean.FALSE)
    private Date prevTradeDate;

    @Column(name="system_state")
    private SystemStatus systemState;

    @NotNull
    @Column(name = "market_open_time", nullable = false)
    @JsonDeserialize(using=TimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh-mm-ss", timezone = "EST", lenient = OptBoolean.FALSE)
    private Time marketOpenTime;

    @NotNull
    @Column(name = "market_close_time", nullable = false)
    @JsonDeserialize(using=TimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss", timezone = "EST", lenient = OptBoolean.FALSE)
    private Time marketCloseTime;
}