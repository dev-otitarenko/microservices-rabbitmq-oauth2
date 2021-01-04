package com.maestro.app.sample1.ms.events.entities;

import lombok.Data;
import org.hibernate.annotations.Formula;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "LOG_PRIVATE_EVENTS")
public class LogPrivateEvents implements Persistable {
    @Id
    @Column(name = "id", updatable = false)
    private String code;
    @Column(name = "oper_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRec;
    @Column(nullable = false)
    private Integer mode;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String iduser;
    @Column(name = "username", nullable = false)
    private String username;
    
    @PrePersist
    public void prePersist() {
        dateRec = new Date();
    }
    
    @Override
    public Serializable getId() {
        return code;
    }
    
    @Override
    public boolean isNew() {
        return true;
    }
}
