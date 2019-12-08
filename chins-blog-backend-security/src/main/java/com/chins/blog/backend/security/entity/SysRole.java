package com.chins.blog.backend.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role")
public class SysRole {

  private Long sysRoleId;
  private String sysRoleName;
  private String sysRoleDescribe;
  private String sysRoleState;
  private String sysAddTime;
  private String sysUpTime;
}
