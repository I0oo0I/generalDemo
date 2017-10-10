package com.kxy.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel<T> {

	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static final String LIB_PATH = "lib";
    public static final String STUDENT_INFO_XLS_PATH = LIB_PATH + "/student_info" + POINT + OFFICE_EXCEL_2003_POSTFIX;
    public static final String STUDENT_INFO_XLSX_PATH = LIB_PATH + "/student_info" + POINT + OFFICE_EXCEL_2010_POSTFIX;
    public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
    public static final String PROCESSING = "Processing...";
	
    /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws IOException
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public  List<T> readExcel(String path, T t) throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException {
        if (path == null || EMPTY.equals(path)) {
            return null;
        } else {
            String postfix = getPostfix(path);
            if (!EMPTY.equals(postfix)) {
                if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path, t);
                } else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path, t);
                }
            } else {
                System.out.println(path + NOT_EXCEL_FILE);
            }
        }
        return null;
    }

    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public  List<T> readXlsx(String path,T t) throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException {
        File file = new File(path);
        if(!file.exists()){
        	return new ArrayList<T>();
        }
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<T> list = new ArrayList<>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            @SuppressWarnings("rawtypes")
			Class cs = t.getClass();
        	Field [] fields = cs.getDeclaredFields();
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                	@SuppressWarnings("unchecked")
					T tt = (T) cs.newInstance();
                	for(int i = 0; i< xssfRow.getLastCellNum(); i++){
                		Type type = fields[i].getGenericType();
                		fields[i].setAccessible(true);
                		fields[i].set(tt, getValue(xssfRow.getCell(i),type));
                	}
                	list.add(tt);
                }
            }
        }
        xssfWorkbook.close();
        is.close();
        return list;
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public List<T> readXls(String path, T t) throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException {
        File file = new File(path);
        if(!file.exists()){
        	return new ArrayList<T>();
        }
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<T> list = new ArrayList<>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            @SuppressWarnings("rawtypes")
			Class cs = t.getClass();
    		Field [] fields = cs.getDeclaredFields();
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                	@SuppressWarnings("unchecked")
					T tt = (T) cs.newInstance();
                	for(int i = 0; i< hssfRow.getLastCellNum(); i++){
                		Type type = fields[i].getGenericType();
                		fields[i].setAccessible(true);
                		fields[i].set(tt, getValue(hssfRow.getCell(i), type));
                		
                	}
                	list.add(tt);
                }
            }
        }
        hssfWorkbook.close();
        is.close();
        return list;
    }

    @SuppressWarnings({ "static-access", "deprecation" })
    private Object getValue(XSSFCell xssfRow, Type type) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return xssfRow.getBooleanCellValue();
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
        	if(DateUtil.isCellDateFormatted(xssfRow)){
        		return xssfRow.getDateCellValue();
        	}else{
        		if(type == int.class || type == Integer.class){
        			return tanferNumberToInt(xssfRow.getNumericCellValue());
        		}else{
        			return xssfRow.getNumericCellValue();
        		}
        	}
           
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_STRING){
            return xssfRow.getStringCellValue();
        } else {
        	return null;
        }
    }

    @SuppressWarnings({ "static-access", "deprecation" })
    private Object getValue(HSSFCell hssfCell, Type type) {
    	if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return hssfCell.getBooleanCellValue();
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
        	if(DateUtil.isCellDateFormatted(hssfCell)){
        		return hssfCell.getDateCellValue();
        	}else{
        		if(type == int.class || type == Integer.class){
        			return tanferNumberToInt(hssfCell.getNumericCellValue());
        		}else{
        			return hssfCell.getNumericCellValue();
        		}
        	}
           
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_STRING){
            return hssfCell.getStringCellValue();
        } else {
        	return null;
        }
    }
    
    /**
     * 将double转换成int数据类型
     * @param dd
     * @return
     */
    public int tanferNumberToInt(Double dd){
    	int result = 0;
    	if(dd.intValue() == dd){
    		result = dd.intValue();
    	}else{
    		result = (int) Math.round(dd);
    	}
    	return result;
    }
    
    /**
     * get postfix of the path
     * @param path
     * @return
     */
    public static String getPostfix(String path) {
        if (path == null || EMPTY.equals(path.trim())) {
            return EMPTY;
        }
        if (path.contains(POINT)) {
            return path.substring(path.lastIndexOf(POINT) + 1, path.length());
        }
        return EMPTY;
    }
}
