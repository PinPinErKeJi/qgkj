/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.form.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.onetomany.form.entity.TestDataChild23;

/**
 * 汽车票MAPPER接口
 * @author liugf
 * @version 2019-01-02
 */
@Mapper
@Repository
public interface TestDataChild23Mapper extends BaseMapper<TestDataChild23> {
	
}