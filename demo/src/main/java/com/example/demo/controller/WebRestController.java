package com.example.demo.controller;

import java.io.IOException;

import java.util.Date;
import java.util.List;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.ParamDAO;
import com.example.demo.bean.Paramgroupbean;
import com.example.demo.bean.Paraminfobean;
import com.example.demo.entity.ParamGroupBatch;
import com.example.demo.entity.ParamInfoBatch;
import com.example.demo.entity.Paramgroup;
import com.example.demo.entity.Paraminfo;
import com.example.demo.entity.ResponseModal;
import com.example.demo.repository.ParamInfoRepository;
import com.example.demo.repository.ParamgroupRepository;
import com.example.demo.service.ParamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.jasperreports.engine.JasperExportManager;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")

public class WebRestController {

	@Autowired
	private ParamService paramService;
	@Autowired
	private ParamDAO paramDAO;

//	SELECT GROUP Service
	@GetMapping("/findallgroup")
	public List<Paramgroupbean> findAllGroup() {
		List<Paramgroupbean> listParamGroupBean = paramService.findAllGroup();
		return listParamGroupBean;
	}
	
	
//	SELECT INFO
	@GetMapping("/findallinfo/{paramGroupId}")
	public List<Paraminfobean> findAllInfo(@PathVariable Long paramGroupId){
		List<Paraminfobean> listParamInfoBean = paramService.findAllInfo(paramGroupId);	
		return listParamInfoBean;
	}

//INSERT GROUP
	@PostMapping("/savegroup")
	public ResponseModal savegroup(@RequestBody Paramgroup group) {
		ResponseModal<Paramgroup> responseModal = paramService.savegroup(group);
		return responseModal;
	}
	
//	INSERT GROUP FROM BATCH
	@PostMapping("/saveparamgroup")
	public void saveParamGroup(@RequestBody String groupCode) throws ParseException, JsonMappingException, JsonProcessingException {
		System.out.println(groupCode);
		paramService.saveParamFromFile(groupCode);
		

		
	}


// INSERT INFO	 
	@PostMapping("/insertinfo")
	public ResponseModal<Paraminfo> saveInfo(@RequestBody Paraminfo info, @RequestParam("groupId") Long groupId) {
		ResponseModal<Paraminfo> responseModal = paramService.saveInfo(info, groupId);
		return responseModal;
	}

//UPDATE GROUP
	@PatchMapping("/update")
	public ResponseModal<Paramgroup> updatedata(@RequestBody Paramgroup group) {
		ResponseModal<Paramgroup> responseModal = paramService.updatedata(group);
		return responseModal;
	}

//UPDATE INFO
	@PatchMapping("/updateinfo")
	public ResponseModal<Paraminfo> updateinfo(@RequestBody Paraminfo info) {
		ResponseModal<Paraminfo> responseModal = paramService.updateinfo(info);
		return responseModal;
	}

//DELETE GROUP
	@DeleteMapping("/delete/{paramGroupId}")
	public ResponseModal<Paramgroup> deleteid(@PathVariable Long paramGroupId) {
		ResponseModal<Paramgroup> responseModal = paramService.deleteid(paramGroupId);
		return responseModal;
	}
	

//DELETE INFO
	@DeleteMapping("/deleteinfo/{paramInfoId}")
	public ResponseModal<Paraminfo> deleteinfoid(@PathVariable Long paramInfoId) {
		ResponseModal<Paraminfo> responseModal = paramService.deleteinfoid(paramInfoId);
		return responseModal;
	}

	@GetMapping("/angularjs")
	public String angularjs(Model model) {
		model.addAttribute("date", new Date().getTime());
		return "angularjs";
	}
	
//	excel
	@GetMapping("/exceldownload")
	public void exceldownload(HttpServletResponse response) throws IOException {
		paramService.excelParamGroups(response);
	}
	
//	PDF
	@GetMapping("/pdf")
	public void pdfdownload(HttpServletResponse response) throws Exception{
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition","attachment;filename=param.pdf"); 
		ServletOutputStream outstream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(paramService.printPDF(), outstream);
	}
	
	/*----------------------------------   DAO  ----------------------------------------*/
	
//	SELECT GROUP DAO
//	@GetMapping("/findallgroup")
//	public List<Paramgroup> findAllGroup() {
//		List<Paramgroup> listParamGroup = paramDAO.getParamGroup();
//		return listParamGroup;
//	}
	
//	SELECT INFO DAO
//	@GetMapping("/findallinfo/{paramGroupId}")
//	public List<Paraminfo> findAllInfo(@PathVariable Long paramGroupId){
//		List<Paraminfo> listParamInfo = paramDAO.getParamInfo(paramGroupId);
//		return listParamInfo;
//	}

//INSERT GROUP
//	@PostMapping("/savegroup")
//	public void savegroup(@RequestBody Paramgroup group) {
//		paramDAO.insertParamGroup(group);
//	}
//	INSERT INFO
//	@PostMapping("/insertinfo")
//	public void saveInfo(@RequestBody Paraminfo info, @RequestParam("groupId") Long groupId) {
//		paramDAO.insertParamInfo(info, groupId);
//	}
	
	
//UPDATE GROUP
//	@PatchMapping("/update")
//	public void updatedata(@RequestBody Paramgroup group) {
//		paramDAO.updateParamGroup(group);
//	}
	
//UPDATE INFO
//	@PatchMapping("/updateinfo")
//	public void updateinfo(@RequestBody Paraminfo info) {
//		paramDAO.updateParamInfo(info);
//	}
	
//DELETE GROUP DAO
//	@DeleteMapping("/delete/{paramGroupId}")
//	public void deleteid(@PathVariable Long paramGroupId) {	
//		paramDAO.deleteParamGroup(paramGroupId);
//	}

//DELETE INFO DAO
//	@DeleteMapping("/delete/{paramInfoId}")
//	public void deleteinfoid(@PathVariable Long paramInfoId) {	
//		paramDAO.deleteParamInfo(paramInfoId);
//	}
	

}
