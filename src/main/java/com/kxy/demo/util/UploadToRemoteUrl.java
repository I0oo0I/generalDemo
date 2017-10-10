package com.kxy.demo.util;

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.net.HttpURLConnection;  
import java.net.URL;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.Map;  

import net.sf.jmimemagic.Magic;  
import net.sf.jmimemagic.MagicMatch;  
      
public class UploadToRemoteUrl {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        String filepath = "E:/upload/201995-120HG1030762.png";  
        String urlStr = "http://devwxy.huaao24.com.cn/file/upload/image.do";  
        Map<String, String> textMap = new HashMap<String, String>();  
        textMap.put("name", "testname");  
        Map<String, String> fileMap = new HashMap<String, String>();  
        fileMap.put("userfile", filepath);  
        String ret = formUpload(urlStr, textMap, fileMap);  
        System.out.println(ret);  
    } 
    
    /**
     * 通过本地文件地址和远程上传文件的接口地址,将本地文件上传到远程服务器
     * @param filePath	本地文件地址
     * @param remoteUrl	远程接口地址
     * @return
     */
    public static String uploadFile(String filePath, String remoteUrl){
    	File file = new File(filePath);
    	if(file.exists()){
    		 Map<String, String> textMap = new HashMap<String, String>();
    	        textMap.put("name", "testname");
    	        Map<String, String> fileMap = new HashMap<String, String>();
    	        fileMap.put("userfile", filePath);
    	    	return formUpload(remoteUrl, textMap, fileMap);
    	}else{
    		return null;
    	}
    }
  
    /** 
     * 上传图片 
     * @param urlStr 
     * @param textMap 
     * @param fileMap 
     * @return 
     */  
    private static String formUpload(String remoteUrl, Map<String, String> textMap, Map<String, String> fileMap) {  
        String res = "";  
        HttpURLConnection conn = null;  
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
        try {  
            URL url = new URL(remoteUrl);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
  
            OutputStream out = new DataOutputStream(conn.getOutputStream());  
            // text    
            if (textMap != null) {  
                StringBuffer strBuf = new StringBuffer();  
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");  
                    strBuf.append(inputValue);  
                }  
                out.write(strBuf.toString().getBytes());  
            }  
  
            // file    
            if (fileMap != null) {  
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry<String, String> entry = iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    File file = new File(inputValue);  
                    String filename = file.getName();  
                    MagicMatch match = Magic.getMagicMatch(file, false, true);  
                    String contentType = match.getMimeType(); //判断文件的类型 
  
                    StringBuffer strBuf = new StringBuffer();  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");  
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  
  
                    out.write(strBuf.toString().getBytes());  
  
                    DataInputStream in = new DataInputStream(new FileInputStream(file));  
                    int bytes = 0;  
                    byte[] bufferOut = new byte[1024];  
                    while ((bytes = in.read(bufferOut)) != -1) {  
                        out.write(bufferOut, 0, bytes);  
                    }  
                    in.close();  
                }  
            }  
  
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
            out.write(endData);  
            out.flush();  
            out.close();  
  
            // 读取返回数据    
            StringBuffer strBuf = new StringBuffer();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                strBuf.append(line).append("\n");  
            }  
            res = strBuf.toString();  
            reader.close();  
            reader = null;  
        } catch (Exception e) {  
            System.out.println("发送POST请求出错。" + remoteUrl);  
            e.printStackTrace();  
        } finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }  
        return res;  
    }  
  
}  