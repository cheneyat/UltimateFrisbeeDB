package uf.services;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class PlayerService {
	
	private DatabaseConnectionService dbService = null;
	
	public PlayerService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addPlayer(int USAUID, String fName, String lName, String podID) {
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_player](?, ?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, USAUID);
			stmt.setString(3, fName);
			stmt.setString(4, lName);
			if (podID != null && !podID.isEmpty()) { 
				
				stmt.setInt(5, Integer.parseInt(podID));
			} else {
				stmt.setString(5, null);
			}
			
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
		else if (errCode == 1) {
			JOptionPane.showMessageDialog(null, "ERROR: USAUID cannot be null");
		}
		else if (errCode == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: USAUID is already assigned to a player.");
		}
		else if (errCode == 3) {
			JOptionPane.showMessageDialog(null, "ERROR: PodID must reference an existing Pod or be null");
		}
		else if (errCode == 4) {
			JOptionPane.showMessageDialog(null, "ERROR: FName and LName cannot be null or empty.");
		}
		
		return result;
	}

}
