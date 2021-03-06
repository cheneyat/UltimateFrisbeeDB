package uf.services;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class TeamService {
	
	private DatabaseConnectionService dbService = null;
	
	public TeamService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addTeam(int ID, String Name) {
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_team](?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, ID);
			stmt.setString(3, Name);
			stmt.execute();
			errCode = stmt.getInt(1);
		} catch (SQLException e) {
		}
		
		if (errCode == 0) {
			JOptionPane.showMessageDialog(null, "Add successful.");
			result = true;
		}
		else if (errCode == 1) {
			JOptionPane.showMessageDialog(null, "ERROR: Input ID already exists in Team");
		}
		else if (errCode == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: Input Name is NULL");
		}
		
		return result;
	}
	
	public int getIDByTeamName(String name) {
		int result = -1;
		CallableStatement stmt = null;
		
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[Get_Team_ByTeamName](?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, name);
			stmt.execute();
			result = stmt.getInt(1);
		} catch (SQLException e) {
		}
		
		return result;
	}

}
