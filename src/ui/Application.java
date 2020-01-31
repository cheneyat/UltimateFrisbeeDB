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
		
		
	}
	
	public void addPointGuiStuff() {
		
	}
	

}
