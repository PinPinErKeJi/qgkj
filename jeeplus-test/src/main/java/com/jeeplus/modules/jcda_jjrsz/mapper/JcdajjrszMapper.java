/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_jjrsz.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.jeeplus.modules.jcda_jjrsz.entity.Jcdajjrsz;

/**
 * 节假日设置MAPPER接口
 * @author ww
 * @version 2019-04-16
 */
@Mapper
@Repository
public interface JcdajjrszMapper extends BaseMapper<Jcdajjrsz> {
	
	@Select("SELECT COUNT(*) FROM jcda_jjrsz WHERE #{date} between kssj and jssj")
	int isInHoliday(String date);
}