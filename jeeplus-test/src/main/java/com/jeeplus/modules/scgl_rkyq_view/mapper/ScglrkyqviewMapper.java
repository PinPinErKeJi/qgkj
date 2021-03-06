/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_rkyq_view.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.scgl_rkyq_view.entity.Scglrkyqview;

/**
 * 员工考勤MAPPER接口
 * @author ww
 * @version 2019-06-02
 */
@Mapper
@Repository
public interface ScglrkyqviewMapper extends BaseMapper<Scglrkyqview> {
	
}