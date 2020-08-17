package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity


public class Paraminfo {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long paramInfoId;
	private String paramInfoName;
	private String isdeleted;
	private String paramInfoValue;
	private String paramInfoValue2;

	public static final String PARAM_INFO_ID = "param_info_id";
	public static final String PARAM_INFO_NAME = "param_info_name";
	public static final String PARAM_INFO_IS_DELETED = "isdeleted";
	
	
	 @ManyToOne
	 @JoinColumn(name="param_group_id", nullable=false)
	private Paramgroup group;

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

	public Paramgroup getGroup() {
		return group;
	}

	public void setGroup(Paramgroup group) {
		this.group = group;
	}

	public String getParamInfoValue() {
		return paramInfoValue;
	}

	public void setParamInfoValue(String paramInfoValue) {
		this.paramInfoValue = paramInfoValue;
	}

	public String getParamInfoValue2() {
		return paramInfoValue2;
	}

	public void setParamInfoValue2(String paramInfoValue2) {
		this.paramInfoValue2 = paramInfoValue2;
	}
	
	
	 
	
	 
	
	

}
