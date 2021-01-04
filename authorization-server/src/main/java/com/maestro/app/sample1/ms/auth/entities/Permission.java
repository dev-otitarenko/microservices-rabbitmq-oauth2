package com.maestro.app.sample1.ms.auth.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "PERMISSIONS")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseIdEntity {
    private static final long serialVersionUID = 1L;

    private String name;
}
