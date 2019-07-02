/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ryxx.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jeeplus.modules.jcda_ryxx.entity.Jcdaygxx;
import com.jeeplus.modules.jcda_ryxx.entity.PersonInfo;

/**
 * 员工信息MAPPER接口
 * @author ww
 * @version 2019-04-07
 */
@Mapper
@Repository
public interface JcdaygxxMapper extends BaseMapper<Jcdaygxx> {
	
	@Select("SELECT name FROM jcda_ygxx WHERE id = #{id}")
	String getName(@Param("id")String id);
	
	@Select("SELECT zdy1 FROM jcda_ygxx WHERE id = #{id}")
	String getSbId(@Param("id")String id);
	
	@Update("UPDATE jcda_ygxx SET personstate = #{status} WHERE id = #{id}")
	void updateStatus(@Param("id")String id, @Param("status")String status);
	
	@Select("SELECT id,name,cardno FROM jcda_ygxx WHERE id = #{id}")
	Jcdaygxx getById(@Param("id")String id);
	
	@Select("SELECT id,name,code,jg_id AS office,shift AS bc FROM jcda_ygxx WHERE del_flag = '0' and personstate = '在职' order by code asc")
	List<PersonInfo> findAllJob();

	void updateYgxxState(@Param("id")String id);
}