package com.example.ex.model.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.example.ex.model.type.ConnectionType;
import com.example.ex.model.type.OrderCapacity;
import com.example.ex.model.type.Status;
import com.example.ex.model.type.Symbology;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fixsession", uniqueConstraints = @UniqueConstraint(columnNames = { "handle" }))
@FilterDef(name = "dealCodeFilter", parameters = @ParamDef(name = "dealcode", type = "string"))
@Filter(name = "dealCodeFilter", condition = "mpid in (select m.handle from mpid m where m.dealcode = :dealcode)")
public class FixSession extends BaseEntity {

    private static final long serialVersionUID = -616388955956021722L;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, nullable = false, unique = true)
    private String handle;

    @Size(max = 64)
    @Column(length = 64)
    private String name;

    @NotNull
    @Size(max = 6)
    @Column(length = 6, name = "app_instance", nullable = false)
    private String appInstance;

    @NotNull
    @Size(max = 12)
    @Column(length = 12, name = "mpid", nullable = false)
    private String mpid;

    @NotNull
    @Size(max = 16)
    @Column(length = 16, name = "sender_comp_id", nullable = false)
    private String senderCompId;

    @NotNull
    @Size(max = 16)
    @Column(length = 16, name = "target_comp_id", nullable = false)
    private String targetCompId;

    @NotNull
    @Size(max = 15)
    @Column(length = 15, name = "local_ip", nullable = false)
    private String localIp;

    @NotNull
    @Size(max = 15)
    @Column(length = 15, name = "remote_ip", nullable = false)
    private String remoteIp;

    @NotNull
    @Column(nullable = false)
    @Digits(integer = 4, fraction = 0)
    private Integer port;

    @NotNull
    @Column(name = "connection_type", nullable = false)
    private ConnectionType connectionType;

    @NotNull
    @Size(max = 32)
    @Column(length = 32, name = "dc_msg_types", nullable = false)
    private String dcMsgTypes;

    @NotNull
    @Column(nullable = false)
    private Status status;

    @NotNull
    @Column(name = "cancel_on_disconnect", nullable = false)
    @Min(0)
    @Max(1)
    private Integer cancelOnDisconnect;

    @NotNull
    @Column(name = "supports_busts", nullable = false)
    @Min(0)
    @Max(1)
    private Integer supportsBusts;

    @NotNull
    @Column(name = "allow_mkt_orders", nullable = false)
    @Min(0)
    @Max(1)
    private Integer allowMktOrders;

    @NotNull
    @Column(name = "max_orderqty", nullable = false)
    @Max(Integer.MAX_VALUE)
    private Integer maxOrderQty;

    @NotNull
    @Column(name = "max_notional_orderqty", nullable = false)
    @Max(Long.MAX_VALUE)
    private Long maxNotionalOrderQty;

    @NotNull
    @Column(name = "heartbeat_interval", nullable = false)
    @Max(Short.MAX_VALUE)
    private Integer heartbeatInterval;

    @NotNull
    @Size(max = 8)
    @Column(length = 8, name = "begin_string", nullable = false)
    private String beginString;

    @NotNull
    @Column(name = "default_order_capacity", nullable = false)
    private OrderCapacity defaultOrderCapacity;

    @NotNull
    @Column(name = "only_allow_test_symbols", nullable = false)
    @Min(0)
    @Max(1)
    private Integer onlyAllowTestSymbols;

    @NotNull
    @Column(nullable = false)
    private Symbology symbology;

    @Size(max = 32)
    @Column(length = 32)
    private String password;

}