/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ygxx_view.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 人员信息档案Entity
 * @author ww
 * @version 2019-04-17
 */
public class JcdaYgxxView extends DataEntity<JcdaYgxxView> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 员工编号
	private String name;		// 员工姓名
	private String cardno;		// 卡片编号
	
	public JcdaYgxxView() {
		super();
	}

	public JcdaYgxxView(String id){
		super(id);
	}

	@ExcelField(title="员工编号", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="员工姓名", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="卡片编号", align=2, sort=3)
	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	
}