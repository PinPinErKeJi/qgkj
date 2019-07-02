package org.jeeplus.cmd.record;

import java.util.ArrayList;

import org.jeeplus.cmd.employee.PageInfo;

/**
 * 脸部识别记录
 * @author Jin
 *
 */
public class PhotoRecord {
	
	private PageInfo pageInfo;
	private ArrayList<Photo> records;
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	public ArrayList<Photo> getRecords() {
		return records;
	}
	public void setRecords(ArrayList<Photo> records) {
		this.records = records;
	}
}
