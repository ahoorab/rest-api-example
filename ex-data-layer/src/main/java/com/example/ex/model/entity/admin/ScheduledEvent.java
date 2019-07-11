package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.ex.model.type.DataMasterType;
import com.example.ex.model.type.FieldNameType;
import com.example.ex.model.type.FieldValueType;
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
@Table(name = "scheduled_event",uniqueConstraints=@UniqueConstraint(columnNames={"handle"}))
public class ScheduledEvent extends BaseEntity {

    private static final long serialVersionUID = -4616844610816479399L;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, nullable = false, unique = true)
    private String handle;

    @Size(max = 64)
    @Column(length = 64)
    private String name;

    @NotNull
    @Size(max = 8)
    @Column(length = 8, name = "start_time", nullable = false)
    private String startTime;

    @NotNull
    @Column(name = "refdata_master", nullable = false)
    private DataMasterType refDataMaster;

    @NotNull
    @Column(name = "refchannel_id", nullable = false)
    @Max(Short.MAX_VALUE)
    private Integer refChannelId;

    @NotNull
    @Column(name = "field_name", nullable = false)
    private FieldNameType fieldName;

    @NotNull
    @Column(name = "field_value", nullable = false)
    private FieldValueType fieldValue;

    @Size(max = 256)
    @Column(length = 256, name="field_addlinfo")
    private String fieldAddlInfo;

    @NotNull
    @Column(nullable = false)
    private Status status;
}