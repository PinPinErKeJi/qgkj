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
import com.jeeplus.modules.api.service.EmployeeService;
import com.jeeplus.modules.jcda_ygda.entity.EmployeeInfo;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value="find",method=RequestMethod.POST)
	public ApiResult<ApiPage<EmployeeInfo>> find(String equipmentId,Integer page,Integer pageSize){
		if(page==null||page<1) {
			page=1;
		}
		if(pageSize==null || pageSize <1) {
			pageSize = 10;
		}
		ApiResult<ApiPage<EmployeeInfo>> result = new ApiResult<>();
		if(StringUtils.isEmpty(equipmentId)) {
			result.setSuccess(false).setMessage("参数错误");
			return result;
		}
		ApiPage<EmployeeInfo> apiPage = new ApiPage<>();

		try {
			int rows = employeeService.countRowsByEquipmentId(equipmentId);
			apiPage.setPage(page).setTotal(rows%pageSize==0?rows/pageSize:rows/pageSize+1).setRows(rows);
			if(rows==0) {
				apiPage.setData(new ArrayList<>());
			}else {
				List<EmployeeInfo> list = employeeService.findByEmployee(equipmentId, page, pageSize);
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
