/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbyhxx.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jeeplus.modules.jcda_sbyhxx.entity.Jcdasbyhxx;

/**
 * 设备用户信息MAPPER接口
 * @author ww
 * @version 2019-04-08
 */
@Mapper
@Repository
public interface JcdasbyhxxMapper extends BaseMapper<Jcdasbyhxx> {
	
	@Select("SELECT count(*) FROM jcda_sbyhxx WHERE id = #{id}")
	int isExist(@Param("id")String id);
	
	@Update("UPDATE jcda_sbyhxx SET code=#{code}, name=#{name} WHERE id = #{id}")
	void synchronize(@Param("id")String id, @Param("code")String code,@Param("name")String name);
	
	@Select("SELECT name FROM jcda_sbyhxx WHERE id = #{id}")
	String getName(@Param("id")String id);
	
}