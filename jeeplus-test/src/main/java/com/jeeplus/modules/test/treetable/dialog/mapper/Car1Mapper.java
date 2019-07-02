/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.test.treetable.dialog.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.treetable.dialog.entity.Car1;

/**
 * 车辆MAPPER接口
 * @author lgf
 * @version 2019-01-01
 */
@Mapper
@Repository
public interface Car1Mapper extends BaseMapper<Car1> {
	
}