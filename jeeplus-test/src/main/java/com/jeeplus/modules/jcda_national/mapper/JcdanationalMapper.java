/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_national.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.jcda_national.entity.Jcdanational;

/**
 * 民族MAPPER接口
 * @author ww
 * @version 2019-04-07
 */
@Mapper
@Repository
public interface JcdanationalMapper extends BaseMapper<Jcdanational> {
	
}