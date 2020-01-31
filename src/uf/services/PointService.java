package uf.services;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class PointService {
	
	private DatabaseConnectionService dbService = null;
	
	public PointService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean AddPoint(int GameID, int OffensiveTeam, int DefensiveTeam, int AssistingPlayer, int scoringPlayer) {
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		int ThrowID = 1;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_point](2, ?, ?, ?, ?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, GameID);
			stmt.setInt(3, ThrowID);
			stmt.setInt(4, OffensiveTeam);
			stmt.setInt(5, DefensiveTeam);
			stmt.setInt(6, AssistingPlayer);
			stmt.setInt(7, scoringPlayer);
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

}

