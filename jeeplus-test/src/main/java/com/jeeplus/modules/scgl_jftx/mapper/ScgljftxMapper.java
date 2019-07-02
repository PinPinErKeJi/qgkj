/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_jftx.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.scgl_jftx.entity.Scgljftx;

/**
 * 缴费提醒MAPPER接口
 * @author ww
 * @version 2019-06-02
 */
@Mapper
@Repository
public interface ScgljftxMapper extends BaseMapper<Scgljftx> {
	
}