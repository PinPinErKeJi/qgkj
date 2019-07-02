package com.jeeplus.modules.api.bean;

/**
 * 封装返回结果对象
 * @author Jin
 */
public class ApiResult<T> {

	private boolean success = true;// 服务器请求结果
	private int code = 0; // 请求是否相同
	private String message = "success";// 消息
	private T content;// 返回数据
	public boolean isSuccess() {
		return success;
	}
	public ApiResult<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}
	public int getCode() {
		return code;
	}
	public ApiResult<T> setCode(int code) {
		this.code = code;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ApiResult<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	public T getContent() {
		return content;
	}
	public ApiResult<T> setContent(T content) {
		this.content = content;
		return this;
	}
}
