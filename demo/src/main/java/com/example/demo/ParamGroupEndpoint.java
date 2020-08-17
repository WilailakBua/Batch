
package com.example.demo;

import java.util.List;

import org.apache.tomcat.util.modeler.ParameterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;


import com.example.demo.ws.AddParamGroupRequest;
import com.example.demo.ws.AddParamGroupResponse;
import com.example.demo.ws.AddParamInfoRequest;
import com.example.demo.ws.AddParamInfoResponse;
import com.example.demo.ws.DeleteParamGroupRequest;
import com.example.demo.ws.DeleteParamGroupResponse;
import com.example.demo.ws.DeleteParamInfoRequest;
import com.example.demo.ws.DeleteParamInfoResponse;
import com.example.demo.ws.GetAllParamGroupRequest;
import com.example.demo.ws.GetAllParamGroupResponse;
import com.example.demo.ws.GetParamGroupRequest;
import com.example.demo.ws.GetParamGroupResponse;
import com.example.demo.ws.GetParamInfoRequest;
import com.example.demo.ws.GetParamInfoResponse;
import com.example.demo.ws.ParamGroups;
import com.example.demo.ws.ParamInfos;
import com.example.demo.ws.UpdateParamGroupRequest;
import com.example.demo.ws.UpdateParamGroupResponse;
import com.example.demo.ws.UpdateParamInfoRequest;
import com.example.demo.ws.UpdateParamInfoResponse;

import lombok.extern.slf4j.Slf4j;

import com.example.demo.repository.ParamgroupRepository;
import com.example.demo.service.ParamService;
import com.example.demo.entity.Paramgroup;
import com.example.demo.entity.Paraminfo;

@Slf4j
@Endpoint
public class ParamGroupEndpoint {
	private static final String NAMESPACE_URI = "http://example.com/demo/ws";
	
	private static final Logger log = LoggerFactory.getLogger(ParamGroupEndpoint.class);
	
