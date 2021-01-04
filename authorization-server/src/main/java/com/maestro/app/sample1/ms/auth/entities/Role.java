package com.maestro.app.sample1.ms.auth.entities;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "ROLES")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseIdEntity {
	private static final long serialVersionUID = 1L;

	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "permission_roles", joinColumns = {
			@JoinColumn(name = "id_role", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "id_permission", referencedColumnName = "id") })
	private List<Permission> permissions;
}
