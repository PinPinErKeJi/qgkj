/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ryxx.service;

import java.util.List;
import java.util.UUID;

import org.jeeplus.cmd.employee.CallBackPersonInfo;
import org.jeeplus.cmd.employee.EmployeeCmd;
import org.jeeplus.cmd.employee.RegisterPersonInfo;
import org.jeeplus.cmd.photo.PhotoCmd;
import org.jeeplus.cmd.photo.SearchPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_ryxx.entity.Jcdaygxx;
import com.jeeplus.modules.jcda_ryxx.entity.Photo;
import com.jeeplus.modules.jcda_ryxx.mapper.JcdaygxxMapper;
import com.jeeplus.modules.jcda_ryxx.mapper.PhotoMapper;
import com.jeeplus.modules.jcda_sbgl.entity.CmdBaseInfo;
import com.jeeplus.modules.utils.BusinessUtils;
import com.jeeplus.modules.utils.ImageUtils;
/**
 * 员工信息Service
 * @author ww
 * @version 2019-04-07
 */

@Service
@Transactional(readOnly = true)
public class JcdaygxxService extends CrudService<JcdaygxxMapper, Jcdaygxx> {

	@Autowired
	private PhotoMapper photoMapper;
	
	public Photo findPhotoByPid(String pid){
		List<Photo> list = photoMapper.findByPerson(pid);
		return list==null||list.isEmpty()?new Photo():list.get(0);
	}
	public Jcdaygxx get(String id) {
		return super.get(id);
	}
	
	public List<Jcdaygxx> findList(Jcdaygxx jcdaygxx) {
		return super.findList(jcdaygxx);
	}
	//
	public Page<Jcdaygxx> findPage(Page<Jcdaygxx> page, Jcdaygxx jcdaygxx) {
		return super.findPage(page, jcdaygxx);
	}
	
	@Transactional(readOnly = false)
	public void dimission(String rids) {
		String[] ids = rids.split(",");
		for (int i = 0; i < ids.length; i++) {
			String sbId = mapper.getSbId(ids[i]);
			CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(sbId);
			if(info==null||!info.isabled()) {
				throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
			}
			mapper.updateStatus(ids[i], "离职");
			EmployeeCmd.delete(info.getIp(), info.getPass(), ids[i]);
		}
	}
	
	@Transactional(readOnly = false)
	public void reRegister(String rids) {
		String[] ids = rids.split(",");
		for (int i = 0; i < ids.length; i++) {
			String sbId = mapper.getSbId(ids[i]);
			CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(sbId);
			if(info==null||!info.isabled()) {
				throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
			}
			mapper.updateStatus(ids[i], "在职");
			Jcdaygxx jcdaygxx = mapper.getById(ids[i]);
			EmployeeCmd.create(info.getIp(), info.getPass(), 
					new RegisterPersonInfo(jcdaygxx.getId(),jcdaygxx.getCardno(),jcdaygxx.getName()));
		}
	}
	@Transactional(readOnly = false)
	public void updateYgxxState(String id){
			mapper.updateYgxxState(id);
	}
	@Transactional(readOnly = false)
	public  void saveAppUser(Jcdaygxx jcdaygxx){
		super.save(jcdaygxx);
	}

	@Transactional(readOnly = false)
	public void saveAdd(Jcdaygxx jcdaygxx){
		super.saveAdd(jcdaygxx);
	}

