package com.chins.blog.backend.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.List;
import lombok.Data;

@Data
@TableName("sys_user")
public class SysUser {

  @TableId(value = "sys_user_id", type = IdType.AUTO)
  private Long sysUserId;
  private String sysUserAvatar;
  private String sysUserAccount;
  private String sysUserPassword;
  private String sysUserName;
  private String sysUserPhone;
  private String sysUserEmail;
  private int sysUserState;
  private String sysAddTime;
  private String sysUpTime;
  private List<SysRole> roles;

}
