/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbgl_view.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.jcda_sbgl_view.entity.JcdaSbglView;

/**
 * 设备名称MAPPER接口
 * @author ww
 * @version 2019-04-14
 */
@Mapper
@Repository
public interface JcdaSbglViewMapper extends BaseMapper<JcdaSbglView> {
	
}