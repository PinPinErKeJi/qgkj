package com.jeeplus.modules.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.jcda_sbqd.entity.EquipmentInfo;
import com.jeeplus.modules.jcda_sbqd.mapper.JcdasbqdMapper;

@Service
@Transactional(readOnly=true)
public class EquipmentService {

	@Autowired
	private JcdasbqdMapper jcdasbqdMapper;
	
	public EquipmentInfo binding(String equipmentNo) {
		return jcdasbqdMapper.binding(equipmentNo);
	}
}
