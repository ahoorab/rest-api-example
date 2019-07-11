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
@Table(name = "currency", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Currency extends BaseEntity {

    private static final long serialVersionUID = 5634207705672517137L;

    @NotNull
    @Size(max = 3)
    @Column(length = 3, nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "amount_dp", nullable = false)
    @Max(Byte.MAX_VALUE)
    private Integer amountDp;

    @NotNull
    @Column(name = "is_cls", nullable = false)
    @Convert(converter = EnumBooleanConverter.class)
    private Boolean cls;

    @NotNull
    @Column(name = "min_quantity", nullable = false)
    @Max(Integer.MAX_VALUE)
    private Integer minQuantity;

    @NotNull
    @Column(name = "min_increment", nullable = false)
    @Max(Integer.MAX_VALUE)
    private Integer minIncrement;

    @NotNull
    @Column(name = "lot_size", nullable = false)
    @Max(Integer.MAX_VALUE)
    private Integer lotSize;

    @NotNull
    @Column(nullable = false)
    private Status status;

}