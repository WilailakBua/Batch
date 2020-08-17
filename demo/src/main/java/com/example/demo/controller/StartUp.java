package com.example.demo.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Paramgroup;
import com.example.demo.repository.ParamgroupRepository;

@Component

public class StartUp {
	@Autowired private ParamgroupRepository paramgroupRepository;
	@PostConstruct
	public void start() {
		
		Paramgroup g1 = new Paramgroup();
		g1.setParamGroupName("MOMO");
		g1.setIsdeleted("N");
		
		Paramgroup g2 = new Paramgroup();
		g2.setParamGroupName("Neemo");
		g2.setIsdeleted("N");
		
		Paramgroup g3 = new Paramgroup();
		g3.setParamGroupName("CT");
		g3.setIsdeleted("N");
		
		paramgroupRepository.save(g1);
		paramgroupRepository.save(g2);
		paramgroupRepository.save(g3);
	}

}
