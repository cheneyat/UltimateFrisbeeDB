package sodabase.services;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SodaService {

	private DatabaseConnectionService dbService = null;
	
	public SodaService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean addSoda(String sodaName, String manf) {
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[AddSoda](?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, sodaName);
			stmt.setString(3, manf);
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
			JOptionPane.showMessageDialog(null, "ERROR: Soda name cannot be null or empty.");
		}
		else if (errCode == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: Soda name already exists.");
		}
			
		
		return result;
	}
	
	public ArrayList<String> getSodas() {
		//TODO: Task 2
		ArrayList<String> sodas = new ArrayList<String>();
		Statement statement = null;
		String query = "SELECT name FROM Soda";
		try {
			statement = this.dbService.getConnection().createStatement();
			ResultSet results = statement.executeQuery(query); 
			//ResultSet is a set of results. Iterate row after row...
			while(results.next()) {
				//Get varchar value from "name" column in current row
				String sodaName = results.getString("name");
				//Add name of current row to array list 
				sodas.add(sodaName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sodas;
	}
}
