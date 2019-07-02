/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.sys_user_view.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.sys_user_view.entity.SysUserView;

/**
 * sys_user_viewMAPPER接口
 * @author ww
 * @version 2019-02-16
 */
@Mapper
@Repository
public interface SysUserViewMapper extends BaseMapper<SysUserView> {
	
}