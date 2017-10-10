package com.kxy.demo.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExportUtils<T> {

  
    public void exporXSSFSheet(List<T> list, String sheetName, String [] titles, ServletOutputStream outPutStream) throws IllegalArgumentException, IllegalAccessException, IOException{
    	//创建一个空的工作簿
    	XSSFWorkbook workbook = new XSSFWorkbook();
    	//创建一个空的电子表格
    	XSSFSheet sheet = workbook.createSheet(sheetName);
    	
    	if(list == null){
    		list = new ArrayList<T>();
    	}
    	
    	XSSFCell cell = null;
    	
    	int ceilId = 0;
    	XSSFRow row = sheet.createRow(0);
		for(String s : titles){
			cell = row.createCell(ceilId++);
			cell.setCellValue(s);
		}
		
    	for (int i = 0; i < list.size(); i++) {
    		ceilId = 0;
    		T t = list.get(i);
    		row = sheet.createRow(i+1);
    		Field [] fields = t.getClass().getDeclaredFields();
    		for(Field fd : fields){
    			fd.setAccessible(true);
    			Object value = fd.get(t);
    			Type type = fd.getType();
    			cell = row.createCell(ceilId++);
    			setCellValue(cell, type, value);
    		}
		}
    	workbook.write(outPutStream);
        workbook.close();
    }

    @SuppressWarnings("deprecation")
	public void setCellValue(XSSFCell cell, Type type, Object value){
    	if(null == value){
    		cell.setCellValue("");
    		return;
    	}
    	if (type == Date.class){
    		cell.setCellValue(DateFormatUtils.format(new Date(String.valueOf(value)), DateFormatUtils.FORMAT_SHORT_CN));
    	} else {
    		cell.setCellValue(String.valueOf(value));
    	} 
    } 
}
