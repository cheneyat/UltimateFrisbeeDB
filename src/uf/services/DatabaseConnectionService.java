package uf.services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionService {

	//DO NOT EDIT THIS STRING, YOU WILL RECEIVE NO CREDIT FOR THIS TASK IF THIS STRING IS EDITED
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		//DO NOT CHANGE THIS METHOD
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		//TODO: Task 1
		//BUILD YOUR CONNECTION STRING HERE USING THE SAMPLE URL ABOVE
		String connectionURL = "jdbc:sqlserver://"
				+ serverName
                + ";databaseName="
                + databaseName
                + ";user="
                + user
                + ";password="
                + pass
                + ";encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
		
		try {
			connection = DriverManager.getConnection(connectionURL);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return this.databaseName;
	}

}
