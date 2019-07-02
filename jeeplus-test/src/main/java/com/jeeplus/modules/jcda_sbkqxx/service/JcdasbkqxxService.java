/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbkqxx.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jeeplus.cmd.employee.EmployeeCmd;
import org.jeeplus.cmd.record.CardRecord;
import org.jeeplus.cmd.record.PhotoRecord;
import org.jeeplus.cmd.record.RecordCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.api.bean.JcdasbkqxxBean;
import com.jeeplus.modules.jcda_banci.entity.BanCiBean;
import com.jeeplus.modules.jcda_banci.service.JcdabanciService;
import com.jeeplus.modules.jcda_gxsz.entity.Jcdagxsz;
import com.jeeplus.modules.jcda_gxsz.mapper.JcdagxszMapper;
import com.jeeplus.modules.jcda_jjrsz.mapper.JcdajjrszMapper;
import com.jeeplus.modules.jcda_ryxx.entity.PersonInfo;
import com.jeeplus.modules.jcda_ryxx.mapper.JcdaygxxMapper;
import com.jeeplus.modules.jcda_sbgl.entity.CmdBaseInfo;
import com.jeeplus.modules.jcda_sbkqxx.entity.ClockInOut;
import com.jeeplus.modules.jcda_sbkqxx.entity.DateBean;
import com.jeeplus.modules.jcda_sbkqxx.entity.JcdaSbmcView1;
import com.jeeplus.modules.jcda_sbkqxx.entity.Jcdasbkqxx;
import com.jeeplus.modules.jcda_sbkqxx.mapper.JcdasbkqxxMapper;
import com.jeeplus.modules.jcda_ygxx_view.entity.JcdaYgxxView;
import com.jeeplus.modules.kqgl_rykqb.entity.Kqglrykqb;
import com.jeeplus.modules.kqgl_rykqb.mapper.KqglrykqbMapper;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.utils.BusinessUtils;
import com.jeeplus.modules.utils.ImageUtils;


/**
 * 设备考勤信息Service
 * @author ww
 * @version 2019-04-08
 */

@Service
@Transactional(readOnly = true)
public class JcdasbkqxxService extends CrudService<JcdasbkqxxMapper, Jcdasbkqxx> {

	@Autowired
	private JcdaygxxMapper jcdasbyhxxMapper;
	@Autowired
	private KqglrykqbMapper kqglrykqbMapper;
	@Autowired
	private JcdaygxxMapper jcdaygxxMapper;
	@Autowired
	private JcdajjrszMapper jcdajjrszMapper;
	@Autowired
	private JcdabanciService jcdabanciService;
	@Autowired
	private JcdagxszMapper jcdagxszMapper;

	public Jcdasbkqxx get(String id) {
		return super.get(id);
	}

	public List<Jcdasbkqxx> findList(Jcdasbkqxx jcdasbkqxx) {
		return super.findList(jcdasbkqxx);
	}

	public Page<Jcdasbkqxx> findPage(Page<Jcdasbkqxx> page, Jcdasbkqxx jcdasbkqxx) {
		return super.findPage(page, jcdasbkqxx);
	}

	@Transactional(readOnly = false)
	public void save(Jcdasbkqxx jcdasbkqxx) {
		super.save(jcdasbkqxx);
	}

	@Transactional(readOnly = false)
	public void delete(Jcdasbkqxx jcdasbkqxx) {
		super.delete(jcdasbkqxx);
	}
	
