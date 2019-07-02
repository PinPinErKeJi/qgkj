/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_sbqk.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.scgl_sbqk.entity.Scglsbqk;

/**
 * 设备使用情况MAPPER接口
 * @author ww
 * @version 2019-06-02
 */
@Mapper
@Repository
public interface ScglsbqkMapper extends BaseMapper<Scglsbqk> {
	
}