	@Transactional(readOnly = false)
	public void save(Jcdaygxx jcdaygxx) {
		boolean isnew = StringUtils.isEmpty(jcdaygxx.getId());
		super.save(jcdaygxx);
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(jcdaygxx.getSb().getId());
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		if(isnew) {
			if("离职".equals(jcdaygxx.getPersonstate())) {
				return ;
			}
			EmployeeCmd.create(info.getIp(), info.getPass(), 
					new RegisterPersonInfo(jcdaygxx.getId(),jcdaygxx.getCardno(),jcdaygxx.getName()));
		}else {
			if("离职".equals(jcdaygxx.getPersonstate())) {
				EmployeeCmd.delete(info.getIp(), info.getPass(), jcdaygxx.getId());
				return ;
			}
			CallBackPersonInfo person = EmployeeCmd.find(info.getIp(), info.getPass(), jcdaygxx.getId());
			if(person==null) {
				EmployeeCmd.create(info.getIp(), info.getPass(), 
						new RegisterPersonInfo(jcdaygxx.getId(),jcdaygxx.getCardno(),jcdaygxx.getName()));
				// TODO 图片注册
			}else {
				EmployeeCmd.update(info.getIp(), info.getPass(), 
						new RegisterPersonInfo(jcdaygxx.getId(),jcdaygxx.getCardno(),jcdaygxx.getName()));
			}
		}
	}
	@Transactional(readOnly = false)
	public Photo registerImage(String personId,String sbId,String img,String faceId) {
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(sbId);
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		boolean flag = true;
		if(StringUtils.isEmpty(faceId)) {
			faceId = UUID.randomUUID().toString().replaceAll("-", "");			
			PhotoCmd.create(info.getIp(), info.getPass(), personId, faceId, img);
		}else {
			flag = false;
			PhotoCmd.update(info.getIp(), info.getPass(), personId, faceId, img);
		}
		Photo p = new Photo();
		List<SearchPhoto> list = PhotoCmd.find(info.getIp(), info.getPass(), personId);
		if(list!=null && !list.isEmpty()) {
			SearchPhoto photo = list.get(0);
			String path = photoMapper.isExist(photo.getFaceId());
			if(com.jeeplus.common.utils.StringUtils.isEmpty(path)) {
				p = new Photo(new Jcdaygxx(personId));
				p.setId(faceId);
				p.setFaceId(photo.getFaceId());
				p.setFeature(photo.getFeature());
				p.setFeatureKey(photo.getFeatureKey());
				p.setPath(ImageUtils.saveRegisterImage(photo.getPath()));
				p.setType("1");
				if(flag) {
					p.setIsNewRecord(true);
					p.preInsert();
					photoMapper.insert(p);
				}else {
					p.preUpdate();
					photoMapper.update(p);
				}
			}else {
				p.setFaceId(photo.getFaceId());
				p.setPath(path);
			}
			return p;
		}else {
			throw new RuntimeException("未获取到注册信息，请确保照片是正面照");
		}
	}
	/** 联机注册 */
	public void registerOnline(String sbId, String personId) {
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(sbId);
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		PhotoCmd.takeImg(info.getIp(), info.getPass(), personId);
	}
	/** 获取联机注册信息 */
	@Transactional(readOnly = false)
	public Photo getRegisterOnline(String sbId, String personId) {
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(sbId);
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		Photo p = new Photo(new Jcdaygxx(personId));
		List<SearchPhoto> list = PhotoCmd.find(info.getIp(), info.getPass(), personId);
		if(list!=null && !list.isEmpty()) {
			SearchPhoto photo = list.get(0);
			String path = photoMapper.isExist(photo.getFaceId());
			if(com.jeeplus.common.utils.StringUtils.isEmpty(path)) {
				p.setFaceId(photo.getFaceId());
				p.setFeature(photo.getFeature());
				p.setFeatureKey(photo.getFeatureKey());
				p.setPath(ImageUtils.saveRegisterImage(photo.getPath()));
				p.setType("0");
				p.setId(p.getFaceId());
				p.setIsNewRecord(true);
				p.preInsert();
				photoMapper.insert(p);
			}else {
				p.setFaceId(photo.getFaceId());
				p.setPath(path);
			}
			return p;
		}else {
			throw new RuntimeException("未获取到注册信息，请确保设备拍照成功。");
		}
	}
	@Transactional(readOnly = false)
	public void deleteImage(String sbId,String faceId) {
		photoMapper.deleteByFaceId(faceId);
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(sbId);
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		PhotoCmd.delete(info.getIp(), info.getPass(), faceId);
	}
	@Transactional(readOnly = false)
	public void delete(Jcdaygxx jcdaygxx) {
		if("在职".equals(jcdaygxx.getPersonstate())) {
			throw new RuntimeException("此员工为在职状态，不可删除.");
		}
		photoMapper.delete(new Photo(jcdaygxx));
		super.delete(jcdaygxx);
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(jcdaygxx.getSb().getId());
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		String rs = EmployeeCmd.delete(info.getIp(), info.getPass(), jcdaygxx.getId());
		if(!"0".equals(rs)) {
			throw new RuntimeException("删除失败.");
		}
	}
	
}