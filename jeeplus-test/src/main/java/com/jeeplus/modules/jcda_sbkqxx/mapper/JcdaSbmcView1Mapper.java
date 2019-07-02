/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbkqxx.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.jcda_sbkqxx.entity.JcdaSbmcView1;

/**
 * 设备考勤信息MAPPER接口
 * @author ww
 * @version 2019-04-08
 */
@Mapper
@Repository
public interface JcdaSbmcView1Mapper extends TreeMapper<JcdaSbmcView1> {
	
}