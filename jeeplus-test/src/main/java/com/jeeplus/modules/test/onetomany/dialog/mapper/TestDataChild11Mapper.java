/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.dialog.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataChild11;

/**
 * 火车票MAPPER接口
 * @author liugf
 * @version 2019-01-01
 */
@Mapper
@Repository
public interface TestDataChild11Mapper extends BaseMapper<TestDataChild11> {
	
}