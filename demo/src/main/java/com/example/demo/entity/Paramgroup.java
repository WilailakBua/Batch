package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity

public class Paramgroup {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long paramGroupId;
	private String paramGroupName;
	private String isdeleted;
	
	public static final String PARAM_GROUP_ID = "param_group_id";
	public static final String PARAM_GROUP_NAME = "param_group_name";
	public static final String PARAM_GROUP_IS_DELETED = "isdeleted";
	
	@OneToMany(mappedBy="group")
	private List<Paraminfo> infoList = new ArrayList();

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

	public List<Paraminfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Paraminfo> infoList) {
		this.infoList = infoList;
	}


	
	
	
	

}