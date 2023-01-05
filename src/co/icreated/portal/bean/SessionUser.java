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



  public static class Builder  {

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

    public SessionUser build() {
      return new SessionUser(this);
    }

    public Builder userId(int userId) {
      this.userId = userId;
      return this;
    }
    public Builder value(String value) {
      this.value = value;
      return this;
    }
    public Builder name(String name) {
      this.name = name;
      return this;
    }
    public Builder email(String email) {
      this.email = email;
      return this;
    }
    public Builder password(String password) {
      this.password = password;
      return this;
    }
    public Builder salt(String salt) {
      this.salt = salt;
      return this;
    }
    public Builder partnerId(int partnerId) {
      this.partnerId = partnerId;
      return this;
    }
    public Builder accountNonExpired(boolean isAccountNonExpired) {
      this.isAccountNonExpired = isAccountNonExpired;
      return this;
    }
    public Builder accountNonLocked(boolean isAccountNonLocked) {
      this.isAccountNonLocked = isAccountNonLocked;
      return this;
    }
    public Builder credentialsNonExpired(boolean isCredentialsNonExpired) {
      this.isCredentialsNonExpired = isCredentialsNonExpired;
      return this;
    }
    public Builder enabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
      return this;
    }
    public Builder authorities(List<GrantedAuthority> authorities) {
	    this.authorities = authorities;
	    return this;
	  }

  }
  

  private SessionUser(Builder builder) {
    this.userId = builder.userId;
    this.value = builder.value;
    this.name = builder.name;
    this.email = builder.email;
    this.password = builder.password;
    this.salt = builder.salt;
    this.partnerId = builder.partnerId;
    this.isAccountNonExpired = builder.isAccountNonExpired;
    this.isAccountNonLocked = builder.isAccountNonLocked;
    this.isCredentialsNonExpired = builder.isCredentialsNonExpired;
    this.isEnabled = builder.isEnabled;
    this.authorities = builder.authorities;
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

    return value;
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

  @Override
  public String toString() {
    return "SessionUser [userId=" + userId + ", name=" + name + ", email=" + email + "]";
  }



}
