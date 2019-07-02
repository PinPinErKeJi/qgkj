/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbyhxx.service;

import java.util.Date;
import java.util.List;
import org.jeeplus.cmd.employee.CallBackPersonInfo;
import org.jeeplus.cmd.employee.EmployeeCmd;
import org.jeeplus.cmd.employee.RegisterPersonInfo;
import org.jeeplus.cmd.employee.SearchEmployeeCollection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_sbgl.entity.CmdBaseInfo;
import com.jeeplus.modules.jcda_sbyhxx.entity.JcdaSbmcView;
import com.jeeplus.modules.jcda_sbyhxx.entity.Jcdasbyhxx;
import com.jeeplus.modules.jcda_sbyhxx.mapper.JcdasbyhxxMapper;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.utils.BusinessUtils;
/**
 * 设备用户信息Service
 * @author ww
 * @version 2019-04-08
 */

@Service
@Transactional(readOnly = true)
public class JcdasbyhxxService extends CrudService<JcdasbyhxxMapper, Jcdasbyhxx> {

	public Jcdasbyhxx get(String id) {
		return super.get(id);
	}
	
	public List<Jcdasbyhxx> findList(Jcdasbyhxx jcdasbyhxx) {
		return super.findList(jcdasbyhxx);
	}
	
	public Page<Jcdasbyhxx> findPage(Page<Jcdasbyhxx> page, Jcdasbyhxx jcdasbyhxx) {
		return super.findPage(page, jcdasbyhxx);
	}
	
	@Transactional(readOnly = false)
	public void save(Jcdasbyhxx jcdasbyhxx) {
		boolean isnew = StringUtils.isEmpty(jcdasbyhxx.getId());
		super.save(jcdasbyhxx);
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(jcdasbyhxx.getSb().getId());
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		if(isnew) {
			EmployeeCmd.create(info.getIp(), info.getPass(), 
					new RegisterPersonInfo(jcdasbyhxx.getId(),jcdasbyhxx.getCode(),jcdasbyhxx.getName()));
		}else {
			EmployeeCmd.update(info.getIp(), info.getPass(), 
					new RegisterPersonInfo(jcdasbyhxx.getId(),jcdasbyhxx.getCode(),jcdasbyhxx.getName()));
		}
	}
	
	
	@Transactional(readOnly = false)
	public void delete(Jcdasbyhxx jcdasbyhxx) {
		super.delete(jcdasbyhxx);
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(jcdasbyhxx.getSb().getId());
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		String rs = EmployeeCmd.delete(info.getIp(), info.getPass(), jcdasbyhxx.getId());
		if(!"0".equals(rs)) {
			throw new RuntimeException("删除失败.");
		}
	}
	
	@Transactional(readOnly = false)
	public void synchronize(String sbId) {
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(sbId);
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		SearchEmployeeCollection employeeCollection = 
				EmployeeCmd.findByPage(info.getIp(), info.getPass(), 200, 0);
		synch(sbId, employeeCollection,info);
		int size = employeeCollection.getPageInfo().getSize();
		if(size>1) {
			for (int i = 1; i < size; i++) {
				employeeCollection = 
						EmployeeCmd.findByPage(info.getIp(), info.getPass(), 200, i);
				synch(sbId, employeeCollection, info);
			}
		}
	}
	
	private void synch(String sbId, SearchEmployeeCollection employeeCollection,CmdBaseInfo info) {
		List<CallBackPersonInfo> alist = employeeCollection.getPersonInfos();
		alist.forEach(e->{
			Jcdasbyhxx yh = new Jcdasbyhxx(new JcdaSbmcView(sbId));
			yh.setId(e.getId());
			yh.setCode(e.getIdcardNum());
			yh.setName(e.getName());
			yh.setCreateBy(UserUtils.getUser());
			yh.setCreateDate(new Date(Long.valueOf(e.getCreateTime())));
			try {
				if(0==mapper.isExist(e.getId())) {
					mapper.insert(yh);
				}else {
					mapper.synchronize(e.getId(), e.getIdcardNum(), e.getName());
				}
			}catch (Exception ex) {
			}
			
		});
	}
}