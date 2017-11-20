package com.kxy.demo.util;

import java.util.UUID;

public class PublicUtils {

	/**
	 * 生成32为主键id
	 * @return
	 */
	public static String createPrimaryId(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
