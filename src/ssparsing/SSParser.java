package ssparsing;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

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

import uf.services.GameService;
import uf.services.PlayerService;
import uf.services.PointService;
import uf.services.TeamService;
import uf.services.ThrowService;

public class SSParser {
	private FileInputStream stream;
	private XSSFWorkbook excelFile;
	
	private ThrowService throwS;
	private PlayerService playerS;
	private PointService pointS;
	private TeamService teamS;
	private GameService gameS;
	
	private Map<String, Integer> team1PlayerMap;
	private Map<String, Integer> team2PlayerMap;
	
	public SSParser(String filePath) {
		
		team1PlayerMap = new HashMap<String, Integer>();
		team2PlayerMap = new HashMap<String, Integer>();
		try {
			stream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Invalid filepath: " + filePath);
		}
		
		try {
			excelFile = new XSSFWorkbook(stream);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Failed initializing the Excel file.");
		}
	}
	
	public void parse(ThrowService throwService, PlayerService playerService, PointService pointService, TeamService teamService, GameService gameService) {
		
		throwS = throwService;
		playerS = playerService;
		pointS = pointService;
		teamS = teamService;
		gameS = gameService;
		
		try {
		    XSSFSheet sheet = excelFile.getSheetAt(0);
		    XSSFRow row;
		    XSSFCell cell;
		    
		    row = sheet.getRow(0);
            cell = row.getCell((short)0);

		    int rows;
		    rows = sheet.getPhysicalNumberOfRows();

		    int cols = 0;
		    int tmp = 0;

		    for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }

		    Game game = getGame(sheet);
		    java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		    int gameID = gameS.addGame(date, teamS.getIDByTeamName(game.team1.teamName), teamS.getIDByTeamName(game.team2.teamName));
		    getPlayersOnTeams(sheet, rows);
		    getGameData(sheet, rows, gameID);

		} catch(Exception ioe) {
		}
	}

	private void getGameData(XSSFSheet sheet, int numrows, int gameID) {
		XSSFRow row;
		int pointID = 1;
		for(int i = 4; i < numrows; i++) {
			row = sheet.getRow(i);
			XSSFCell typeCell = row.getCell((short)0);
			XSSFCell throwerCell = row.getCell((short)1);
			XSSFCell otherPlayerCell = row.getCell((short)2);
			XSSFCell utilityCell = row.getCell((short)3);
			
			if (typeCell.getStringCellValue().equals("")) {
				break;
			}

			String type = typeCell.getStringCellValue();
			int throwingPlayer = (int) throwerCell.getNumericCellValue();
			int otherPlayer = (int) otherPlayerCell.getNumericCellValue();			
			
			if (type.equals("Completed Pass")) {
				throwS.addCompletedPass(gameID, pointID, throwingPlayer, otherPlayer, utilityCell.getBooleanCellValue());
				if (utilityCell.getBooleanCellValue()) {
					pointID++;
				}
			} else if (type.equals("Throwaway")) {
				throwS.addThrowaway(gameID, pointID, throwingPlayer);
			} else if (type.equals("BlockedThrow")) {
				throwS.addBlockedPass(gameID, pointID, throwingPlayer, otherPlayer);
			} else if (type.equals("Pull")) {
				throwS.addPull(gameID, pointID, throwingPlayer, (int) utilityCell.getNumericCellValue());
			} else {
				
			}
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
			
			if (t1NameCell.getStringCellValue().equals("")) {
				break;
			}

			team1PlayerMap.put(t1NameCell.getStringCellValue(), (int) t1USAUIDCell.getNumericCellValue());
			
			if (t2NameCell.getStringCellValue().equals("")) {
				continue;
			}
			
			team2PlayerMap.put(t2NameCell.getStringCellValue(), (int) t2USAUIDCell.getNumericCellValue());
		}
	}

	private Game getGame(XSSFSheet sheet) {
		
		XSSFRow row = sheet.getRow(2);
		XSSFCell cell = row.getCell((short)0);
		XSSFCell cell2 = row.getCell((short)2);
		
		if (cell.getStringCellValue().equals("")) {
			return null;
		}
		
       
        return new Game(cell.getStringCellValue(), cell2.getStringCellValue());
	}

}
