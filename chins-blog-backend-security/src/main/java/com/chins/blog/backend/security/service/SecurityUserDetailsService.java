package com.chins.blog.backend.security.service;

import com.chins.blog.backend.commons.exception.BlogException;
import com.chins.blog.backend.security.entity.SecurityUserDetails;
import com.chins.blog.backend.security.entity.SysRole;
import com.chins.blog.backend.security.entity.SysUser;
import com.chins.blog.backend.security.mapper.SysUserMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

  @Autowired
  private SysUserMapper sysUserMapper;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

    SysUser sysUser = sysUserMapper.selectUserByName(s);

    if (null != sysUser) {

      List<SimpleGrantedAuthority> authorities = sysUser.getRoles().stream()
          .map(SysRole::getSysRoleName).map(SimpleGrantedAuthority::new).collect(
              Collectors.toList());

      return new SecurityUserDetails(sysUser.getSysUserPassword(), sysUser.getSysUserName(),
          sysUser.getSysUserState(), authorities);
    }

    throw new BlogException("User " + s + " does not exist!");
  }
}
