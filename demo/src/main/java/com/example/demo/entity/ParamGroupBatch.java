package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParamGroupBatch {
	
	
	public String paramGroupCode;	

	public List<ParamInfoBatch> infoList;
	
	
	public String getParamGroupCode() {
		return paramGroupCode;
	}
	public void setParamGroupCode(String paramGroupCode) {
		this.paramGroupCode = paramGroupCode;
	}
	public List<ParamInfoBatch> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<ParamInfoBatch> infoList) {
		this.infoList = infoList;
	}

	
	
	
	

}
