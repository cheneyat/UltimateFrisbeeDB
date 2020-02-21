package uf.services;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class PlaysOnService {
	
	private DatabaseConnectionService dbService = null;
	
	public PlaysOnService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addPlayerToTeam(int USAUID, int TeamID) {
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[update_player_to_team](?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, USAUID);
			stmt.setInt(3, TeamID);
			stmt.execute();
			errCode = stmt.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "failed to add");
		}
		
		if (errCode == 0) {
			JOptionPane.showMessageDialog(null, "Add successful.");
			result = true;
		}
		else if (errCode == 1) {
			JOptionPane.showMessageDialog(null, "ERROR: Input USAUID does not exist in Player");
		}
		else if (errCode == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: Input TeamID does not match an existing team");
		}
		
		return result;
	}

}
