package com.chins.blog.backend.core.controller;

import com.chins.blog.backend.commons.base.ResponseBase;
import com.chins.blog.backend.security.service.SysUserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
}
