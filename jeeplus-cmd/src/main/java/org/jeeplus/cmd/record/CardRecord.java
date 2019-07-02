package org.jeeplus.cmd.record;


import java.util.ArrayList;

import org.jeeplus.cmd.employee.PageInfo;
/**
 * 刷卡记录
 * @author Jin
 */
public class CardRecord {

	private PageInfo pageInfo;
	private ArrayList<Card> records;
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public ArrayList<Card> getRecords() {
		return records;
	}
	public void setRecords(ArrayList<Card> records) {
		this.records = records;
	}
}
