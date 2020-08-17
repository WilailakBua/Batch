package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Paramgroup;

@Repository
public interface ParamgroupRepository extends JpaRepository<Paramgroup, Long> {
	public List<Paramgroup> findAllByIsdeleted (String isdeleted);
	public Paramgroup findByParamGroupId(Long paramGroupId);

	

}
