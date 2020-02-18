package uf.services;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class GameService {
	
	private DatabaseConnectionService dbService = null;
	
	public GameService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addGame(Date date) {
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_game](?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setDate(2, date);
			
			stmt.execute();
			errCode = stmt.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (errCode == 0) {
			JOptionPane.showMessageDialog(null, "Add to Game successful.");
			result = true;
		} else {
			JOptionPane.showMessageDialog(null, "Error adding game.");
			result = false;
		}
		
		return result;
	}

}
