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
	
	private boolean addThrow(int gameID, int pointID, int throwingPlayer, String type) {
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
	
	public boolean addCompletedPass(int gameID, int pointID, int throwingPlayer, int catchingPlayer, boolean isGoal) {
		boolean result = false;
		int intGoal = isGoal ? 1 : 0;
		CallableStatement stmt = null;
		int errCode = -1;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_completed_throw](?, ?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, throwingPlayer);
			stmt.setInt(3, catchingPlayer);
			stmt.setInt(4, intGoal);
			stmt.setInt(5, gameID);

			stmt.execute();
			errCode = stmt.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (errCode == 0) {
			JOptionPane.showMessageDialog(null, "Add to Completed Pass successful.");
			result = true;
		} else if (errCode == 1) {
			JOptionPane.showMessageDialog(null, "ERROR: Throwing Player must be a valid player");
		} else if(errCode == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: Catching Player must be a valid player");
		} else if(errCode == 3) {
			JOptionPane.showMessageDialog(null, "ERROR: GameID must reference a valid game");
		} else if(errCode == 4) {
			JOptionPane.showMessageDialog(null, "ERROR: isGoal must be 1 or 0");
		}
		
		return result;
	}
	
	public boolean addBlockedPass(int gameID, int pointID, int throwingPlayer, int blockingPlayer) {
		boolean result = false;
		CallableStatement stmt = null;
		int errCode = -1;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_blocked_throw](?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, throwingPlayer);
			stmt.setInt(3, blockingPlayer);
			stmt.setInt(4, gameID);
			
			stmt.execute();
			errCode = stmt.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (errCode == 0) {
			JOptionPane.showMessageDialog(null, "Add to Blocked Pass successful.");
			result = true;
		} else if (errCode == 1) {
			JOptionPane.showMessageDialog(null, "ERROR: Throwing Player must be a valid player");
		} else if(errCode == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: Blocking Player must be a valid player");
		} else if(errCode == 3) {
			JOptionPane.showMessageDialog(null, "ERROR: GameID must reference a valid game");
		}
		return result;
	}
	
	public boolean addPull(int gameID, int pointID, int throwingPlayer, int hangtime) {
		boolean result = false;
		CallableStatement stmt = null;
		int errCode = -1;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_pull](?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, hangtime);
			stmt.setInt(3, gameID);
			
			stmt.execute();
			errCode = stmt.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (errCode == 0) {
			JOptionPane.showMessageDialog(null, "Add to Pull successful.");
			result = true;
		} else if (errCode == 1) {
			JOptionPane.showMessageDialog(null, "ERROR: Throwing Player must be a valid player");
		} else if(errCode == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: Hangtime must be a positive integer");
		} else if(errCode == 3) {
			JOptionPane.showMessageDialog(null, "ERROR: GameID must reference a valid game");
		}
		return result;
	}
	
	public boolean addThrowaway(int gameID, int pointID, int throwingPlayer) {
		boolean result = false;
		CallableStatement stmt = null;
		int errCode = -1;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[insert_throwaway](?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setInt(2, throwingPlayer);
			stmt.setInt(3, gameID);
			
			stmt.execute();
			errCode = stmt.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (errCode == 0) {
			JOptionPane.showMessageDialog(null, "Add to Throw successful.");
			result = true;
		} else if (errCode == 1) {
			JOptionPane.showMessageDialog(null, "ERROR: Throwing Player must be a valid player");
		} else if(errCode == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: GameID must reference a valid game");
		}
		return result;
	}

}
