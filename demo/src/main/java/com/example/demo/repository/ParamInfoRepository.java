package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Paramgroup;
import com.example.demo.entity.Paraminfo;

public interface ParamInfoRepository extends JpaRepository<Paraminfo, Long>{
	public List<Paraminfo> findAllByIsdeleted (String isdelete);
	public List<Paraminfo> findAllByisdeletedAndGroup_ParamGroupId(String isdeleted, Long paramGroupId);
	public List<Paraminfo> findAllByGroup(Paramgroup paramgroup);
	public List<Paraminfo> findAllByGroup_ParamGroupId(Long group);
	
}
