package com.kxy.demo.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {

	private String name;
	
	
	public TestService() {
		super();
		System.out.println("开始构造");
	}


	public TestService(String name) {
		super();
		this.name = name;
	}


	public void test(){
		System.out.println("1111111111111111111111111111111111111111111");
	}
}
