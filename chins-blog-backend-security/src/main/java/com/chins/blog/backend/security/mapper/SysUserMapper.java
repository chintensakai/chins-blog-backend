package com.chins.blog.backend.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chins.blog.backend.security.entity.SysUser;
import org.apache.ibatis.annotations.Select;

public interface SysUserMapper extends BaseMapper<SysUser> {

  //  @Results(id = "BaseResultMap", value = {
//      @Result(id = true, column = "sys_user_id", property = "sysUserId"),
//      @Result(column = "sys_user_avatar", property = "sysUserAvatar"),
//      @Result(column = "sys_user_account", property = "sysUserAccount"),
//      @Result(column = "sys_user_password", property = "sysUserPassword"),
//      @Result(column = "sys_user_name", property = "sysUserName"),
//      @Result(column = "sys_user_phone", property = "sysUserPhone"),
//      @Result(column = "sys_user_email", property = "sysUserEmail"),
//      @Result(column = "sys_user_state", property = "sysUserState"),
//      @Result(column = "sys_add_time", property = "sysAddTime"),
//      @Result(column = "sys_up_time", property = "sysUpTime"),
//      @Result(property="roles", column="sys_user_id", many = @Many(select = "com.chins.blog.backend.security.mapper.selectRoleListByUserId"))
//  })
  @Select("SELECT sys_user_id,sys_user_state,sys_add_time,sys_up_time,sys_user_password,sys_user_email,sys_user_name,sys_user_avatar,sys_user_account,sys_user_phone FROM sys_user WHERE sys_user_name=#{username}")
  SysUser selectUserByName(String username);

  @Select("SELECT sys_user_id,sys_user_state,sys_add_time,sys_up_time,sys_user_password,sys_user_email,sys_user_name,sys_user_avatar,sys_user_account,sys_user_phone FROM sys_user WHERE sys_user_id=#{id}")
  SysUser selectUserById(Long id);
}
