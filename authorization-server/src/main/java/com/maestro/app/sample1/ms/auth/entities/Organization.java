package com.maestro.app.sample1.ms.auth.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity
@Table(name = "ORGS")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Organization extends BaseGuidEntity {
    private static final long serialVersionUID = 1L;

    private String name;
}
