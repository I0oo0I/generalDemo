package com.kxy.demo.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.kxy.demo.model.User;

public class PasswordHelper {

	private static RandomNumberGenerator numberGenerator = new SecureRandomNumberGenerator();
	private static String algorithmName = "md5";
	private final static int hashIterations = 2;
	
	public static void encryptPassword(User user){
		user.setSalt(numberGenerator.nextBytes().toHex());
		String newPassword = new SimpleHash(algorithmName, user.getPassword(), 
				ByteSource.Util.bytes(user.getSalt()), hashIterations).toHex();
		user.setPassword(newPassword);
	}
}
