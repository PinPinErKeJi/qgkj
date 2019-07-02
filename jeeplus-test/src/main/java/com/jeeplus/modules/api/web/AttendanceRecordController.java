package com.jeeplus.modules.api.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeeplus.modules.api.bean.ApiPage;
import com.jeeplus.modules.api.bean.ApiResult;
import com.jeeplus.modules.api.service.RecordService;
import com.jeeplus.modules.jcda_sbkqxx.entity.AttendanceRecordInfo;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceRecordController {

	@Autowired
	private RecordService recordService;
	
	@RequestMapping(value="find",method=RequestMethod.POST)
	public ApiResult<ApiPage<AttendanceRecordInfo>> find(String equipmentId,String employeeId,
			String start,String end,
			Integer page,Integer pageSize){
		if(page==null||page<1) {
			page=1;
		}
		if(pageSize==null || pageSize <1) {
			pageSize = 10;
		}
		if("-1".equals(employeeId)) {
			employeeId = null;
		}
		if("0".equals(start)||"0".equals(end)) {
			start = null;
			end = null;
		}
		ApiResult<ApiPage<AttendanceRecordInfo>> result = new ApiResult<>();
		if(StringUtils.isEmpty(equipmentId)) {
			result.setSuccess(false).setMessage("参数错误");
			return result;
		}
		ApiPage<AttendanceRecordInfo> apiPage = new ApiPage<>();
		try {
			int rows = recordService.count(equipmentId, employeeId, start, end);
			apiPage.setPage(page).setTotal(rows%pageSize==0?rows/pageSize:rows/pageSize+1).setRows(rows);
			if(rows==0) {
				apiPage.setData(new ArrayList<>());
			}else {
				List<AttendanceRecordInfo> list =  recordService.find(equipmentId, employeeId, start, end, page, pageSize);				
				apiPage.setData(list);
			}
			result.setSuccess(true).setContent(apiPage).setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false).setMessage("服务器异常");
		}
		return result;
	}
}
