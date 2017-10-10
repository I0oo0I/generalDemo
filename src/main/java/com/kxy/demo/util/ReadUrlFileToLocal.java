package com.kxy.demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadUrlFileToLocal {

	/**
	 * 读取远程服务器的文件到本地
	 * @param urlFilePath 远程服务器文件
	 * @param localFilePath 保存到本地的文件地址
	 * @throws RestException
	 */
	public static void getFileToLocal(String urlFilePath, String localFilePath) throws Exception {
       try {  
           File file = new File(localFilePath);//本地创建新文件  
           if(file!=null && !file.exists()){  
               file.createNewFile();  
           }  
           OutputStream oputstream = new FileOutputStream(file);  
           URL url = new URL(urlFilePath);  
           HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
           uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
           uc.connect();  
           InputStream iputstream = uc.getInputStream();  
           byte[] buffer = new byte[4*1024];  
           int byteRead = -1;     
           while((byteRead=(iputstream.read(buffer)))!= -1){  
               oputstream.write(buffer, 0, byteRead); 
           }  
           oputstream.flush();    
           iputstream.close();  
           oputstream.close();  
	    } catch (Exception e) {
	    	System.out.println("读取远程服务器文件失败！");
	        e.getStackTrace();
	    }     
		
	}
}
