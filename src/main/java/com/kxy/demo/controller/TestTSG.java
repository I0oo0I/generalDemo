package com.kxy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxy.demo.model.User;
import com.kxy.demo.service.AlertService;

@Controller
@RequestMapping("api/test")
public class TestTSG {

	@Autowired
	private AlertService alertService;
	
	@RequestMapping("test")
	@ResponseBody
	private String test(){
		User user = new User("1", "小黑", "1", 1);
		alertService.sendUserMessage(user);
		return "success";
	}
	
	
}
