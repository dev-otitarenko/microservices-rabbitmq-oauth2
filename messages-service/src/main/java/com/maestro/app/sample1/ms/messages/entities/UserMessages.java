package com.maestro.app.sample1.ms.messages.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maestro.app.utils.CommonUtils;
import lombok.Data;
import org.springframework.data.domain.Persistable;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "TBLL_USER_MESSAGES")
public class UserMessages implements Persistable {
    @Id
    @Column(name = "code", updatable = false)
    private String code;
    @Column(name = "oper_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRec;
    @JsonIgnore
    @Column(nullable = false)
    private String idUser;
    @Column(nullable = false)
    private String title;
    private String message;
    @Column(name = "id_download")
    private String idDownload;
    @Column(name = "fname")
    private String fileName;
    @Column(nullable = false)
    private Integer state;
    @Column(nullable = false)
    private Double executionTime;

    @PrePersist
    public void prePersist() {
        if (StringUtils.isEmpty(this.code)) this.code = CommonUtils.generateGuid();
        if (StringUtils.isEmpty(this.title)) this.title = "Where is my title ??";
        if (StringUtils.isEmpty(this.idUser)) this.title = "N/A";
        if (state == null) this.state = 0;
        if (executionTime == null) this.executionTime = (double)0;

        this.dateRec = new Date();
    }

    @JsonIgnore
    @Override
    public Serializable getId() {
        return code;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return true;
    }
}
