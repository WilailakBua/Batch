package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.service.ParamService;
@Service
public class ParamController {
	
	@Autowired
	private ParamService paramService;
	
	public void execution() {
		// TODO Auto-generated method stub
		paramService.readFile();
		
		
	}
}

