package ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Application {
	
	public JFrame frame;
	
	public Application() {
		frame = new JFrame();
		frame.setSize(800,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Ultimate Frisbee Stat Tracking App");
		addTeamGuiStuff();
		addPointGuiStuff();
		addPlayerGuiStuff();
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	
	public void addTeamGuiStuff() {
		
		JLabel teamLabel = new JLabel();
		teamLabel.setText("Team Name");
		teamLabel.setBounds(20,200, 200, 30);
		
		JTextField teamTextField = new JTextField();
		teamTextField.setEditable(true);
		teamTextField.setBounds(100, 200, 200, 30);
		
		JButton teamButton = new JButton();
		teamButton.setText("Add Team");
		teamButton.setEnabled(true);
		teamButton.setBounds(150, 250, 150, 30);
		
		frame.add(teamTextField);
		frame.add(teamButton);
		frame.add(teamLabel);
		
	}
	
	public void addPlayerGuiStuff() {
		
		JLabel USAUIDLabel = new JLabel();
		USAUIDLabel.setText("USAUID");
		USAUIDLabel.setBounds(20,20, 200, 30);
		
		JTextField USAUIDTextField = new JTextField();
		USAUIDTextField.setEditable(true);
		USAUIDTextField.setBounds(100, 20, 200, 30);
		
		JLabel fNameLabel = new JLabel();
		fNameLabel.setText("First Name");
		fNameLabel.setBounds(320,20, 200, 30);
		
		JTextField fNameTextField = new JTextField();
		fNameTextField.setEditable(true);
		fNameTextField.setBounds(400, 20, 200, 30);
		
		JLabel lNameLabel = new JLabel();
		lNameLabel.setText("Last Name");
		lNameLabel.setBounds(20, 70, 200, 30);
		
		JTextField lNameTextField = new JTextField();
		lNameTextField.setEditable(true);
		lNameTextField.setBounds(100, 70, 200, 30);

		JLabel podIDLabel = new JLabel();
		podIDLabel.setText("Pod ID");
		podIDLabel.setBounds(320, 70, 200, 30);
		
		JTextField podIDTextField = new JTextField();
		podIDTextField.setEditable(true);
		podIDTextField.setBounds(400, 70, 200, 30);
		
		JButton playerButton = new JButton();
		playerButton.setText("Add Player");
		playerButton.setEnabled(true);
		playerButton.setBounds(400, 120, 150, 30);
		
		frame.add(USAUIDTextField);
		frame.add(USAUIDLabel);
		frame.add(fNameLabel);
		frame.add(fNameTextField);
		frame.add(lNameLabel);
		frame.add(lNameTextField);
		frame.add(podIDLabel);
		frame.add(podIDTextField);

		frame.add(playerButton);
	}
	
	public void addPointGuiStuff() {
		
		
		// Offense
		JLabel offteamLabel = new JLabel();
		offteamLabel.setText("Off Team");
		offteamLabel.setBounds(20,400, 200, 30);
		
		JTextField offteamTextField = new JTextField();
		offteamTextField.setEditable(true);
		offteamTextField.setBounds(120, 400, 200, 30);
		
		frame.add(offteamTextField);
		frame.add(offteamLabel);
		
		// Defense
		JLabel defteamLabel = new JLabel();
		defteamLabel.setText("Def Team");
		defteamLabel.setBounds(420,400, 200, 30);
		
		JTextField defteamTextField = new JTextField();
		defteamTextField.setEditable(true);
		defteamTextField.setBounds(520, 400, 200, 30);
		
		frame.add(defteamTextField);
		frame.add(defteamLabel);
		
		// Scoring Player
		JLabel scoringPlayerLabel = new JLabel();
		scoringPlayerLabel.setText("Scoring Player");
		scoringPlayerLabel.setBounds(20,450, 200, 30);
		
		JTextField scoringPlayerTextField = new JTextField();
		scoringPlayerTextField.setEditable(true);
		scoringPlayerTextField.setBounds(120, 450, 200, 30);
		
		frame.add(scoringPlayerTextField);
		frame.add(scoringPlayerLabel);
		
		// Assisting Player
		JLabel assistingPlayerLabel = new JLabel();
		assistingPlayerLabel.setText("Assisting Player");
		assistingPlayerLabel.setBounds(420,450, 200, 30);
		
		JTextField assistingPlayerTextField = new JTextField();
		assistingPlayerTextField.setEditable(true);
		assistingPlayerTextField.setBounds(520, 450, 200, 30);
		
		frame.add(assistingPlayerLabel);
		frame.add(assistingPlayerTextField);
		
		
		JButton defteamButton = new JButton();
		defteamButton.setText("Add Point");
		defteamButton.setEnabled(true);
		defteamButton.setBounds(550, 500, 150, 30);
		
		
		
		
		frame.add(defteamButton);
	}
	

}
