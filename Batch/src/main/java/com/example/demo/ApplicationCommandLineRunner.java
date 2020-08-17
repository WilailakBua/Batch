package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.example.demo.Controller.ParamController;

public class ApplicationCommandLineRunner implements CommandLineRunner{


	@Autowired
	private ParamController controller;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		log.info("app run");
	       this.controller.execution();
	}

}