	/*
	 * 查询数据设备考勤信息
	 */
	public List<JcdasbkqxxBean> findAtendance(){
		return mapper.findAtendance();
	}
	/*
	 * 修改设备考勤同步数据状态
	 */
	@Transactional(readOnly = false)
	public void updateSbkState(String id) {
		mapper.updateSbkState(id);
		
	}
	public int rowCount(){
		return mapper.rowCount();
	}
	/*
	 * 提供对外服务数据App
	 */
	public List<JcdasbkqxxBean> findApp(String sb_id,String code,Date beginDate,Date endDate,int page, int pageSize){
		int from = (page-1) * pageSize;
		return mapper.findApp(sb_id,code,beginDate,endDate,from,pageSize);
	}
	@Transactional(readOnly = false)
	public void synchronize(String sbId,String startTime,String endTime) {
		CmdBaseInfo info = BusinessUtils.getCmdBaseInfo(sbId);
		if(info==null||!info.isabled()) {
			throw new RuntimeException("请维护该设备的基础信息(设备IP和设备密码)");
		}
		if("0".equals(startTime)) {
			Date date = mapper.getLastSynchronize(sbId);
			if(date != null) {
				startTime = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
			}
		}
		if("0".equals(endTime)) {
			endTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
		/** 刷卡 */
		CardRecord cardRecord = 
				RecordCmd.findICRecords(info.getIp(), info.getPass(), "-1", 800, 0, startTime, endTime);
		synchCard(cardRecord, sbId,info.getDeviceKey());
		int csize = cardRecord.getPageInfo().getSize();
		if(csize>1) {
			for (int i = 1; i < csize; i++) {
				cardRecord = 
						RecordCmd.findICRecords(info.getIp(), info.getPass(), "-1", 800, i, startTime, endTime);
				synchCard(cardRecord, sbId,info.getDeviceKey());
			}
		}
		/** 刷脸 */
		PhotoRecord photoRecord = RecordCmd.findRecords(info.getIp(), info.getPass(), "-1", 800, 0, startTime, endTime);
		synchPhoto(photoRecord, sbId,info);
		int psize = cardRecord.getPageInfo().getSize();
		if(psize>1) {
			for (int i = 1; i < psize; i++) {
				photoRecord = RecordCmd.findRecords(info.getIp(), info.getPass(), "-1", 800, i, startTime, endTime);
				synchPhoto(photoRecord, sbId,info);
			}
		}
	}

	private void synchCard(CardRecord cardRecord, String sbId,String sbNo) {
		cardRecord.getRecords().forEach(e->{
			if(0==mapper.isExist(e.getId())) {
				Jcdasbkqxx jcdasbkqxx = new Jcdasbkqxx(new JcdaSbmcView1(sbId));
				jcdasbkqxx.setId(e.getId());
				jcdasbkqxx.setName(e.getName());
				jcdasbkqxx.setDate(new Date(e.getTime()));
				jcdasbkqxx.setZdy1(e.getPersonId());
				jcdasbkqxx.setZdy2(e.getType().toString());
				jcdasbkqxx.setZdy3("刷卡识别");
				jcdasbkqxx.setSbxlh(sbNo);
				try {
					jcdasbkqxx.setIsNewRecord(true);
					jcdasbkqxx.preInsert();
					mapper.insert(jcdasbkqxx);
				} catch (Exception e2) {}
			}
		});
	}

	private void synchPhoto(PhotoRecord photoRecord, String sbId,CmdBaseInfo info) {
		photoRecord.getRecords().forEach(e->{
			if(0==mapper.isExist(e.getId())) {
				Jcdasbkqxx jcdasbkqxx = new Jcdasbkqxx(new JcdaSbmcView1(sbId));
				jcdasbkqxx.setId(e.getId());
				jcdasbkqxx.setName(jcdasbyhxxMapper.getName(e.getPersonId()));
				jcdasbkqxx.setDate(new Date(e.getTime()));
				jcdasbkqxx.setZdy1(e.getPersonId());
				jcdasbkqxx.setZdy2(e.getType().toString());
				jcdasbkqxx.setZdy3("人脸识别");
				jcdasbkqxx.setSbxlh(info.getDeviceKey());
				jcdasbkqxx.setZdy4(ImageUtils.saveImage(e.getPath()));
				try {
					if(StringUtils.isEmpty(jcdasbkqxx.getName())) {
						jcdasbkqxx.setName(EmployeeCmd.find(info.getIp(), info.getPass(), e.getPersonId()).getName());
					}
				} catch (Exception e2) {
				}
				jcdasbkqxx.setIsNewRecord(true);
				jcdasbkqxx.preInsert();
				mapper.insert(jcdasbkqxx);
			}
		});
	}

	@Transactional(readOnly=false)
	public void calculate(String startTime,String endTime) {
		boolean isBefore = false;
		if(StringUtils.isEmpty(startTime)) {
			startTime = kqglrykqbMapper.getLastYMD();
		}
		Date start = null, end = null;
		if(StringUtils.isEmpty(startTime)) {
			start = mapper.getOld();
			isBefore = true;
			if(start==null) {
				throw new RuntimeException("考勤无记录！请同步");
			}
		}else {
			start = DateUtils.parseDate(startTime);
		}
		if(StringUtils.isEmpty(endTime)) {
			end = new Date();
		}else {
			end = DateUtils.parseDate(endTime);
		}
		// 设置同步时间段
		List<DateBean> dateBeans = getDateBeanBetween(start, end,isBefore);
		if(dateBeans.isEmpty()) {
			throw new RuntimeException("今日已同步一次");
		}
		// 班次
		HashMap<String, BanCiBean> bcmap = jcdabanciService.getBancis();
		if(bcmap == null) {
			throw new RuntimeException("班次未设置，请设置");
		}
		List<PersonInfo> persons = jcdaygxxMapper.findAllJob();
		// 查询员工在职员工
		if(persons==null||persons.isEmpty()) {
			throw new RuntimeException("暂无在职人员");
		}
		// 公休表
		Jcdagxsz gxsz = jcdagxszMapper.getLast();
		// 计算开始
		dateBeans.forEach(d->{
			// 判断是否是节假日
			if(jcdajjrszMapper.isInHoliday(d.getYmd())>0) {
				persons.forEach(p->{
					rest(p, d, "假日");
				});
			}else {
				// 是否公休
				if("星期六".equals(d.getE())||"星期日".equals(d.getE())) {// 是否进入双休
					List<BanCiBean> bcs = gxsz.getDuty(d.getE());
					if(gxsz==null||bcs.isEmpty()) {// 休
						persons.forEach(p->{
							rest(p, d, "休");
						});
					}else {// 单休
						for (BanCiBean bc : bcs) {
							persons.forEach(p->{
								clockInOut(p, d, bc, bcmap);
							});
						}
					}
				}else {//正常上班
					persons.forEach(p->{
						clockInOut(p, d, null, bcmap);
					});
				}
			}
		});
	}

	private void rest(PersonInfo p,DateBean d,String restType) {
		Kqglrykqb kqglrykqb = new Kqglrykqb();
		kqglrykqb.setJg(new Office(p.getOffice()));
		kqglrykqb.setXm(new JcdaYgxxView(p.getId()));
		kqglrykqb.setCode(p.getCode());
		kqglrykqb.setKqnyr(d.getYmd());
		kqglrykqb.setXq(d.getE());
		kqglrykqb.setZt(restType);
		kqglrykqb.setTs(0.00);
		kqglrykqb.setSc(0.00);
		kqglrykqb.setIsNewRecord(true);
		kqglrykqb.preInsert();
		kqglrykqb.setId(p.getId()+"-"+d.getYmd());
		kqglrykqbMapper.insert(kqglrykqb);
	}
	/** 考勤打卡 */
	private void clockInOut(PersonInfo p,DateBean d,BanCiBean bc,HashMap<String, BanCiBean> bcmap) {
		Kqglrykqb kqglrykqb = new Kqglrykqb();
		kqglrykqb.setJg(new Office(p.getOffice()));
		kqglrykqb.setXm(new JcdaYgxxView(p.getId()));
		kqglrykqb.setCode(p.getCode());
		kqglrykqb.setKqnyr(d.getYmd());
		kqglrykqb.setXq(d.getE());
		kqglrykqb.setBb(p.getBc());
		if(bc==null) {
			bc = bcmap.get(p.getBc());
		}
		ClockInOut clock = mapper.getClockIn(d.getYmd()+" "+bc.getOnestart(), d.getYmd()+" "+bc.getOneend(), p.getId());
		if(clock==null) {
			kqglrykqb.setTs(0.00);
			kqglrykqb.setSc(0.00);
			kqglrykqb.setZt("缺勤");
			kqglrykqb.setKqsj(bc.getOnestart());
		}else {
			kqglrykqb.setZt("正常");
			kqglrykqb.setTs(bc.getJts());
			kqglrykqb.setSc(bc.getJxs());
			kqglrykqb.setSbmc(clock.getSbName());
			kqglrykqb.setSbxlh(clock.getSbxlh());
			kqglrykqb.setKqsj(DateUtils.formatDate(clock.getDate(), "HH:mm:ss"));
			kqglrykqb.setZp(clock.getImage());
		}
		kqglrykqb.setIsNewRecord(true);
		kqglrykqb.preInsert();
		kqglrykqb.setId(p.getId()+"-"+d.getYmd()+"-0"+bc.getAmap());
		kqglrykqbMapper.insert(kqglrykqb);
		clock = mapper.getClockIn(d.getYmd()+" "+bc.getTwostart(), d.getYmd()+" "+bc.getTwoend(), p.getId());
		if(clock==null) {
			kqglrykqb.setTs(0.00);
			kqglrykqb.setSc(0.00);
			kqglrykqb.setZt("缺勤");
			kqglrykqb.setKqsj(bc.getTwostart());
		}else {
			kqglrykqb.setZt("正常");
			kqglrykqb.setTs(bc.getJts());
			kqglrykqb.setSc(bc.getJxs());
			kqglrykqb.setSbmc(clock.getSbName());
			kqglrykqb.setSbxlh(clock.getSbxlh());
			kqglrykqb.setKqsj(DateUtils.formatDate(clock.getDate(), "HH:mm:ss"));
			kqglrykqb.setZp(clock.getImage());
		}
		kqglrykqb.setId(p.getId()+"-"+d.getYmd()+"-1"+bc.getAmap());
		kqglrykqbMapper.insert(kqglrykqb);
	}
	
	private List<DateBean> getDateBeanBetween(Date start,Date end,boolean isBefore){
		List<DateBean> dateBeans = Lists.newArrayList();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);
		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		DateBean bean = null;
		if(!isBefore) {
			tempStart.add(Calendar.DAY_OF_YEAR, 1);			
		}
		while(tempStart.before(tempEnd)/*||tempStart.equals(tempEnd)*/) {
			bean = new DateBean();
			bean.setYmd(DateUtils.formatDate(tempStart.getTime(), "yyyy-MM-dd"));
			bean.setE(DateUtils.getWeek(tempStart.getTime()));
			dateBeans.add(bean);
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		return dateBeans;
	}
}