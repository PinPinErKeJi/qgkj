package org.jeeplus.cmd.photo;

public class SearchPhoto {

	/** 员工id */
	private String personId;
	/** 图片id */
	private String faceId;
	/** 特征码 */
	private String feature;
	/** 特征码密钥 */
	private String featureKey;
	/** 注册照片存储在设备内的路径 */
	private String path;
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getFaceId() {
		return faceId;
	}
	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getFeatureKey() {
		return featureKey;
	}
	public void setFeatureKey(String featureKey) {
		this.featureKey = featureKey;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
