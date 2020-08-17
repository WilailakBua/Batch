package com.example.demo.bean;

import com.example.demo.entity.Paramgroup;

public class Paraminfobean {

	private Long paramInfoId;
	private String paramInfoName;
	private String isdeleted;
	private Paramgroupbean group;
	public Long getParamInfoId() {
		return paramInfoId;
	}
	public void setParamInfoId(Long paramInfoId) {
		this.paramInfoId = paramInfoId;
	}
	public String getParamInfoName() {
		return paramInfoName;
	}
	public void setParamInfoName(String paramInfoName) {
		this.paramInfoName = paramInfoName;
	}
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public Paramgroupbean getGroup() {
		return group;
	}
	public void setGroup(Paramgroupbean group) {
		this.group = group;
	}
	public Paraminfobean(Long paramInfoId, String paramInfoName, String isdeleted) {
		super();
		this.paramInfoId = paramInfoId;
		this.paramInfoName = paramInfoName;
		this.isdeleted = isdeleted;
	}
	public Paraminfobean() {
		super();
	}
	
	
	
}
