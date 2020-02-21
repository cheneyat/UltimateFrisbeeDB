package uf.services;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import sodabase.ui.SodaByRestaurant;

public class PlayerStatsService {
	
	private DatabaseConnectionService dbService = null;
	
	public PlayerStatsService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public String[][] statsByPlayer(String USAUID, String fName, String lName) {
		PreparedStatement stmt = null;
		String query = "Select USAUID, FName, LName, [Completed Passes], Assists, [Points scored], [Throwaways], [Completion percentage]\n"
								+ "From StatsByPlayer\n";
		try {
			int numParams = 1;
			ArrayList<String> wheresToAdd = new ArrayList<String>();
			if (USAUID != null && !USAUID.isEmpty()) {
				wheresToAdd.add("USAUID = ?");
			}
			if (fName != null && !fName.isEmpty()) {
				wheresToAdd.add("FName = ?");
			}
			if (lName != null && !lName.isEmpty()) {
				wheresToAdd.add("LName = ?");
			}
			boolean isFirst = true;
			while (wheresToAdd.size() > 0) {
				if (isFirst) {
					query = query + " WHERE " + wheresToAdd.remove(0);
					isFirst = false;
				} else {
					query = query + " AND " + wheresToAdd.remove(0);
				}
			}
			stmt = this.dbService.getConnection().prepareStatement(query);
			if(USAUID != null && !USAUID.isEmpty()) {
				stmt.setInt(numParams, Integer.parseInt(USAUID));
				numParams++;
			}
			if(fName != null && !fName.isEmpty()) {
				stmt.setString(numParams, fName);
				numParams++;
			}
			if(lName != null && !lName.isEmpty()) {
				stmt.setString(numParams, lName);
			}
			ResultSet rs = stmt.executeQuery();
			return parseResults(rs);
		} catch (SQLException e) {
			return null;
		}
	}

	private String[][] parseResults(ResultSet rs) {
		List<String[]> results = new ArrayList<>();
		try {
			int USAUID = rs.findColumn("USAUID");
			int fName = rs.findColumn("FName");
			int lName = rs.findColumn("LName");
			int cPasses = rs.findColumn("Completed Passes");
			int assists = rs.findColumn("Assists");
			int pScored = rs.findColumn("Points scored");
			int throwaways = rs.findColumn("Throwaways");
			int cPercentage = rs.findColumn("Completion percentage");
			while(rs.next()) {
				String[] temp = new String[8];
				temp[0] = (Integer.toString(rs.getInt(USAUID)));
				temp[1] = (rs.getString(fName));
				temp[2] = (rs.getString(lName));
				temp[3] = (Integer.toString(rs.getInt(cPasses)));
				temp[4] = (Integer.toString(rs.getInt(assists)));
				temp[5] = (Integer.toString(rs.getInt(pScored)));
				temp[6] = (Integer.toString(rs.getInt(throwaways)));
				temp[7] = (Double.toString(rs.getDouble(cPercentage)));
				results.add(temp);
			}
		} catch (SQLException e) {
		}
		String[][] actualResults = new String[results.size()][7];
		for(int i = 0; i < results.size(); i++) {
			actualResults[i] = results.get(i);
		}
		
		return actualResults;
	}

}
