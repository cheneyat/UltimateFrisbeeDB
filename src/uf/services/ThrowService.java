package uf.services;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

public class ThrowService {
	
	private DatabaseConnectionService dbService = null;
	
	public ThrowService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	private boolean addThrow(int pointID, int throwingPlayer, String type) {
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_throw](?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, pointID);
			stmt.setInt(3, throwingPlayer);
			stmt.setString(4, type);
			
			stmt.execute();
			errCode = stmt.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (errCode == 0) {
			JOptionPane.showMessageDialog(null, "Add to Throw successful.");
			result = true;
		}
		else if (errCode == 1) {
			JOptionPane.showMessageDialog(null, "ERROR: ThrowID already exists");
		}
		else if (errCode == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: PointID does not exist.");
		}
		else if (errCode == 3) {
			JOptionPane.showMessageDialog(null, "ERROR: Throwing Player ID does not exist.");
		}
		else if (errCode == 4) {
			JOptionPane.showMessageDialog(null, "ERROR: Invalid throw type.");
		}
		
		return result;
	}
	
	public boolean addCompletedPass(int pointID, int throwingPlayer, int catchingPlayer, boolean isGoal) {
		boolean result = false;
		if(addThrow(pointID, throwingPlayer, "CP")) {
			CallableStatement stmt = null;
			int errCode = -1;
			try {
				stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_completed_throw](?, ?)}");
				stmt.registerOutParameter(1, Types.INTEGER);
				stmt.setInt(2, catchingPlayer);
				if(isGoal) stmt.setInt(3, 1);
				else stmt.setInt(3, 0);
				
				stmt.execute();
				errCode = stmt.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (errCode == 0) {
				JOptionPane.showMessageDialog(null, "Add to Completed Pass successful.");
				result = true;
			}
			else if (errCode == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Invalid Inputs");
			}
		}
		return result;
	}
	
	public boolean addBlockedPass(int pointID, int throwingPlayer, int blockingPlayer) {
		boolean result = false;
		if(addThrow(pointID, throwingPlayer, "BT")) {
			CallableStatement stmt = null;
			int errCode = -1;
			try {
				stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_blocked_throw] ?)}");
				stmt.registerOutParameter(1, Types.INTEGER);
				stmt.setInt(2, blockingPlayer);
				
				stmt.execute();
				errCode = stmt.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (errCode == 0) {
				JOptionPane.showMessageDialog(null, "Add to Blocked Pass successful.");
				result = true;
			}
			else if (errCode == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Invalid Inputs");
			}
			
		}
		return result;
	}
	
	public boolean addPull(int pointID, int throwingPlayer, int hangtime) {
		boolean result = false;
		if(addThrow(pointID, throwingPlayer, "PU")) {
			CallableStatement stmt = null;
			int errCode = -1;
			try {
				stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_pull] ?)}");
				stmt.registerOutParameter(1, Types.INTEGER);
				stmt.setInt(2, hangtime);
				
				stmt.execute();
				errCode = stmt.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (errCode == 0) {
				JOptionPane.showMessageDialog(null, "Add to Blocked Pass successful.");
				result = true;
			}
			else if (errCode == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Invalid Inputs");
			}
			
		}
		return result;
	}

}
