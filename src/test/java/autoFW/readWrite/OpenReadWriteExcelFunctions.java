package autoFW.readWrite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import autoFW.objects.ObjectUtilities;



public class OpenReadWriteExcelFunctions {
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	FileInputStream excelFileInputStream;
	OpenReadWriteExcelFunctions excelFunc;
	ObjectUtilities objUtil;

	public void openExcelFile(String file) throws FileNotFoundException, IOException {
		objUtil = new ObjectUtilities();
		excelFileInputStream = new FileInputStream(file);
		workbook = new XSSFWorkbook(excelFileInputStream);

	}

	@SuppressWarnings("static-access")
	public String readExcelData(int row, int col, String sheetName) {
		sheet = workbook.getSheet(sheetName);
		XSSFRow rw = sheet.getRow(row - 1);
		XSSFCell cell = rw.getCell(col - 1);
//		cell.setCellType(cell.CELL_TYPE_STRING);

		return cell.getStringCellValue();
	}

	public void writeExcelData(int row, int col, String data, String file, String sheetName) throws IOException {
		excelFileInputStream = new FileInputStream(file);
		workbook = new XSSFWorkbook(excelFileInputStream);
		sheet = workbook.getSheet(sheetName);

		XSSFRow rw = sheet.getRow(row - 1);
		if (rw == null)
			rw = sheet.createRow(row - 1);
		XSSFCell cell = rw.getCell(col - 1);
		if (cell == null)
			cell = rw.createCell(col - 1);

		// HC - visit later
		cell.setCellValue(data);
		excelFileInputStream.close();

		FileOutputStream excelFileOutputStream = new FileOutputStream(new File(file));
		workbook.write(excelFileOutputStream);
		excelFileOutputStream.close();
	}

	public void closeExcel() throws IOException {
		excelFileInputStream.close();
	}

	public int getMatchRowNumber(String data, int colNoMatch, String file, String sheetName) throws IOException {
		excelFunc = new OpenReadWriteExcelFunctions();
		excelFileInputStream = new FileInputStream(file);
		workbook = new XSSFWorkbook(excelFileInputStream);
		sheet = workbook.getSheet(sheetName);
		int RowCount = sheet.getLastRowNum();
		int rowNo;
		boolean fnd = false;
		for (rowNo = 1; rowNo <= RowCount; rowNo++) {
			String cellvalue = sheet.getRow(rowNo).getCell(colNoMatch - 1).getStringCellValue();
			if (cellvalue.equals(data)) {
				fnd = true;
				break;
			}
		}

		if (fnd)
			return rowNo + 1;
		else {
			objUtil.Reporter__log("Could not find Key: '" + data + "' in column number '" + colNoMatch + "'", "Fail");
			return -1;
		}

	}

	public int getRowCount(String file, String sheetName) throws IOException {
		excelFunc = new OpenReadWriteExcelFunctions();
		excelFileInputStream = new FileInputStream(file);
		workbook = new XSSFWorkbook(excelFileInputStream);
		sheet = workbook.getSheet(sheetName);
		int RowCount = sheet.getLastRowNum();

		return RowCount;

	}

	public int getColumnCount(String file, String sheetName) throws IOException {
		excelFunc = new OpenReadWriteExcelFunctions();
		excelFileInputStream = new FileInputStream(file);
		workbook = new XSSFWorkbook(excelFileInputStream);
		sheet = workbook.getSheet(sheetName);
		int RowCount = sheet.getLastRowNum();
		int columnCount = sheet.getRow(RowCount).getLastCellNum();

		return columnCount;

	}

	public List<Integer> getAllMatchingRowNumber(String data, int colNoMatch, String file, String sheetName)
			throws IOException {
		excelFunc = new OpenReadWriteExcelFunctions();
		excelFileInputStream = new FileInputStream(file);
		workbook = new XSSFWorkbook(excelFileInputStream);
		sheet = workbook.getSheet(sheetName);

		List<Integer> columnCount = new ArrayList<>();
		int RowCount = sheet.getLastRowNum();
		int rowNo;
		for (rowNo = 3; rowNo < RowCount; rowNo++) {
			String cellvalue = sheet.getRow(rowNo).getCell(colNoMatch).getStringCellValue();
			if (cellvalue.equals(data)) {
				columnCount.add(rowNo);

			}

		}

		return columnCount;

	}

	public int getMatchRowNumberFromRow1(String data, int colNoMatch, String file, String sheetName)
			throws IOException {
		excelFunc = new OpenReadWriteExcelFunctions();
		excelFileInputStream = new FileInputStream(file);
		workbook = new XSSFWorkbook(excelFileInputStream);
		sheet = workbook.getSheet(sheetName);
		int RowCount = sheet.getLastRowNum();
		int rowNo;
		for (rowNo = 1; rowNo <= RowCount; rowNo++) {
			String cellvalue = sheet.getRow(rowNo).getCell(colNoMatch).getStringCellValue();
			if (cellvalue.equals(data)) {
				break;

			}

		}

		return rowNo;

	}
}
