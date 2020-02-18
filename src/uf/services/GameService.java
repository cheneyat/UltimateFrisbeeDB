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
	
	public int addGame(Date date, int team1, int team2) {
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		
		System.out.println(team1);
		System.out.println(team2);
		
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_game](?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setDate(2, date);
			stmt.setInt(3, team1);
			stmt.setInt(4, team2);
			
			stmt.execute();
			errCode = stmt.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (errCode == -1) {
			JOptionPane.showMessageDialog(null, "Error adding game.");
			result = false;
			
		} else {
			JOptionPane.showMessageDialog(null, "Add to Game successful.");
			result = true;
		}
		
		return errCode;
	}
}