	private ParamgroupRepository paramgroupRepository;
	@Autowired
	public ParamService paramService;
	@Autowired
	public ParamGroupEndpoint(ParamgroupRepository paramgroupRepository) 
	{
		this.paramgroupRepository = paramgroupRepository;
	}
//	Get ParamInfo
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getParamInfoRequest")
		@ResponsePayload
		public GetParamInfoResponse getParamInfoRequest(@RequestPayload GetParamInfoRequest request) 
		{
			List<ParamInfos> infos = paramService.transformInfoToParamInfo(request.getParamGroupId());
			GetParamInfoResponse response = new GetParamInfoResponse();
			
			response.getParamInfos().addAll(infos);
			
			return response;
		}
		
//		Get ParamGroup
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getParamGroupRequest")
		@ResponsePayload
		public GetParamGroupResponse getParamGroupRequest(@RequestPayload GetParamGroupRequest request) 
		{
			ParamGroups groups = paramService.transformGroupToParamGroup(request.getParamGroupId());
			GetParamGroupResponse responseGroup = new GetParamGroupResponse();
			responseGroup.getParamGroups().add(groups);
			return responseGroup;
		}
		
		
		
//		Excel


//		Get All ParamGroup
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllParamGroupRequest")
		@ResponsePayload
		public GetAllParamGroupResponse getAllParamGroupRequest(@RequestPayload GetAllParamGroupRequest request)
		{
			List<ParamGroups> groups = paramService.getAllGroup(request.getRequestMessage());
			GetAllParamGroupResponse responseAllGroup = new GetAllParamGroupResponse();
			responseAllGroup.getParamGroups().addAll(groups);
			return responseAllGroup;
		}
		
//		Delete ParamGroup
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteParamGroupRequest")
		@ResponsePayload
		public DeleteParamGroupResponse deleteParamGroupRequest(@RequestPayload DeleteParamGroupRequest request) 
		{
			ParamGroups groups = paramService.paramGroupsDeleteid(request.getParamGroupId());
			DeleteParamGroupResponse responseGroup = new DeleteParamGroupResponse();
			if(groups.getIsdeleted().equals("Y")) {
				responseGroup.setResponseMessage("Delete Success");
			}else {
				responseGroup.setResponseMessage("Delete Fail");
			}
			
			return responseGroup;
		}
		
//		Delete ParamInfo
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteParamInfoRequest")
		@ResponsePayload
		public DeleteParamInfoResponse deleteParamInfoRequest(@RequestPayload DeleteParamInfoRequest request) 
		{
			ParamInfos infos = paramService.paramInfodeleteId(request.getParamInfoId());
			DeleteParamInfoResponse resposeInfo = new DeleteParamInfoResponse();
			if(infos.getIsdeleted().equals("Y")) {
				resposeInfo.setResponseMessage("Delete Success");
			}else {
				resposeInfo.setResponseMessage("Delete Fail");
			}
			return resposeInfo;
		}
		
//		Add ParamGroup
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addParamGroupRequest")
		@ResponsePayload
		public AddParamGroupResponse addParamGroupRequest(@RequestPayload AddParamGroupRequest request)
		{
			AddParamGroupResponse response = new AddParamGroupResponse();
			ParamGroups groups = request.getAddParamGroup();
			Paramgroup group = new Paramgroup();
			group.setParamGroupId(new Long(groups.getParamGroupId()+""));
			group.setParamGroupName(groups.getParamGroupName());
			group.setIsdeleted(groups.getIsdeleted());
			try {
				paramService.savegroup(group);
				response.setResponseMessage("Insert Success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setResponseMessage("Insert Fail");
			}
			return response;
		}
		
//		Add ParamInfo
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addParamInfoRequest")
		@ResponsePayload
		public AddParamInfoResponse addParamInfoRequest(@RequestPayload AddParamInfoRequest request)
		{
			AddParamInfoResponse response = new AddParamInfoResponse();
			ParamInfos infos = request.getAddParamInfo();
			Paraminfo info = new Paraminfo();
			info.setParamInfoId(new Long(infos.getParamInfoId()+""));
			info.setParamInfoName(infos.getParamInfoName());
			info.setIsdeleted(infos.getIsdeleted());
			try {
				paramService.saveInfo(info, new Long(infos.getParamGroupId()+""));
				response.setResponseMessage("Insert Success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setResponseMessage("Insert Fail");
			}
			return response;	
		}
		
//		Update ParamGroup
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateParamGroupRequest")
		@ResponsePayload
		public UpdateParamGroupResponse updateParamGroupRequest(@RequestPayload UpdateParamGroupRequest request)
		{
			UpdateParamGroupResponse response = new UpdateParamGroupResponse();
			ParamGroups groups = request.getUpdateParamGroup();
			Paramgroup group = new Paramgroup();
			group.setParamGroupId(new Long(groups.getParamGroupId()+""));
			group.setParamGroupName(groups.getParamGroupName());
			group.setIsdeleted(groups.getIsdeleted());
			try {
				paramService.updatedata(group);
				response.setResponseMessage("Update Success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setResponseMessage("Update Fail");
			}
			return response;
		}
		
//		Update ParamInfo
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateParamInfoRequest")
		@ResponsePayload
		public UpdateParamInfoResponse updateParamInfoRequest(@RequestPayload UpdateParamInfoRequest request)
		{
			UpdateParamInfoResponse response = new UpdateParamInfoResponse();
			ParamInfos infos = request.getUpdateParamInfo();
			Paraminfo info = new Paraminfo();
			info.setParamInfoId(new Long(infos.getParamInfoId()+""));
			info.setParamInfoName(infos.getParamInfoName());
			info.setIsdeleted(infos.getIsdeleted());
			try {
				paramService.updateinfo(info);
				response.setResponseMessage("Update Success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setResponseMessage("Update Fail");
			}
			return response;	
		}

}
