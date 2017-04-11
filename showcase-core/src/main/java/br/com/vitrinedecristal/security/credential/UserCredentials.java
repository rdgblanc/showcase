package br.com.vitrinedecristal.security.credential;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class UserCredentials implements UserDetails {

	private Long id;
	private String username;
	private String password;
	protected List<GrantedAuthority> grantedAuthority;

	public UserCredentials(Long id, String username, String password, List<GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.grantedAuthority = authorities;
	}

	public Long getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.grantedAuthority;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

}