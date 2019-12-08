package com.chins.blog.backend.security.entity;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUserDetails implements UserDetails {

  private String password;
  private String username;
  private Integer status;
  private Collection<? extends GrantedAuthority> authorities;

  public SecurityUserDetails(String password, String username, Integer status,
      Collection<? extends GrantedAuthority> authorities) {
    this.password = password;
    this.username = username;
    this.status = status;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return status == 1;
  }
}
