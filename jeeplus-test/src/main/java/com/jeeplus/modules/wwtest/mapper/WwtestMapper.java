/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.wwtest.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.wwtest.entity.Wwtest;

/**
 * wwtestMAPPER接口
 * @author ww
 * @version 2019-02-15
 */
@Mapper
@Repository
public interface WwtestMapper extends BaseMapper<Wwtest> {
	
}