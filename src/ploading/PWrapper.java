package ploading;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PWrapper {

	private Properties properties = new Properties();
	
	
	public PWrapper() {
		
		properties = new Properties();
	}
	
	
	public PWrapper(String filePath) {
		
		InputStream stream = null;
		try {
			stream = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.err.println("File does not exist at path: " + filePath);
		}
		
		try {
			properties.load(stream);
		} catch (IOException e) {
			
			System.err.println("Failed to intialize the properties file.");
		}
	}
	
	public String getValue(String key) {
		return properties.getProperty(key);
	}
	
	public void setValue(String key, String value) {
		properties.setProperty(key, value);
	}
	
}
