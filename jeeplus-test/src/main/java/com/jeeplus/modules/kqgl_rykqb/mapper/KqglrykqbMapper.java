/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.kqgl_rykqb.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.jeeplus.modules.kqgl_rykqb.entity.Kqglrykqb;

/**
 * 员工考勤表MAPPER接口
 * @author ww
 * @version 2019-04-17
 */
@Mapper
@Repository
public interface KqglrykqbMapper extends BaseMapper<Kqglrykqb> {
	
	@Select("SELECT kqnyr FROM kqgl_rykqb ORDER BY kqnyr DESC LIMIT 0,1")
	String getLastYMD();
}