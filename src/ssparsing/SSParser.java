package ssparsing;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SSParser {
	private FileInputStream stream;
	private XSSFWorkbook excelFile;
	
	public SSParser(String filePath) {
		
		try {
			stream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			System.err.println("Invalid filepath: " + filePath);
		}
		
		try {
			excelFile = new XSSFWorkbook(stream);
		} catch (IOException e) {
			System.err.println("Failed initializing the Excel file.");
		}
	}
	
	public void parse() {
		
		try {
		    XSSFSheet sheet = excelFile.getSheetAt(0);
		    XSSFRow row;
		    XSSFCell cell;
		    
		    row = sheet.getRow(0);
            cell = row.getCell((short)0);
            System.out.println(cell.getStringCellValue());
	                    // Your code here
		    
		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();
//
		    int cols = 0; // No of columns
		    int tmp = 0;

		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }
		    
//		    System.out.println("Number of rows: " + rows);
//		    System.out.println("Number of columns: " + cols);
//		    
		    getGame(sheet);
		    getPlayersOnTeams(sheet, rows);
		    getGameData(sheet, rows);
		    
//		    for(int r = 0; r < rows; r++) {
//		        row = sheet.getRow(r);
//		        if(row != null) {
//		            for(int c = 0; c < cols; c++) {
//		                cell = row.getCell((short)c);
//		                if(cell != null) {
//		                    // Your code here
//		                }
//		            }
//		        }
//		    }
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
	}

	private void getGameData(XSSFSheet sheet, int numrows) {
		XSSFRow row;
		
		int t1Name = 5;
		int t1USAUID = 6;
		int t2Name = 7;
		int t2USAUID = 8;
		
		for(int i = 4; i < numrows; i++) {
			
			row = sheet.getRow(i);
			XSSFCell t1NameCell = row.getCell((short)0);
			XSSFCell t1USAUIDCell = row.getCell((short)1);
			XSSFCell t2NameCell = row.getCell((short)2);
			XSSFCell t2USAUIDCell = row.getCell((short)3);
			
			if (t1NameCell.getStringCellValue().equals("")) {
				break;
			}
			
			System.out.println("Throw #" + (i-3) + "=  { Type: " + t1NameCell + ", Thower: " +
					t1USAUIDCell + ", Receiver: " + t2NameCell + ", isPoint: " + t2USAUIDCell + "} ");
			
			
			
		}
	}

	private void getPlayersOnTeams(XSSFSheet sheet, int numrows) {
		
		XSSFRow row;
		
		int t1Name = 5;
		int t1USAUID = 6;
		int t2Name = 7;
		int t2USAUID = 8;
		
		for(int i = 3; i < numrows; i++) {
			
			row = sheet.getRow(i);
			XSSFCell t1NameCell = row.getCell((short)t1Name);
			XSSFCell t1USAUIDCell = row.getCell((short)t1USAUID);
			XSSFCell t2NameCell = row.getCell((short)t2Name);
			XSSFCell t2USAUIDCell = row.getCell((short)t2USAUID);
			
			System.out.println("Team 1 - Player " + (i-2) + ": " + t1NameCell.getStringCellValue());
			System.out.println("Team 1 - Player " + (i-2) + " USAUID: " + t1USAUIDCell.getStringCellValue());
			System.out.println("Team 2 - Player " + (i-2) + ": " + t2NameCell.getStringCellValue());
			System.out.println("Team 2 - Player "  + (i-2) + " USAUID: " + t2USAUIDCell.getStringCellValue());
			
			if (t1NameCell.getStringCellValue().equals("")) {
				break;
			}
		}
		
	}

	private Game getGame(XSSFSheet sheet) {
		
		XSSFRow row = sheet.getRow(2);
		XSSFCell cell = row.getCell((short)0);
		XSSFCell cell2 = row.getCell((short)2);
        System.out.println("Team1: " + cell.getStringCellValue());
        System.out.println("Team2: " + cell2.getStringCellValue());
        
        return new Game(cell.getStringCellValue(), cell2.getStringCellValue());
	}

}
