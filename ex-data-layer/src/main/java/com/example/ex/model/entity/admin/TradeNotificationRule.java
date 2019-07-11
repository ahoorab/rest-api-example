package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.example.ex.model.type.NotificationType;
import com.example.ex.model.type.RoleType;
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
@Table(name = "trade_notification_rule", uniqueConstraints = { @UniqueConstraint(columnNames = { "handle" }),
        @UniqueConstraint(columnNames = { "role", "notification_type", "fix_session", "firm", "only_for_fixsessionmpid" }) })
@FilterDef(name="firmFilter", parameters=@ParamDef( name="firm", type="string" ) )
@Filter(name="firmFilter", condition="firm = :firm")
public class TradeNotificationRule extends BaseEntity {

    private static final long serialVersionUID = -1929045048939479305L;

    @NotNull
    @Column(length = 12, nullable = false, unique = true)
    @Size(max = 12)
    private String handle;

    @NotNull
    @Column(length = 64, nullable = false)
    @Size(max = 64)
    private String name;

    @NotNull
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType;

    @NotNull
    @Column(length = 12, nullable = false)
    @Size(max = 12)
    private String firm;

    @NotNull
    @Column(nullable = false)
    private RoleType role;

    @Column(length = 12, name = "only_for_fixsessionmpid")
    @Size(max = 12)
    private String onlyForFixSessionMpid;

    @NotNull
    @Column(length = 12, name = "fix_session", nullable = false)
    @Size(max = 12)
    private String fixSession;

    @NotNull
    @Column(nullable = false)
    private Status status;
}