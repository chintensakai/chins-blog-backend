package com.chins.blog.backend.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chins.blog.backend.security.entity.SysUser;
import org.apache.ibatis.annotations.Select;

public interface SysUserMapper extends BaseMapper<SysUser> {

  @Select(
      "SELECT u.*, r.* FROM chinsblog.sys_user u left join chinsblog.sys_user_role ur on u.sys_user_id = ur.sys_user_id\n"
          + "left join sys_role r on r.sys_role_id = ur.sys_role_id where u.sys_user_name = #{username}")
  SysUser selectUserByName(String username);
}
