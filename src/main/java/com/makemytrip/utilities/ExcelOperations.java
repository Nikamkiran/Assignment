package com.makemytrip.utilities;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelOperations {

	public static Workbook openExcel(String fileName) throws IOException {

		FileInputStream fis = new FileInputStream(fileName);

		Workbook workbook = null;

		if(fileName.toLowerCase().endsWith("xlsx")){
			workbook = new XSSFWorkbook(fis);
		}else if(fileName.toLowerCase().endsWith("xls")){
			workbook = new HSSFWorkbook(fis);
		}

		return workbook;
	}

	
	public static void create(String file_path) throws IOException {
		@SuppressWarnings("resource")
		Workbook wb = new XSSFWorkbook();   
		//creates an excel file at the specified location  
		OutputStream fileOut = new FileOutputStream(file_path);
		wb.createSheet();
		wb.write(fileOut);   
	}
	
	
	public static void write(String file_path, String[] details) throws IOException, EncryptedDocumentException, InvalidFormatException 
	{ 

		InputStream inp = new FileInputStream(file_path); 
		Workbook wb = WorkbookFactory.create(inp); 
		Sheet sheet = wb.getSheetAt(0); 
		int num = sheet.getLastRowNum();
		
		if(num==0) {
			Row row = sheet.createRow(num);
			row.createCell(0).setCellValue("SrNo"); 
			row.createCell(1).setCellValue("AirLine");
			row.createCell(2).setCellValue("Departure");
			row.createCell(3).setCellValue("Arrival");
			row.createCell(4).setCellValue("Price");
			row.createCell(5).setCellValue("Duration");
		}
		
		Row row = sheet.createRow(++num); 
		row.createCell(0).setCellValue(num); 
		row.createCell(1).setCellValue(details[0]);
		row.createCell(2).setCellValue(details[1]);
		row.createCell(3).setCellValue(details[2]);
		row.createCell(4).setCellValue(details[3]);
		row.createCell(5).setCellValue(details[4]);
		FileOutputStream fileOut = new FileOutputStream(file_path); 
		wb.write(fileOut); 
		fileOut.close(); 
	} 
	
}
