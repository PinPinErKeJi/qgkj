/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_gxsz.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.jcda_gxsz.entity.Jcdagxsz;

/**
 * 公休设置MAPPER接口
 * @author ww
 * @version 2019-04-18
 */
@Mapper
@Repository
public interface JcdagxszMapper extends BaseMapper<Jcdagxsz> {
	
	Jcdagxsz getLast();
}