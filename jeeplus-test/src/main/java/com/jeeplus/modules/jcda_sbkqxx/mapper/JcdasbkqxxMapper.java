/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbkqxx.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jeeplus.modules.api.bean.JcdasbkqxxBean;
import com.jeeplus.modules.jcda_sbkqxx.entity.AttendanceRecordInfo;
import com.jeeplus.modules.jcda_sbkqxx.entity.ClockInOut;
import com.jeeplus.modules.jcda_sbkqxx.entity.Jcdasbkqxx;

/**
 * 设备考勤信息MAPPER接口
 * @author ww
 * @version 2019-04-08
 */
@Mapper
@Repository
public interface JcdasbkqxxMapper extends BaseMapper<Jcdasbkqxx> {
	/**
	 * 查询数据设备考勤信息
	 */
	List<JcdasbkqxxBean> findAtendance();
	/**
	 * 提供对外服务数据App
	 */
	List<JcdasbkqxxBean> findApp(@Param("sb_id")String sb_id,@Param("code")String code,
								 @Param("beginDate")Date beginDate,@Param("endDate")Date endDate,
								 @Param("from")int from,@Param("pageSize")int pageSize);

	/*
	数据总数
	 */
	@Select("select COUNT(*) from jcda_sbkqxx")
	int rowCount();
	/**
	 * 修改同步数据状态
	 */
	@Update("update  jcda_sbkqxx set zdy5 = '0' where id = #{ids}")
	void updateSbkState(@Param("ids")String id);

	@Select("SELECT count(*) FROM jcda_sbkqxx  WHERE id = #{id}")
	int isExist(@Param("id")String id);
	
	@Select("SELECT date FROM jcda_sbkqxx where sb_id = #{sbId} order by date desc limit 0,1")
	Date getLastSynchronize(@Param("sbId")String sbId);
	@Select("SELECT date FROM jcda_sbkqxx order by date asc limit 0,1")
	Date getOld();
	/**
	 * 上班打卡（最早一条）
	 */
	@Select("select a.id,a.zdy4 AS image ,a.sb_id AS sbId,a.date,v.sbxlh,v.name AS sbName from jcda_sbkqxx a "
			+ "left join jcda_sbmc_view v on a.sb_id = v.id where zdy1 = #{personId} "
			+ "and date between #{start} and #{end} order by date asc limit 0,1")
	ClockInOut getClockIn(@Param("start")String start,@Param("end")String end,@Param("personId")String personId);
	
	
	int countAtendanceByEquipment(@Param("equipmentId")String equipmentId,@Param("employeeId")String employeeId,@Param("start")String start,@Param("end")String end);
	
	List<AttendanceRecordInfo> findAtendanceByEquipment(@Param("equipmentId")String equipmentId,@Param("employeeId")String employeeId,
				@Param("start")String start,@Param("end")String end,@Param("from")int from,@Param("size")int size);
}