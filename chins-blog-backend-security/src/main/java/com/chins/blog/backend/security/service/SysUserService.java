package com.chins.blog.backend.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chins.blog.backend.security.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

  String selectUserByUsernameAndPwd(String username, String password);

  SysUser selectUserById(Long id);
}
