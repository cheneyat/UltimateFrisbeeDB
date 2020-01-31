package sodabase.services;



import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import uf.services.DatabaseConnectionService;

public class RestaurantService {

	private DatabaseConnectionService dbService = null;
	
	public RestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addResturant(String restName, String addr, String contact) {
		//TODO: Task 5
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[AddRestaurant](?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, restName);
			stmt.setString(3, addr);
			stmt.setString(4, contact);
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
			JOptionPane.showMessageDialog(null, "ERROR: Restaurant name cannot be null or empty");
		}
		else if (errCode == 2) {
			JOptionPane.showMessageDialog(null, "ERROR: Restaurant name already exists.");
		}
			
		
		return result;
	}
	

	public ArrayList<String> getRestaurants() {	
		//TODO: Task 2 
		ArrayList<String> rests = new ArrayList<String>();
		
		Statement statement = null;
		String query = "SELECT name FROM Rest";
		try {
			statement = this.dbService.getConnection().createStatement();
			ResultSet results = statement.executeQuery(query); 
			//ResultSet is a set of results. Iterate row after row...
			while(results.next()) {
				//Get varchar value from "name" column in current row
				String restName = results.getString("name");
				//Add name of current row to array list 
				rests.add(restName);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rests;
	}
}
