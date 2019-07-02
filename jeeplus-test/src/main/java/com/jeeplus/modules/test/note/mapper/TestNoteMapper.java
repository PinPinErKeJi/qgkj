/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.test.note.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.note.entity.TestNote;

/**
 * 富文本测试MAPPER接口
 * @author liugf
 * @version 2018-12-31
 */
@Mapper
@Repository
public interface TestNoteMapper extends BaseMapper<TestNote> {
	
}