package com.jeeplus.modules.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.jcda_ygda.entity.EmployeeInfo;
import com.jeeplus.modules.jcda_ygda.mapper.JcdarydaMapper;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.service.OfficeService;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
	@Autowired
	private JcdarydaMapper jcdarydaMapper;
	@Autowired
	private OfficeService officeService;
	public int countRowsByEquipmentId(String equipmentId) {
		return jcdarydaMapper.countEmployee(equipmentId);
	}
	
	public List<EmployeeInfo> findByEmployee(String equipmentId,int page,int size){
		int from = (page-1) * size;
		List<EmployeeInfo> list = jcdarydaMapper.findByEquipmentId(equipmentId, from, size);
		if(list == null) {
			return new ArrayList<>(0);
		}
		Map<String, Office> officeMap = new HashMap<>();
		list.forEach(e->{
			if(officeMap.containsKey(e.getCompanyId())) {
				Office office = officeMap.get(e.getCompanyId());
				e.setCompanyName(office==null?"暂无":office.getName());
			}else {
				Office office = officeService.getCompany(e.getCompanyId());
				officeMap.put(e.getCompanyId(), office);
				e.setCompanyName(office==null?"暂无":office.getName());
			}
		});
		return list;
	}
}
