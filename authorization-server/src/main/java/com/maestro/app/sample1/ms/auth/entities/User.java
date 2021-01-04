package com.maestro.app.sample1.ms.auth.entities;

import java.util.*;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseGuidEntity implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Column(name = "id_org")
	private String idOrg;
	@Column(name = "full_name")
	private String fullName;
	private String email;
	private String username;
	@Column(name = "psw")
	private String password;
	private boolean enabled;
	@Column(name = "account_locked")
	private boolean accountNonLocked;
	@Column(name = "account_expired")
	private boolean accountNonExpired;
	@Column(name = "credentials_expired")
	private boolean credentialsNonExpired;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogon;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_org", insertable = false, updatable = false)
	private Organization organization;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = {
			@JoinColumn(name = "id_user", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_role", referencedColumnName = "id") })
	private List<Role> roles;

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountNonExpired;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountNonLocked;
	}

	/*
	 * Get roles and permissions and add them as a Set of GrantedAuthority
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		roles.forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getName()));
			r.getPermissions().forEach(p -> {
				authorities.add(new SimpleGrantedAuthority(p.getName()));
			});
		});

		if (roles.size() == 0) {
			authorities.add(new SimpleGrantedAuthority("CAN_ORG_READ"));
			authorities.add(new SimpleGrantedAuthority("CAN_AUTH_READ"));
			authorities.add(new SimpleGrantedAuthority("CAN_PROJECT_READ"));
		}

		return authorities;
	}
}
