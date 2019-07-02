/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.sc_sbgl.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.sc_sbgl.entity.Scsbgl;

/**
 * 设备管理MAPPER接口
 * @author ww
 * @version 2019-05-31
 */
@Mapper
@Repository
public interface ScsbglMapper extends BaseMapper<Scsbgl> {
    //设备查询
    @Select("select COUNT(*) from sc_sbgl  where arry = #{arry}")
    int selectSbCount(@Param("arry")String arry);
    //修改设备在线状态
    @Update("update sc_sbgl set state = #{state} where arry = #{arry}")
    void updateSbState(@Param("state")String state,@Param("arry")String arry);
}