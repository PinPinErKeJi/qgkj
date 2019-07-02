/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbqd.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jeeplus.modules.jcda_sbqd.entity.EquipmentInfo;
import com.jeeplus.modules.jcda_sbqd.entity.Jcdasbqd;

/**
 * 设备清单MAPPER接口
 * @author ww
 * @version 2019-04-25
 */
@Mapper
@Repository
public interface JcdasbqdMapper extends BaseMapper<Jcdasbqd> {
	
	@Select("SELECT sbid AS equipmentId,sbmc AS equipmentName, sbxlh AS equipmentNo FROM jcda_sbqd WHERE sbxlh = #{equipmentNo} LIMIT 0,1")
	EquipmentInfo  binding(@Param("equipmentNo")String equipmentNo);
}