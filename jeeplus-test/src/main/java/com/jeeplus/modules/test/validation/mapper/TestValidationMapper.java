/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.test.validation.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.validation.entity.TestValidation;

/**
 * 测试校验功能MAPPER接口
 * @author lgf
 * @version 2019-01-02
 */
@Mapper
@Repository
public interface TestValidationMapper extends BaseMapper<TestValidation> {
	
}