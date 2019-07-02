/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ygxx_view.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.jcda_ygxx_view.entity.JcdaYgxxView;

/**
 * 人员信息档案MAPPER接口
 * @author ww
 * @version 2019-04-17
 */
@Mapper
@Repository
public interface JcdaYgxxViewMapper extends BaseMapper<JcdaYgxxView> {
	
}