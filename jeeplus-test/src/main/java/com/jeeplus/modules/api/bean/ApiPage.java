package com.jeeplus.modules.api.bean;

import java.util.List;
/**
 * 分页查询封装
 * @author Jin
 */
public class ApiPage<T> {
	/**
	 * 当前页码
	 */
	private int page;
	/**
	 * 总页数
	 */
	private int total;
	/**
	 * 总记录条数
	 */
	private int rows;
	/**
	 * 查询结果集合
	 */
	private List<T> data;
	
	public int getPage() {
		return page;
	}
	public ApiPage<T> setPage(int page) {
		this.page = page;
		return this;
	}
	public int getTotal() {
		return total;
	}
	public ApiPage<T> setTotal(int total) {
		this.total = total;
		return this;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public List<T> getData() {
		return data;
	}
	public ApiPage<T> setData(List<T> data) {
		this.data = data;
		return this;
	}
}
