package com.kxy.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxy.demo.exception.ReturnFormat;

@Controller
@RequestMapping("errorPage")
public class ErrorController {
	
	@RequestMapping("error404")
	@ResponseBody
	public String error404(Exception ex){
		ex.printStackTrace();
		return ReturnFormat.retParam(404, null);
	}
	
	@RequestMapping("error500")
	@ResponseBody
	public String error500(Exception ex){
		ex.printStackTrace();
		return ReturnFormat.retParam(500, null);
	}
}
