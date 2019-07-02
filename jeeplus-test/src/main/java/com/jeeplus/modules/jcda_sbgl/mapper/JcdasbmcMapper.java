/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbgl.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jeeplus.modules.jcda_sbgl.entity.CmdBaseInfo;
import com.jeeplus.modules.jcda_sbgl.entity.Jcdasbmc;

/**
 * 设备名称MAPPER接口
 * @author ww
 * @version 2019-04-07
 */
@Mapper
@Repository
public interface JcdasbmcMapper extends BaseMapper<Jcdasbmc> {
	
	@Select("SELECT mm  FROM jcda_sbmc WHERE id = #{id}")
	String getPass(@Param("id")String id);
	
	@Update("UPDATE jcda_sbmc SET mm = #{pass} WHERE id = #{id} ")
	void updatePass(@Param("id")String id,@Param("pass")String pass);
	
	@Select("SELECT ip, mm AS pass, sbxlh AS deviceKey FROM jcda_sbmc WHERE id = #{id}")
	CmdBaseInfo getCmdBaseInfo(@Param("id")String id);
}