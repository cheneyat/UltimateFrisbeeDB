package sodabase.services;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sodabase.ui.SodaByRestaurant;
import uf.services.DatabaseConnectionService;


public class SodasByRestaurantService {

	private DatabaseConnectionService dbService = null;

	public SodasByRestaurantService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public boolean addSodaByRestaurant(String rest, String soda, double price) {
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[AddSells](?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, soda);
			stmt.setString(3, rest);
			stmt.setDouble(4, price);
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
			JOptionPane.showMessageDialog(null, "ERROR: Rest name cannot be null or empty.");
		}
		else if (errCode == 3) {
			JOptionPane.showMessageDialog(null, "ERROR: Price cannot be null or empty.");
		}
		else if (errCode == 4) {
			JOptionPane.showMessageDialog(null, "ERROR: Given soda does not exist.");
		}
		else if (errCode == 5) {
			JOptionPane.showMessageDialog(null, "ERROR: Given restaurant name does not exist.");
		}
		
		return result;
	}

	public ArrayList<SodaByRestaurant> getSodasByRestaurants(String rest, String soda, String price,
			boolean useGreaterThanEqual) {
		//TODO: Task 3 and Task 4
		try {
			int numParams = 1;
			String query;
			PreparedStatement stmt = null;
			Double dPrice = 0.;
			if(!price.isEmpty() && !price.isEmpty()) {
				dPrice =  Double.parseDouble(price);
				query = buildParameterizedSqlStatementString(rest, soda, dPrice, useGreaterThanEqual);
			} else {
				query = buildParameterizedSqlStatementString(rest, soda, null, useGreaterThanEqual);
			}
			
			stmt = this.dbService.getConnection().prepareStatement(query);
			if(rest != null) {
				stmt.setString(numParams, rest);
				numParams++;
			}
			if(soda != null) {
				stmt.setString(numParams, soda);
				numParams++;
			}
			if(price != null && !price.isEmpty()) {
				stmt.setDouble(numParams, dPrice);
			}
			
			ResultSet rs = stmt.executeQuery();
			return parseResults(rs);
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Failed to retrieve sodas by restaurant.");
			ex.printStackTrace();
			return new ArrayList<SodaByRestaurant>();
		}
		
	}
	/**
	 * Creates a string containing ? in the correct places in the SQL statement based on the filter information provided.
	 * 
	 * @param rest - The restaurant to match
	 * @param soda - The soda to match
	 * @param price - The price to compare to
	 * @param useGreaterThanEqual - If true, the prices returned should be greater than or equal to what's given, otherwise less than or equal.
	 * @return A string representing the SQL statement to be executed 
	 */
	private String buildParameterizedSqlStatementString(String rest, String soda, Double price, boolean useGreaterThanEqual) {
		String sqlStatement = "SELECT Restaurant, Soda, Manufacturer, RestaurantContact, Price \nFROM SodasByRestaurant\n";
		ArrayList<String> wheresToAdd = new ArrayList<String>();

		if (rest != null) {
			wheresToAdd.add("Restaurant = ?");
		}
		if (soda != null) {
			wheresToAdd.add("Soda = ?");
		}
		if (price != null) {
			if (useGreaterThanEqual) {
				wheresToAdd.add("Price >= ?");
			} else {
				wheresToAdd.add("Price <= ?");
			}
		}
		boolean isFirst = true;
		while (wheresToAdd.size() > 0) {
			if (isFirst) {
				sqlStatement = sqlStatement + " WHERE " + wheresToAdd.remove(0);
				isFirst = false;
			} else {
				sqlStatement = sqlStatement + " AND " + wheresToAdd.remove(0);
			}
		}
		return sqlStatement;
	}

	private ArrayList<SodaByRestaurant> parseResults(ResultSet rs) {
		try {
			ArrayList<SodaByRestaurant> sodasByRestaurants = new ArrayList<SodaByRestaurant>();
			int restNameIndex = rs.findColumn("Restaurant");
			int sodaNameIndex = rs.findColumn("Soda");
			int manfIndex = rs.findColumn("Manufacturer");
			int restContactIndex = rs.findColumn("RestaurantContact");
			int priceIndex = rs.findColumn("Price");
			while (rs.next()) {
				sodasByRestaurants.add(new SodaByRestaurant(rs.getString(restNameIndex), rs.getString(sodaNameIndex),
						rs.getString(manfIndex), rs.getString(restContactIndex), rs.getDouble(priceIndex)));
			}
			return sodasByRestaurants;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,
					"An error ocurred while retrieving sodas by restaurants. See printed stack trace.");
			ex.printStackTrace();
			return new ArrayList<SodaByRestaurant>();
		}

	}


}
