/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.scsb_ryxx.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.scsb_ryxx.entity.Scsbmain;

/**
 * 人员信息MAPPER接口
 * @author ww
 * @version 2019-06-02
 */
@Mapper
@Repository
public interface ScsbmainMapper extends BaseMapper<Scsbmain> {
	
}