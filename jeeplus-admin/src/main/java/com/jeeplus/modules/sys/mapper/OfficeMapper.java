/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import java.util.List;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.modules.sys.entity.Office;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;;

/**
 * 机构MAPPER接口
 * @author jeeplus
 * @version 2017-05-16
 */
@Mapper
@Repository
public interface OfficeMapper extends TreeMapper<Office> {
	
	public Office getByCode(String code);
	
	/**
	 * 查询含有发布资源的组织部门id和parent_ids
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<Office> releaseResOffice(Office office);
	
	@Select("SELECT id,name FROM sys_office WHERE parent_id = '0' AND id=#{id}")
	public Office getCompanyById(String id);
	
	@Select("SELECT id,name FROM sys_office WHERE id =(SELECT parent_id FROM sys_office WHERE id=#{officeId})")
	public Office getCompanyByOffice(String officeId);
}
