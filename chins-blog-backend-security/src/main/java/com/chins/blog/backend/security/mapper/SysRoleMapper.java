package com.chins.blog.backend.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chins.blog.backend.security.entity.SysRole;
import com.chins.blog.backend.security.entity.SysUserRole;
import java.util.List;
import org.apache.ibatis.annotations.Select;

public interface SysRoleMapper extends BaseMapper<SysRole> {

  @Select("SELECT * FROM chinsblog.sys_role where sys_role_id = #{sysRoleId}")
  SysRole selectSysRoleBy(Long sysRoleId);

  //  @Results(id = "BaseResultMap", value = {
//      @Result(id = true, column = "sys_role_id", property = "sysRoleId"),
//      @Result(column = "sys_role_name", property = "sysRoleName"),
//      @Result(column = "sys_role_describe", property = "sysRoleDescribe"),
//      @Result(column = "sys_role_state", property = "sysRoleState"),
//      @Result(column = "sys_add_time", property = "sysAddTime"),
//      @Result(column = "sys_up_time", property = "sysUpTime"),
//      @Result(property="roles", column="sys_user_id", many = @Many(select = "com.chins.blog.backend.security.mapper.selectRoleListByUserId"))
//  })
  @Select("SELECT * FROM chinsblog.sys_user_role where sys_user_id = #{sysUserId}")
  List<SysUserRole> selectRoleListByUserId(Long sysUserId);

}
