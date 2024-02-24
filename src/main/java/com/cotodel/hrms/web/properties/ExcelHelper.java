package com.cotodel.hrms.web.properties;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.ObjectUtils;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = {"Name", "Mobile"};
	static String SHEET = "Sheet1";
	public static ByteArrayInputStream DowinloadToExcel(String filedownload) {
		JSONObject demoRes=null;
		try (
			Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);
			// Header
			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}
			int rowIdx = 1;
			if(!ObjectUtils.isEmpty(filedownload)) {
				
				demoRes= new JSONObject(filedownload);

				JSONArray jsonArray = demoRes.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					Row row = sheet.createRow(rowIdx++);
					row.createCell(0).setCellValue(jsonArray.getJSONObject(i).getString("name"));
					row.createCell(1).setCellValue(jsonArray.getJSONObject(i).getString("mobile"));
				}

				workbook.write(out);
				return new ByteArrayInputStream(out.toByteArray());
			}else {
				return null;
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}
}