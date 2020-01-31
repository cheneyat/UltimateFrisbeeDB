package uf.services;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JOptionPane;

public class PointService {
	
	private DatabaseConnectionService dbService = null;
	
	public PointService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean AddPoint(int PointID, int GameID, String OffensiveTeam, String DefensiveTeam, String AssistingPlayer, String scoringPlayer) {
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		int ThrowID = 1;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_point](?, ?, ?, ?, ?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, PointID);
			stmt.setInt(3, GameID);
			stmt.setInt(4, ThrowID);
			stmt.setInt(5, GetTeamIDByName(OffensiveTeam));
			stmt.setInt(6, GetTeamIDByName(DefensiveTeam));
			stmt.setInt(7, GetPlayerIDByName(AssistingPlayer));
			stmt.setInt(8, GetPlayerIDByName(scoringPlayer));
			stmt.execute();
			errCode = stmt.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (errCode == 0) {
			JOptionPane.showMessageDialog(null, "Add successful.");
			result = true;
		}
		else if (errCode == 14) {
			JOptionPane.showMessageDialog(null, "ERROR: PointID and associated GameID already exist");
		}
		
		return result;
	}
	
	public int GetPlayerIDByName(String username) {
		
		CallableStatement stmt;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[Get_Player_ByFName](?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.execute();
			int id = stmt.getInt(1);
			return id;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Invalid player");
			return -1;
		}
	}
	
public int GetTeamIDByName(String username) {
		
		CallableStatement stmt;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[Get_Team_ByTeamName](?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.execute();
			int id = stmt.getInt(1);
			return id;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Invalid team");
			return -1;
		}
	}
	

}

