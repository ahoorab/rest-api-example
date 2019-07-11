package com.example.ex.model.entity.ts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fixmessage_ordercxlrejecttobroker")
public class FixMessageOrderCxlRejectToBroker extends FixMessage {
       
    @Column(name = "rejectreason")
    private String rejectReason;
    
    @Column(name = "cancelrejectresponseto")
    private String cancelRejectResponseTo;
    
}