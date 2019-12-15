package com.chins.blog.backend.core.controller;

import com.chins.blog.backend.commons.base.ResponseBase;
import com.chins.blog.backend.security.entity.SysUser;
import com.chins.blog.backend.security.service.SysUserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysUserController {

  @Autowired
  private SysUserService sysUserService;

  @PostMapping("/login")
  public ResponseBase login(@RequestBody Map<String, String> map) {

    String token = sysUserService
        .selectUserByUsernameAndPwd(map.get("username"), map.get("password"));
    return ResponseBase.success(token);
  }

  @GetMapping("/user/{id}")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseBase getUserInfo(@PathVariable Long id) {

    SysUser sysUser = sysUserService.selectUserById(id);

    return ResponseBase.success(sysUser);
  }
}
