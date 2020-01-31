package sodabase.services;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;


public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return true;
	}
	
	public boolean login(String username, String password) {
		ResultSet rs;
		byte[] salt = {};
		String dbPHash = "";
		if (username.isEmpty() || username == null) {
			return false;
		}
		try {
			String query = "SELECT PasswordSalt, PasswordHash \nFROM [User] \nWHERE Username = ?";
			PreparedStatement stmt = null;
			stmt = this.dbService.getConnection().prepareStatement(query);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			while (rs.next()) {
				salt = rs.getBytes(rs.findColumn("PasswordSalt"));
				dbPHash = rs.getString(rs.findColumn("PasswordHash"));
			}
			String pHash = hashPassword(salt, password);
			if (pHash.equals(dbPHash)) {
				return true;
			}
		}
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Login failed.");
			ex.printStackTrace();
		}
		
		return false;
	}

	public boolean register(String username, String password) {
		byte[] salt = getNewSalt();
		String pHash = hashPassword(salt, password);
		boolean result = false;
		int errCode = -1;
		CallableStatement stmt = null;
		try {
			stmt = this.dbService.getConnection().prepareCall("{? = call [dbo].[Register](?, ?, ?)}");
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, username);
			stmt.setBytes(3, salt);
			stmt.setString(4, pHash);
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
		else {
			System.out.println(errCode);
			JOptionPane.showMessageDialog(null, "ERROR: Registration failed.");
		}
		return result;
	}
	
	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}
