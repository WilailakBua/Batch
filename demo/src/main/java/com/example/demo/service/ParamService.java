package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.bean.Paramgroupbean;
import com.example.demo.bean.Paraminfobean;
import com.example.demo.entity.ExcelDataBean;
import com.example.demo.entity.ParamGroupBatch;
import com.example.demo.entity.ParamInfoBatch;
import com.example.demo.entity.Paramgroup;
import com.example.demo.entity.Paraminfo;
import com.example.demo.entity.ResponseModal;
import com.example.demo.repository.ParamInfoRepository;
import com.example.demo.repository.ParamgroupRepository;
import com.example.demo.ws.ParamGroups;
import com.example.demo.ws.ParamInfos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

import com.example.demo.*;
import com.example.demo.DAO.ParamDAO;

@Service
public class ParamService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ParamgroupRepository paramgroupRepository;
	@Autowired
	private ParamInfoRepository paramInfoRepository;
	@Autowired
	private ParamDAO paramDAO;
	@Autowired
	private DataSource ds;

//	SELECT GROUP
	@Transactional(readOnly = true)
	public List<Paramgroupbean> findAllGroup() {
//		List<Paramgroup> listParamGroup = paramgroupRepository.findAllByIsdeleted("N");
		List<Paramgroup> listParamGroup = null;
		List<Paramgroupbean> listParamGroupBean = null;
		try {
			listParamGroup = paramDAO.getParamGroup();
			listParamGroupBean = new ArrayList<>();
			transformGroupToBean(listParamGroupBean, listParamGroup);
			log.info("findAllGroup listParamGroupBean size " + listParamGroupBean.size());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return listParamGroupBean;
	}
	

	private void transformGroupToBean(List<Paramgroupbean> listParamGroupBean, List<Paramgroup> listParamGroup) {
		try {
			for (Paramgroup g : listParamGroup) {
				Paramgroupbean paramgroupbean = new Paramgroupbean(g.getParamGroupId(), g.getParamGroupName(),
						g.getIsdeleted());
				for (Paraminfo info : g.getInfoList()) {
					Paraminfobean paraminfobean = new Paraminfobean(info.getParamInfoId(), info.getParamInfoName(),
							info.getIsdeleted());
					paramgroupbean.getInfoList().add(paraminfobean);
				}
				listParamGroupBean.add(paramgroupbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	SELECT INFO
	@Transactional(readOnly = true)
	public List<Paraminfobean> findAllInfo(Long paramGroupId) {
//		List<Paraminfo> listParaminfo = paramInfoRepository.findAllByisdeletedAndGroup_ParamGroupId("N", paramGroupId);
		List<Paraminfo> listParaminfo = null;
		List<Paraminfobean> listParamInfoBean = null;
		try {
			listParaminfo = paramDAO.getParamInfo(paramGroupId);
			listParamInfoBean = new ArrayList<>();
			transformInfoToBean(listParamInfoBean, listParaminfo);
			log.info("findAllInfo listParamInfoBean size " + listParamInfoBean.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listParamInfoBean;
	}

	private void transformInfoToBean(List<Paraminfobean> listParamInfoBean, List<Paraminfo> listParaminfo) {
		for (Paraminfo info : listParaminfo) {
			Paraminfobean paraminfobean = new Paraminfobean(info.getParamInfoId(), info.getParamInfoName(),
					info.getIsdeleted());
			listParamInfoBean.add(paraminfobean);
		}
	}

//INSERT GROUP
	@Transactional(rollbackFor = Exception.class)
	public ResponseModal<Paramgroup> savegroup(Paramgroup group) {
		ResponseModal<Paramgroup> responsemodal = new ResponseModal<>();
		try {
			if (group == null || StringUtils.isEmpty(group.getParamGroupName())) {
				log.info("Undefined Group Name");
				responsemodal.setResponseMessage("Undefined name");
				responsemodal.setResponseCode("500");
			} else {
	//			paramgroupRepository.save(group);
				paramDAO.insertParamGroup(group);
				log.info("savegroup " + group.getParamGroupName());
				responsemodal.setResponseMessage("Success");
				responsemodal.setResponseCode("200");
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsemodal.setResponseMessage("fail");
			responsemodal.setResponseCode("500");
		}
		return responsemodal;
	}

// INSERT INFO
	@Transactional(rollbackFor = Exception.class)
	public ResponseModal<Paraminfo>  saveInfo(Paraminfo info, Long groupId) {
		ResponseModal<Paraminfo> responsemodal = new ResponseModal<>();
//		Paramgroup group = paramgroupRepository.getOne(groupId);
		try {
			if (groupId == null || StringUtils.isEmpty(info.getParamInfoName())) {
				log.info("Undefined Group Name ");
				responsemodal.setResponseMessage("Undefined name");
				responsemodal.setResponseCode("500");
			} else {
				paramDAO.insertParamInfo(info, groupId);
//				info.setGroup(group);
//				paramInfoRepository.save(info);
				log.info("saveInfo " + info.equals(info));
				responsemodal.setResponseMessage("Success");
				responsemodal.setResponseCode("200");
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsemodal.setResponseMessage("fail");
			responsemodal.setResponseCode("500");
		}
		return responsemodal;


	}

//UPDATE GROUP
	@Transactional(rollbackFor = Exception.class)
	public ResponseModal<Paramgroup>  updatedata(Paramgroup group) {
		ResponseModal<Paramgroup> responsemodal = new ResponseModal<>();
		try {
//			Paramgroup paramGroup = paramgroupRepository.getOne(group.getParamGroupId());
			if (group.getParamGroupId() == null || StringUtils.isEmpty(group.getParamGroupName())) {
				log.info("Undefined Group Name");
				responsemodal.setResponseMessage("Undefined name");
				responsemodal.setResponseCode("500");
			} else {
//				paramGroup.setParamGroupName(group.getParamGroupName());
//				paramgroupRepository.save(paramGroup);
//				log.info("savegroup " + paramGroup.getParamGroupName());
				paramDAO.updateParamGroup(group);
				responsemodal.setResponseMessage("Success");
				responsemodal.setResponseCode("200");
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsemodal.setResponseMessage("fail");
			responsemodal.setResponseCode("500");
		}
		return responsemodal;

	}

//UPDATE INFO
	@Transactional(rollbackFor = Exception.class)
	public ResponseModal<Paraminfo>  updateinfo(Paraminfo info) {
		ResponseModal<Paraminfo> responsemodal = new ResponseModal<>();
		try {
//			Paraminfo paramInfo = paramInfoRepository.getOne(group.getParamInfoId());
			if (info == null || StringUtils.isEmpty(info.getParamInfoName())) {
				log.info("Undefined Info Name");
				responsemodal.setResponseMessage("Undefined name");
				responsemodal.setResponseCode("500");
			} else {
//				paramInfo.setParamInfoName(info.getParamInfoName());
//				paramInfoRepository.save(paramInfo);
				paramDAO.updateParamInfo(info);
				responsemodal.setResponseMessage("Success");
				responsemodal.setResponseCode("200");
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsemodal.setResponseMessage("fail");
			responsemodal.setResponseCode("500");
		}
		return responsemodal;

	}

//DELETE GROUP
	@Transactional(rollbackFor = Exception.class)
	public ResponseModal<Paramgroup>  deleteid(Long paramGroupId) {
//		Paramgroup group = paramgroupRepository.getOne(paramGroupId);
//		group.setIsdeleted("Y");
//		paramgroupRepository.save(group);
		ResponseModal<Paramgroup> responsemodal = new ResponseModal<>();
		try {
			paramDAO.deleteParamGroup(paramGroupId);
			responsemodal.setResponseMessage("Success");
			responsemodal.setResponseCode("200");
		} catch (Exception e) {
			e.printStackTrace();
			responsemodal.setResponseMessage("fail");
			responsemodal.setResponseCode("500");
		}
		return responsemodal;
	}

//DELETE INFO
	@Transactional(rollbackFor = Exception.class)
	public ResponseModal<Paraminfo>  deleteinfoid(Long paramInfoId) {
//		Paraminfo info = paramInfoRepository.getOne(paramInfoId);
//		info.setIsdeleted("Y");
//		paramInfoRepository.save(info);
		ResponseModal<Paraminfo> responsemodal = new ResponseModal<>();
		try {
			paramDAO.deleteParamInfo(paramInfoId);  
		} catch (Exception e) {
			e.printStackTrace();
			responsemodal.setResponseMessage("fail");
			responsemodal.setResponseCode("500");
		}
		return responsemodal;
	}

//	TRANSFORM GROUP
	public ParamGroups transformGroupToParamGroup(int paramGroupId) {
		Paramgroup listgroup = paramgroupRepository.findByParamGroupId((long) paramGroupId);

		ParamGroups group = new ParamGroups();
		group.setParamGroupId(Integer.parseInt(listgroup.getParamGroupId() + ""));
		group.setParamGroupName(listgroup.getParamGroupName());
		group.setIsdeleted(listgroup.getIsdeleted());

		return group;
	}

//	Get All ParamGroup
	public List<ParamGroups> getAllGroup(String msg) {
		List<Paramgroup> listGroup = paramgroupRepository.findAllByIsdeleted("N");
		List<ParamGroups> newGroup = new ArrayList<>();
		for (int i = 0; i < listGroup.size(); i++) {
			ParamGroups paramGroups = new ParamGroups();
			paramGroups.setParamGroupId(Integer.parseInt(listGroup.get(i).getParamGroupId() + ""));
			paramGroups.setParamGroupName(listGroup.get(i).getParamGroupName());
			paramGroups.setIsdeleted(listGroup.get(i).getIsdeleted());
			newGroup.add(paramGroups);
		}
		return newGroup;
	}
	
//	Excel
	public void excelParamGroups(HttpServletResponse response) throws IOException {
		List<String> header = null;
		header = Arrays.asList(
				"Id",
				"Name",
				"IsDelete");
		List<Paramgroup> listGroup = paramgroupRepository.findAllByIsdeleted("N");
		List<ExcelDataBean> dataBeans = new ArrayList<ExcelDataBean>();
		for (int i = 0; i < listGroup.size(); i++) {
			ExcelDataBean row = new ExcelDataBean();
			List<Object> values = new ArrayList<Object>();
			List<Class> types = new ArrayList<Class>();
			
			values.add(listGroup.get(i).getParamGroupId());
			types.add(String.class);
			
			values.add(listGroup.get(i).getParamGroupName());
			types.add(String.class);
			
			values.add(listGroup.get(i).getIsdeleted());
			types.add(String.class);
			
			row.setValues(values);
			row.setTypes(types);
			
			dataBeans.add(row);			
		}
		
		XSSFWorkbook workbook = generatExcelData(header, dataBeans, "header", "ParamGroup");

        Calendar calendar = Calendar.getInstance();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=location-document-"+calendar.getTime().getTime()+".xlsx");
        OutputStream out = null;
        out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
	}
	
	public XSSFWorkbook generatExcelData(List<String> headers, List<ExcelDataBean> datas, String sheetName, String titleName) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        int titleRowIndex       = 2;
        int titleColumnIndex    = 3;
        int startRowIndex       = 4;

        Row rowTitle = sheet.createRow(titleRowIndex);
        Cell cellTitle = rowTitle.createCell(titleColumnIndex);
        cellTitle.setCellValue(titleName);

        Row rowHeader = sheet.createRow(startRowIndex++);
        for (int i = 0; i < headers.size(); i++) {
            Cell cellHeader = rowHeader.createCell(i);
            cellHeader.setCellValue(headers.get(i));
        }
        for (int i = 0; i < datas.size(); i++) {
            Row rowData = sheet.createRow(startRowIndex++);
            for (int j = 0; j < datas.get(i).getValues().size(); j++) {
                Class type = datas.get(i).getTypes().get(j);
                Cell cellData = rowData.createCell(j);

                Object xValue = datas.get(i).getValues().get(j);
                if(type == Long.class) {
                    if(null != xValue) {
                        Long v = (Long) xValue;
                        CellStyle cellStyle = cellData.getCellStyle();
                        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                        cellData.setCellValue(v);
                    }
                }
                if(type == BigDecimal.class) {
                    if(null != xValue) {
                        BigDecimal v = (BigDecimal) xValue;
                        CellStyle cellStyle = cellData.getCellStyle();
                        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
                        cellData.setCellValue(v.toPlainString());
                    }
                }
                if(type == String.class) {
                    if(null != xValue) {
                        String v = String.valueOf(xValue);
                        cellData.setCellValue(v);
                    }
                }

                if(type == Integer.class) {
                    if(null != xValue) {
                        Integer v = (Integer) xValue;
                        cellData.setCellValue(v);
                    }
                }
            }
        }

        return workbook;
    }
	
	
//	PDF
	public JasperPrint printPDF() throws JRException, SQLException {

//		Use the JDBC DataSource.
		Connection con = ds.getConnection();
		
		JasperPrint jasperPrint = null;
		InputStream inputStream = null;
		try {
			//Locale locale = new Locale("th", "TH");
//			log.info("Report File : " +reportFile);
			inputStream = this.getClass().getClassLoader().getResourceAsStream("static/pdf/report4.jasper");
			jasperPrint = JasperFillManager.fillReport(inputStream, null, con);
			inputStream.close();
		} 
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return jasperPrint;
	}
	
	
	
	
	
	

//	TRANSFORM INFO 
	public List<ParamInfos> transformInfoToParamInfo(int paraminfo) {
		List<Paraminfo> list = paramInfoRepository.findAllByisdeletedAndGroup_ParamGroupId("N", (long) paraminfo);
		List<ParamInfos> resultList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ParamInfos paraminfo2 = new ParamInfos();
			paraminfo2.setParamInfoId(Integer.parseInt(list.get(i).getParamInfoId() + ""));
			paraminfo2.setParamInfoName(list.get(i).getParamInfoName());
			paraminfo2.setIsdeleted(list.get(i).getIsdeleted());
			resultList.add(paraminfo2);
		}
		return resultList;
	}

	// DELETE GROUPS
	@Transactional(rollbackFor = Exception.class)
	public ParamGroups paramGroupsDeleteid(int paramGroupId) {
		Paramgroup group = paramgroupRepository.getOne((long) paramGroupId);
		group.setIsdeleted("Y");
		ParamGroups paramGroups = new ParamGroups();
		paramGroups.setIsdeleted(group.getIsdeleted());
		paramgroupRepository.save(group);

		return paramGroups;
	}

	// DELETE INFOS
	@Transactional(rollbackFor = Exception.class)
	public ParamInfos paramInfodeleteId(int paramInfoId) {
		Paraminfo info = paramInfoRepository.getOne((long) paramInfoId);
		info.setIsdeleted("Y");
		ParamInfos paramInfos = new ParamInfos();
		paramInfos.setIsdeleted(info.getIsdeleted());
		paramInfoRepository.save(info);

		return paramInfos;
	}
	
	
	
//	INSERT GROUP FROM FILE //	INSERT INFO FROM FILE
	
	public void saveParamFromFile(String groupCode) throws JsonMappingException, JsonProcessingException {
		 ObjectMapper mapper = new ObjectMapper();
	      
	     ParamGroupBatch[] g = mapper.readValue(groupCode, ParamGroupBatch[].class);
	     Paramgroup paramgroup = new Paramgroup();
	     Paraminfo paraminfo = new Paraminfo();
	     Long paramGroupId;
	     Long id;
		 
		  for(ParamGroupBatch groups : g) {
			  System.out.println(groups.getParamGroupCode());
			  
			 paramgroup.setParamGroupName(groups.getParamGroupCode());
			 paramgroup.setIsdeleted("N");
			 
			 id = paramDAO.insertParamGroup(paramgroup);
			 System.out.println("id group : " + id);

			 for(ParamInfoBatch infos : groups.getInfoList()) {
				 System.out.println("group  " + groups.getParamGroupCode());
				 System.out.println("group inofs  " + infos.getParamGroupCode());

				 paraminfo.setParamInfoName(infos.getParamInfoCode()); 
				 paraminfo.setParamInfoValue(infos.getParamInfoValue());
				 paraminfo.setParamInfoValue2(infos.getParamInfoValue2());
				 paraminfo.setIsdeleted("N");
				
				 paramDAO.insertParamInfo(paraminfo, id);
				
			 }
		  }  
	}



}
