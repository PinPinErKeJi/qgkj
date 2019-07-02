/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_sbwl.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.scgl_sbwl.entity.Scglsbwl;

/**
 * 设备维修MAPPER接口
 * @author ww
 * @version 2019-06-02
 */
@Mapper
@Repository
public interface ScglsbwlMapper extends BaseMapper<Scglsbwl> {
	
}