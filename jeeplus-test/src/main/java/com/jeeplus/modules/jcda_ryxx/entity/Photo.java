package com.jeeplus.modules.jcda_ryxx.entity;

import com.jeeplus.core.persistence.DataEntity;

public class Photo extends DataEntity<Photo>{
	private static final long serialVersionUID = -1015298710647256810L;
	private Jcdaygxx person;
	private String faceId;
	private String path;
	private String feature;
	private String featureKey;
	private String type;// 0：联机注册 1：图片注册
	public Photo() {
		super();
	}

	public Photo(String id){
		super(id);
	}
	public Photo(Jcdaygxx jcdasbyhxx) {
		this.person = jcdasbyhxx;
	}
	public Jcdaygxx getPerson() {
		return person;
	}
	public void setPerson(Jcdaygxx person) {
		this.person = person;
	}
	public String getFaceId() {
		return faceId;
	}
	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
