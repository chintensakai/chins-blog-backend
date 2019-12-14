package com.chins.blog.backend.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chins.blog.backend.security.entity.SysUser;
import com.chins.blog.backend.security.mapper.SysUserMapper;
import com.chins.blog.backend.security.service.SysUserService;
import com.chins.blog.backend.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements
    SysUserService {

  @Autowired
  private SysUserMapper sysUserMapper;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  public String selectUserByUsernameAndPwd(String username, String password) {

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        username, password);

    Authentication authentication = authenticationManager.authenticate(authenticationToken);

    System.out.println("---------------- " + authentication);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return jwtUtils.generateTokenFromUsername(username);
  }
}
