package com.example.demo.bean;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Paraminfo;

public class Paramgroupbean {
	
	private Long paramGroupId;
	private String paramGroupName;
	private String isdeleted;
	private List<Paraminfobean> infoList = new ArrayList();
	public Long getParamGroupId() {
		return paramGroupId;
	}
	public void setParamGroupId(Long paramGroupId) {
		this.paramGroupId = paramGroupId;
	}
	public String getParamGroupName() {
		return paramGroupName;
	}
	public void setParamGroupName(String paramGroupName) {
		this.paramGroupName = paramGroupName;
	}
	public String getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public List<Paraminfobean> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<Paraminfobean> infoList) {
		this.infoList = infoList;
	}
	public Paramgroupbean(Long paramGroupId, String paramGroupName, String isdeleted) {
		super();
		this.paramGroupId = paramGroupId;
		this.paramGroupName = paramGroupName;
		this.isdeleted = isdeleted;
	}
	public Paramgroupbean() {
		super();
	}

	

}
