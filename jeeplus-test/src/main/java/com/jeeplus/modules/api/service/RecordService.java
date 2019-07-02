package com.jeeplus.modules.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.jcda_sbkqxx.entity.AttendanceRecordInfo;
import com.jeeplus.modules.jcda_sbkqxx.mapper.JcdasbkqxxMapper;

@Service
@Transactional(readOnly=true)
public class RecordService {

	@Autowired
	private JcdasbkqxxMapper jcdasbkqxxMapper;
	
	public int count(String equipmentId,String employeeId,String start,String end) {
		return jcdasbkqxxMapper.countAtendanceByEquipment(equipmentId, employeeId, start, end);
	}
	
	public List<AttendanceRecordInfo> find(String equipmentId,String employeeId,String start,String end,int page,int size){
		int from = (page-1) * size;
		return jcdasbkqxxMapper.findAtendanceByEquipment(equipmentId, employeeId, start, end, from, size);
	}
}
