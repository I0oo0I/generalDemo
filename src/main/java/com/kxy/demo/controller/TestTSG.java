package com.kxy.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxy.demo.model.User;
import com.kxy.demo.service.TestService;
import com.kxy.demo.util.TreeNode;

@Controller
@RequestMapping("api/test")
public class TestTSG {

	@Autowired
	private TestService testService;
	
	private TestService testService1 = new TestService();
	
	@RequestMapping("test")
	@ResponseBody
	private List<TreeNode<User>> test() throws Exception{
		testService.test();
		testService1.test();
		return null;
	}
	
	
}
