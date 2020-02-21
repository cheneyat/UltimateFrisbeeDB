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

public class TeamStatsService {
	
	private DatabaseConnectionService dbService = null;
	
	public TeamStatsService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public String[][] statsByTeam(String Name) {
		PreparedStatement stmt = null;
		String query = "Select Name, Wins, Losses, PointsFor, PointsAgainst\n"
								+ "From StatsByTeam\n";
		try {
			int numParams = 1;
			ArrayList<String> wheresToAdd = new ArrayList<String>();
			if (Name != null && !Name.isEmpty()) {
				wheresToAdd.add("Name = ?");
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
			if(Name != null && !Name.isEmpty()) {
				stmt.setString(numParams, Name);
			}
			ResultSet rs = stmt.executeQuery();
			return parseResults(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private String[][] parseResults(ResultSet rs) {
		List<String[]> results = new ArrayList<>();
		try {
			int Name = rs.findColumn("Name");
			int Wins = rs.findColumn("Wins");
			int Losses = rs.findColumn("Losses");
			int pFor = rs.findColumn("PointsFor");
			int pAgainst = rs.findColumn("PointsAgainst");
			while(rs.next()) {
				String[] temp = new String[5];
				temp[0] = (rs.getString(Name));
				temp[1] = (Integer.toString(rs.getInt(Wins)));
				temp[2] = (Integer.toString(rs.getInt(Losses)));
				temp[3] = (Integer.toString(rs.getInt(pFor)));
				temp[4] = (Integer.toString(rs.getInt(pAgainst)));
				results.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[][] actualResults = new String[results.size()][7];
		for(int i = 0; i < results.size(); i++) {
			actualResults[i] = results.get(i);
		}
		
		return actualResults;
	}

}
