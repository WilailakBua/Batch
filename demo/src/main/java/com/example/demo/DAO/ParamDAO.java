package com.example.demo.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Paramgroup;
import com.example.demo.entity.Paraminfo;

@Repository
public class ParamDAO {

	@Autowired
	DataSource dataSource;
	
	private final Logger log = Logger.getLogger(getClass());
	
	private JdbcTemplate jdbcTemplate;  

//	SELECT PARAMGROUP
	public List<Paramgroup> getParamGroup() {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		final String paramGroupQuery = "select * from paramgroup where isdeleted = 'N'";
		List<Paramgroup> paramGroupList = null;
		
		try {
			paramGroupList = jdbcTemplate.query(paramGroupQuery, new RowMapper<Paramgroup>() {
				@Override
				public Paramgroup mapRow(ResultSet resultSet, int i) throws SQLException {
					Paramgroup p = new Paramgroup();
					p.setParamGroupId(resultSet.getLong(Paramgroup.PARAM_GROUP_ID));
					p.setParamGroupName(resultSet.getString(Paramgroup.PARAM_GROUP_NAME));
					p.setIsdeleted(resultSet.getString(Paramgroup.PARAM_GROUP_IS_DELETED));
					
					return p;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return paramGroupList;
	}
	
//	SELECT PARAMINFO
	public List<Paraminfo> getParamInfo(Long paramGroupId) {
//		log.info("ParamGroupId -----------------------------------------   " + paramGroupId);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		final String paramInfoQuery = "select * from paraminfo where param_group_id = ?  and isdeleted = 'N'";
		List<Paraminfo> paramInfoList = null;
		
		try {
			paramInfoList = jdbcTemplate.query(paramInfoQuery, new Object[]{paramGroupId}, new RowMapper<Paraminfo>(){
				@Override
				public Paraminfo mapRow(ResultSet rs, int i)throws SQLException {
					Paraminfo paraminfo = new Paraminfo();
					paraminfo.setParamInfoId(rs.getLong(Paraminfo.PARAM_INFO_ID));
					paraminfo.setParamInfoName(rs.getString(Paraminfo.PARAM_INFO_NAME));
					paraminfo.setIsdeleted(rs.getString(Paraminfo.PARAM_INFO_IS_DELETED));
					return paraminfo;	
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return paramInfoList;
	}
	
//	INSERT PARAMGROUP
	public Long insertParamGroup(Paramgroup paramgroup) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		Long id = maxParamGroupId() + 1;	
		final String insertParamGroup = "insert into paramgroup (param_group_id, param_group_name, isdeleted) values(?, ?, ?)";
		jdbcTemplate.update(insertParamGroup, new Object[]{id, paramgroup.getParamGroupName(), paramgroup.getIsdeleted()});
		return id;
	}
//	GET MAX ID PARAMGROUP
	public Long maxParamGroupId() {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		final String selectMaxId = "select MAX(param_group_id) from paramgroup";
		return  jdbcTemplate.queryForObject(selectMaxId, long.class);
	}
	
//	INSERT PARAMINFO
	public void insertParamInfo(Paraminfo paraminfo, Long paramGroupId) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		Long id = maxParamInfoId();
		if (id == null) {
			id = (long) 1;
		}else {
			id += 1;
		}
		log.info("id --------------------------  " + id);
		log.info(paramGroupId);
		log.info("Name --------------------  " + paraminfo.getParamInfoName());
		log.info("Vaue1 --------------------  " + paraminfo.getParamInfoValue());
		log.info("Name2 --------------------  " + paraminfo.getParamInfoValue2());
		log.info("is delete --------------------  " + paraminfo.getIsdeleted());
		final String insertParamInfo = "insert into paraminfo (param_info_id, param_info_name, isdeleted, param_info_value, param_group_id, param_info_value2) values (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertParamInfo, new Object[] {id, paraminfo.getParamInfoName(), paraminfo.getIsdeleted(), paraminfo.getParamInfoValue(), paramGroupId, paraminfo.getParamInfoValue2()});

	}
//	GET MAX ID PARAMINFO
	public Long maxParamInfoId() {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		final String selectMaxId = "select MAX(param_info_id) from paraminfo";
		return  jdbcTemplate.queryForObject(selectMaxId, long.class);
	}
	
//	UPDATE PARAMGROUP
	public void updateParamGroup(Paramgroup paramgroup) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		final String updateParamGroup = "update paramgroup set param_group_name = ? where param_group_id = ?";
		jdbcTemplate.update(updateParamGroup, new Object[]{paramgroup.getParamGroupName(),paramgroup.getParamGroupId()});
	}
//	UPDATE PARAMINFO
	public void updateParamInfo(Paraminfo paraminfo) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		final String updateParamInfo = "update paraminfo set param_info_name = ? where param_info_id = ?";
		jdbcTemplate.update(updateParamInfo, new Object[]{paraminfo.getParamInfoName(),paraminfo.getParamInfoId()});
	}
	
//	DELETE PARAMGROUP
	public void deleteParamGroup(Long paramGroupId) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		final String deleteParamGroup = "update paramgroup set isdeleted = 'Y' where param_group_id = ?";
		 jdbcTemplate.update(deleteParamGroup, new Object[]{paramGroupId});
	}
//	DELETE PARAMINFO
	public void deleteParamInfo(Long paramInfoId) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		final String deleteParamInfo = "update paraminfo set isdeleted = 'Y' where param_info_id = ?";
		 jdbcTemplate.update(deleteParamInfo, new Object[]{paramInfoId});
	}

}
