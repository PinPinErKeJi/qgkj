/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.form.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.onetomany.form.entity.TestDataMainForm;

/**
 * 票务代理MAPPER接口
 * @author liugf
 * @version 2019-01-02
 */
@Mapper
@Repository
public interface TestDataMainFormMapper extends BaseMapper<TestDataMainForm> {
	
}