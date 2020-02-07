package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import uf.services.DatabaseConnectionService;
import uf.services.PlayerService;
import uf.services.PointService;
import uf.services.ThrowService;
import uf.services.TeamService;
import uf.services.PlaysOnService;

public class Application {
	
	public JFrame frame;
	private PlayerService playerService = null;
	private PointService pointService;
	private ThrowService throwService;
	private TeamService teamService;
	private PlaysOnService playsOnService;
	
	public Application(PlayerService playerService, PointService pointService, ThrowService throwService, TeamService teamService, PlaysOnService playsOnService) {
		this.playerService = playerService;
		this.pointService = pointService;
		this.throwService = throwService;
		this.teamService = teamService;
		this.playsOnService = playsOnService;
		frame = new JFrame();
		frame.setSize(800,1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Ultimate Frisbee Stat Tracking App");
		addTeamGuiStuff();
		addPointGuiStuff();
		addPlayerGuiStuff();
		addThrowStuff();
		addPlaysOnGuiStuff();
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	
	public void addTeamGuiStuff() {
		
		JLabel teamLabel = new JLabel();
		teamLabel.setText("Team Name");
		teamLabel.setBounds(20,200, 200, 30);
		
		JLabel teamIDLabel = new JLabel();
		teamIDLabel.setText("Team ID");
		teamIDLabel.setBounds(320,200, 200, 30);
		
		JTextField teamTextField = new JTextField();
		teamTextField.setEditable(true);
		teamTextField.setBounds(100, 200, 200, 30);
		
		JTextField teamIDTextField = new JTextField();
		teamIDTextField.setEditable(true);
		teamIDTextField.setBounds(400, 200, 200, 30);
		
		JButton teamButton = new JButton();
		teamButton.setText("Add Team");
		teamButton.setEnabled(true);
		teamButton.setBounds(400, 250, 150, 30);
		
		teamButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				teamService.addTeam(Integer.parseInt(teamIDTextField.getText()), teamTextField.getText());			
			}
			
		});
		
