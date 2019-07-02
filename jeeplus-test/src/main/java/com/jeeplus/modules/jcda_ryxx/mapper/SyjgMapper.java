/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ryxx.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.jcda_ryxx.entity.Syjg;

/**
 * 人员信息MAPPER接口
 * @author ww
 * @version 2019-04-07
 */
@Mapper
@Repository
public interface SyjgMapper extends TreeMapper<Syjg> {
	
}