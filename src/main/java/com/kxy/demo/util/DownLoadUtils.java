package com.kxy.demo.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class DownLoadUtils {
	  
	public static void downLoadFile(HttpServletResponse response, File file) { 
		if (file == null || !file.exists()) {
			return;
		} 
		OutputStream out = null; 
		try { 
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8"); 
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName()); 
			response.setHeader("Content-Length", "" + file.length());
			out = response.getOutputStream(); 
			out.write(FileUtils.readFileToByteArray(file)); 
			out.flush(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally { 
			if (out != null) { 
				try { 
					out.close(); 
				} catch (IOException e) { 
					e.printStackTrace();
				} 
			} 
		} 
	}
	
	public static ResponseEntity<byte[]> download(String fileName, File file) throws IOException {
		String dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1"); 
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
		headers.setContentDispositionFormData("attachment", dfileName); 
		headers.setContentLength(file.getTotalSpace());
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED); 
	}
	
	public static void download3(HttpServletResponse response, File file) throws IOException{
		response.setContentType("application/OCTET-STREAM;charset=UTF-8");
        response.setHeader("Content-Dispositon", "attachment;filename="+file.getName());
        response.setHeader("Content-Length", "" + file.length());
        FileInputStream fis = null;
        BufferedOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer))!=-1){
                bos.write(buffer,0,len);
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            fis.close();
            bos.close();
        }
	}

}
