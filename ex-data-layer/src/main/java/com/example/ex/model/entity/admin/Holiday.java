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
@Table(name = "holiday",uniqueConstraints=@UniqueConstraint(columnNames={"ccy","holiday"}))
public class Holiday extends BaseEntity {

    private static final long serialVersionUID = -5544915673812541802L;

    @NotNull
    @Size(max = 3)
    @Column(name = "ccy", length = 3, nullable = false)
    private String currency;

    @Size(max = 50)
    @Column(length = 50)
    private String description;

    @NotNull
    @Column(name = "holiday", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST", lenient = OptBoolean.FALSE)
    private Date date;

    @NotNull
    @Column(nullable = false)
    private Status status;

    @NotNull
    @Max(Integer.MAX_VALUE)
    @Column(nullable = false)
    private Integer version;

    @NotNull
    @Column(name = "update_time", nullable = false)
//    @JsonDeserialize(using=TimestampDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "EST", lenient = OptBoolean.FALSE)
    private Timestamp updateTime;

    @Size(max = 32)
    @Column(name = "update_user", length = 32)
    private String updateUser;
    
}