		frame.add(teamTextField);
		frame.add(teamButton);
		frame.add(teamLabel);
		frame.add(teamIDTextField);
		frame.add(teamIDLabel);
		
	}
	
	public void addPlaysOnGuiStuff() {
		JLabel teamIDLabel = new JLabel();
		teamIDLabel.setText("Team ID");
		teamIDLabel.setBounds(320,800, 200, 30);
		
		JLabel USAUIDLabel = new JLabel();
		USAUIDLabel.setText("USAUID");
		USAUIDLabel.setBounds(20,800, 200, 30);
		
		JTextField teamIDTextField = new JTextField();
		teamIDTextField.setEditable(true);
		teamIDTextField.setBounds(400, 800, 200, 30);
		
		JTextField PlayerIDTextField = new JTextField();
		PlayerIDTextField.setEditable(true);
		PlayerIDTextField.setBounds(100, 800, 200, 30);
		
		JButton addToTeamButton = new JButton();
		addToTeamButton.setText("Add To Team");
		addToTeamButton.setEnabled(true);
		addToTeamButton.setBounds(400, 850, 150, 30);
		
		addToTeamButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playsOnService.addPlayerToTeam(Integer.parseInt(PlayerIDTextField.getText()), Integer.parseInt(teamIDTextField.getText()));			
			}
			
		});
		
		frame.add(teamIDLabel);
		frame.add(USAUIDLabel);
		frame.add(teamIDTextField);
		frame.add(PlayerIDTextField);
		frame.add(addToTeamButton);
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
		playerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playerService.addPlayer(Integer.parseInt(USAUIDTextField.getText()), fNameTextField.getText(),
						lNameTextField.getText(), podIDTextField.getText());
				
			}
			
		});
		
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
		
		// GameID
		JLabel gameID = new JLabel();
		gameID.setText("GameID");
		gameID.setBounds(420,360, 200, 30);
		
		JTextField gameIDtext = new JTextField();
		gameIDtext.setEditable(true);
		gameIDtext.setBounds(520, 360, 200, 30);
		
		frame.add(gameID);
		frame.add(gameIDtext);
		
		// PointID
		JLabel pointID = new JLabel();
		pointID.setText("PointID");
		pointID.setBounds(20,360, 200, 30);
		
		JTextField pointIDtext = new JTextField();
		pointIDtext.setEditable(true);
		pointIDtext.setBounds(120, 360, 200, 30);
		
		frame.add(pointIDtext);
		frame.add(pointID);
		
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
		
		defteamButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pointService.AddPoint(Integer.parseInt(pointIDtext.getText()), Integer.parseInt(gameIDtext.getText()), offteamTextField.getText(), defteamTextField.getText(),
						assistingPlayerTextField.getText(), scoringPlayerTextField.getText());
				
			}
			
		});
		
		
		frame.add(defteamButton);
	}
	
	public void addThrowStuff() {
		
		JLabel throwingPlayerLabel = new JLabel();
		throwingPlayerLabel.setText("Throwing Player");
		throwingPlayerLabel.setBounds(20, 550, 200, 30);
		
		frame.add(throwingPlayerLabel);
		
		JTextField throwingPlayerText = new JTextField();
		throwingPlayerText.setEditable(true);
		throwingPlayerText.setBounds(120, 550, 200, 30);
		
		frame.add(throwingPlayerText);
		
		JLabel blockingPlayerLabel = new JLabel();
		blockingPlayerLabel.setText("Blocking Player");
		blockingPlayerLabel.setBounds(420, 550, 200, 30);
		
		frame.add(blockingPlayerLabel);
		
		JTextField blockingPlayerText = new JTextField();
		blockingPlayerText.setEditable(true);
		blockingPlayerText.setBounds(520, 550, 200, 30);
		
		frame.add(blockingPlayerText);
		
		JLabel catchingPlayerLabel = new JLabel();
		catchingPlayerLabel.setText("Catching Player");
		catchingPlayerLabel.setBounds(20, 600, 200, 30);
		
		frame.add(catchingPlayerLabel);
		
		JTextField catchingPlayerText = new JTextField();
		catchingPlayerText.setEditable(true);
		catchingPlayerText.setBounds(120, 600, 200, 30);
		
		frame.add(catchingPlayerText);
		
		JLabel hangtimeLabel = new JLabel();
		hangtimeLabel.setText("Hangtime");
		hangtimeLabel.setBounds(420, 600, 200, 30);
		
		frame.add(hangtimeLabel);
		
		JTextField hangtimeText = new JTextField();
		hangtimeText.setEditable(true);
		hangtimeText.setBounds(520, 600, 200, 30);
		
		frame.add(hangtimeText);
		
		JLabel type = new JLabel();
		type.setText("Type");
		type.setBounds(20, 650, 200, 30);
		
		frame.add(type);
		
		String[] types = {"Completed Pass", "Blocked Pass", "Pull"};
		JComboBox typeList = new JComboBox(types);
		typeList.setBounds(120, 650, 200, 30);
		
		frame.add(typeList);
		
		JLabel pointIDLabel = new JLabel();
		pointIDLabel.setText("PointID");
		pointIDLabel.setBounds(420, 650, 200, 30);
		
		frame.add(pointIDLabel);
		
		JTextField pointIDText = new JTextField();
		pointIDText.setEditable(true);
		pointIDText.setBounds(520, 650, 200, 30);
		
		frame.add(pointIDText);
		
		JLabel isGoalText = new JLabel();
		isGoalText.setText("Is goal?");
		isGoalText.setBounds(20, 700, 150, 30);
		
		frame.add(isGoalText);
		
		JRadioButton yesButton = new JRadioButton("yes");
		yesButton.setBounds(120, 700, 100, 30);
		JRadioButton noButton = new JRadioButton("no");
		noButton.setBounds(220, 700, 100, 30);
		
		frame.add(yesButton);
		frame.add(noButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(yesButton);
		group.add(noButton);
		
		
		JButton throwButton = new JButton();
		throwButton.setText("Add Throw");
		throwButton.setEnabled(true);
		throwButton.setBounds(520, 700, 150, 30);
		
		throwButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(typeList.getSelectedItem().equals("Completed Pass")) {
					if(yesButton.isSelected()) {
						throwService.addCompletedPass(Integer.parseInt(pointIDText.getText()), Integer.parseInt(throwingPlayerText.getText()),
								Integer.parseInt(catchingPlayerText.getText()), true);
					} else if(noButton.isSelected()) {
						throwService.addCompletedPass(Integer.parseInt(pointIDText.getText()), Integer.parseInt(throwingPlayerText.getText()),
								Integer.parseInt(catchingPlayerText.getText()), false);
					}
				} else if(typeList.getSelectedItem().equals("Blocked Pass")) {
					throwService.addBlockedPass(Integer.parseInt(pointIDText.getText()), Integer.parseInt(throwingPlayerText.getText()),
							Integer.parseInt(blockingPlayerText.getText()));
				} else if(typeList.getSelectedItem().equals("Pull")) {
					throwService.addPull(Integer.parseInt(pointIDText.getText()), Integer.parseInt(throwingPlayerText.getText()),
							Integer.parseInt(hangtimeText.getText()));
				}
			}
			
		});
		
		frame.add(throwButton);
		
	}
	

}
