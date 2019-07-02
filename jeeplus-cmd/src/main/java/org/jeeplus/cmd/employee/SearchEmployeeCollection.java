package org.jeeplus.cmd.employee;
/**
 * 分页查询人员信息集合
 * @author Jin
 *
 */

import java.util.ArrayList;

public class SearchEmployeeCollection {
	
	private PageInfo pageInfo;
	private ArrayList<CallBackPersonInfo> personInfos;
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public ArrayList<CallBackPersonInfo> getPersonInfos() {
		return personInfos;
	}
	public void setPersonInfos(ArrayList<CallBackPersonInfo> personInfos) {
		this.personInfos = personInfos;
	}
	
}
