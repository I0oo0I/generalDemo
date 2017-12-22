package com.kxy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxy.demo.model.User;
import com.kxy.demo.service.AlertService;
import com.kxy.demo.service.RabbitTemplateService;

@Controller
@RequestMapping("api/test")
public class TestTSG {

	@Autowired
	private AlertService alertService;
	
	@Autowired
	private RabbitTemplateService templateService;
	
	@RequestMapping("test")
	@ResponseBody
	private String test(){
		User user = new User("1", "小黑", "1", 1);
		alertService.sendUserMessage(user);
		return "success";
	}
	
	@RequestMapping("testRabbitMQ")
	@ResponseBody
	private String testRabbitMQ(){
		User user = new User("1", "小黑", "1", 1);
		templateService.sendMesssage(user);
		return "success";
	}
	
	
}
