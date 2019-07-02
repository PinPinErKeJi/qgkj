package org.jeeplus.cmd.employee;

public class PageInfo {
	/** 页码从0开始 */
	private Integer index;
	private Integer length;
	/** 总页数 */
	private Integer size;
	/** 总数据条数 */
	private Integer total;
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
