/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_banci.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.jeeplus.modules.jcda_banci.entity.BanCiBean;
import com.jeeplus.modules.jcda_banci.entity.Jcdabanci;

/**
 * 班次设置MAPPER接口
 * @author ww
 * @version 2019-04-16
 */
@Mapper
@Repository
public interface JcdabanciMapper extends BaseMapper<Jcdabanci> {
	
	@Select("SELECT id,banci AS type,dkkssj AS onestart,sbsj AS oneend,xbsj AS twostart,"
			+ " dkjssj AS twoend,jxs,jts FROM jcda_banci WHERE del_flag ='0'")
	List<BanCiBean> findAllBanci();
}