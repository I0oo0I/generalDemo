package com.kxy.demo.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class UploadUtils {

	/**
	 * springMvc包装类上传文件，支持多文件上传?,速度快，大文件推荐?
	 * @param request
	 * @param response
	 * @param path 为要保存文件的路径，传入null，则默认上传到项目中，也可以自定义，格式如:"F:/picture/",
	 * @param folderName 文件保存的文件夹，传入null，则为默认存在文件夹uploadFile下，也可以自定义，例如：myFile/image
	 * @return
	 */
	 public static Map<String, String> upload2(HttpServletRequest request, HttpServletResponse response, String path, String folderName){  
		    System.out.println("2");   
		    //创建通用的多部分解析?  
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	        Map<String, String> map = new HashMap<String, String>();
	        //判断 request 是否有文件上传?,即多部分请求  
	        if(multipartResolver.isMultipart(request)){  
	            //转换成多部分request    
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
	            //取得request中的上传的文件名  
	            Iterator<String> iter = multiRequest.getFileNames(); 
	            while(iter.hasNext()){  
	                //取得上传文件  
	                List<MultipartFile> files = (List<MultipartFile>) multiRequest.getFiles(iter.next());  
	                if(null != files && files.size() > 0 ){  
	                  for(MultipartFile file : files){	
	                    //取得当前上传文件的文件名?  
	                    String myFileName = file.getOriginalFilename(); 
	                    //如果名称不为空,说明该文件存在，否则说明该文件不存在  
	                    if(myFileName.trim() !=""){  
	                        //重命名上传后的文件名  
	                    	String suffix = myFileName.substring(myFileName.lastIndexOf("."), myFileName.length());
	                        String fileName = UUID.randomUUID().toString().replaceAll("-", "")+suffix;  
	                        //定义上传路径  
	                        if(StringUtils.isBlank(folderName)){
	                        	folderName = "uploadFile";
	                        }
	                        if(StringUtils.isBlank(path)){
	                        	path = request.getSession().getServletContext().getRealPath("/");
	                        }
	                        String pathWay = folderName + "\\" + fileName;
	                        File localFile = new File(path+pathWay);  
	                        try {
	                        	if(!localFile.exists()){localFile.mkdirs();}
								file.transferTo(localFile);
								map.put(myFileName, pathWay);
							} catch (IllegalStateException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}  
	                    }  
	                
	                  }
	                }  
	                //记录上传该文件后的时�?  
	            }  
	        }  
	        return map;  
	    }  
}
