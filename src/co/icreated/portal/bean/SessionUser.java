package co.icreated.portal.bean;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionUser implements UserDetails {
	

	private static final long serialVersionUID = 4033276243673320690L;
	
	int userId;
	String value;
	String name;
	String email;
	String password;
	String salt;
	int partnerId;

	boolean isAccountNonExpired = true;
	boolean isAccountNonLocked = true;
	boolean isCredentialsNonExpired = true;
	boolean isEnabled = true;
	
	List<GrantedAuthority> authorities;
	
	
	
	

	public SessionUser(int userId, String value, String name, String email, String password, String salt, int partnerId,
			boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
		super();
		this.userId = userId;
		this.value = value;
		this.name = name;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.partnerId = partnerId;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getPassword() {

		return this.password;
	}

	@Override
	public String getUsername() {

		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return this.isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return this.isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return this.isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		
		return this.isEnabled;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public int getUserId() {
		return userId;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getSalt() {
		return salt;
	}

	public int getPartnerId() {
		return partnerId;
	}

	
	
}
