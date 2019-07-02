/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.test.treetable.dialog.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.treetable.dialog.entity.CarKind1;

/**
 * 车系MAPPER接口
 * @author lgf
 * @version 2019-01-01
 */
@Mapper
@Repository
public interface CarKind1Mapper extends TreeMapper<CarKind1> {
	
}