package com.jeeplus.modules.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeeplus.modules.api.bean.ApiResult;
import com.jeeplus.modules.api.service.EquipmentService;
import com.jeeplus.modules.jcda_sbqd.entity.EquipmentInfo;

@RestController
@RequestMapping("api/equipment")
public class EquipmentController {

	@Autowired
	private EquipmentService equipmentService;
	
	@RequestMapping(value="binding",method=RequestMethod.POST)
	public ApiResult<EquipmentInfo> binding(String equipmentNo){
		ApiResult<EquipmentInfo> result = new ApiResult<>();
		if(StringUtils.isEmpty(equipmentNo)) {
			result.setSuccess(false).setMessage("参数错误");
			return result;
		}
		try {
			result.setContent(equipmentService.binding(equipmentNo))
				.setSuccess(true).setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false)
				.setMessage("服务器异常");
			
		}
		return result;
	}
}
