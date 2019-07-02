/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ygda.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jeeplus.modules.jcda_ygda.entity.EmployeeInfo;
import com.jeeplus.modules.jcda_ygda.entity.Jcdaryda;

/**
 * 人员档案MAPPER接口
 * @author ww
 * @version 2019-04-25
 */
@Mapper
@Repository
public interface JcdarydaMapper extends BaseMapper<Jcdaryda> {
	
	@Select("SELECT COUNT(*) FROM jcda_ygxx  WHERE zdy1 LIKE CONCAT('%',#{equipmentId},'%') AND del_flag = '0' AND  personstate = '在职'")
	int countEmployee(@Param("equipmentId")String equipmentId);
	
	@Select("SELECT id AS employeeId,jg_id AS companyId,name AS employeeName,code AS employeeCode,sex AS employeeSex "
			+ " FROM jcda_ygxx  WHERE zdy1 LIKE CONCAT('%',#{equipmentId},'%') "
			+ "AND del_flag = '0' AND  personstate = '在职' LIMIT #{from},#{size}")
	List<EmployeeInfo> findByEquipmentId(@Param("equipmentId")String equipmentId,@Param("from")int from,@Param("size")int size);
	
}