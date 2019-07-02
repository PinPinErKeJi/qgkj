/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbgl.service;

import java.util.Date;
import java.util.List;

import org.jeeplus.cmd.equipment.EquipmentCmd;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_sbgl.entity.CmdBaseInfo;
import com.jeeplus.modules.jcda_sbgl.entity.Jcdasbmc;
import com.jeeplus.modules.jcda_sbgl.mapper.JcdasbmcMapper;
import com.jeeplus.modules.utils.BusinessUtils;


/**
 * 设备名称Service
 * @author ww
 * @version 2019-04-07
 */

@Service
@Transactional(readOnly = true)
public class JcdasbmcService extends CrudService<JcdasbmcMapper, Jcdasbmc> {

	public Jcdasbmc get(String id) {
		return super.get(id);
	}
	@Transactional(readOnly=false)
	public boolean setPass(String id,String ip,String pass) {
		boolean flag = false;
		String old = mapper.getPass(id);
		if(StringUtils.isEmpty(old)) {
			flag = EquipmentCmd.setPassWord(ip, pass, pass);
			if(flag) {
				mapper.updatePass(id, pass);
			}
		}else if(old.equals(pass)) {
			return true;
		}else {
			flag = EquipmentCmd.setPassWord(ip, old, pass);
			if(flag) {
				mapper.updatePass(id, pass);
			}
		}
		return flag;
	}
	
	public void setTime(String id) {
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(id);
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		EquipmentCmd.setTime(info.getIp(), info.getPass(), new Date().getTime());
	}
	
	public List<Jcdasbmc> findList(Jcdasbmc jcdasbmc) {
		return super.findList(jcdasbmc);
	}
	
	public Page<Jcdasbmc> findPage(Page<Jcdasbmc> page, Jcdasbmc jcdasbmc) {
		return super.findPage(page, jcdasbmc);
	}
	
	@Transactional(readOnly = false)
	public void save(Jcdasbmc jcdasbmc) {
		super.save(jcdasbmc);
	}
	
	@Transactional(readOnly = false)
	public void delete(Jcdasbmc jcdasbmc) {
		super.delete(jcdasbmc);
	}
	
}