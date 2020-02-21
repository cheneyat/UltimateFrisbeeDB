package ui;

import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;

import ssparsing.Game;
import ssparsing.SSParser;
import uf.services.DatabaseConnectionService;
import uf.services.GameService;
import uf.services.PlayerService;
import uf.services.PlayerStatsService;
import uf.services.PointService;
import uf.services.ThrowService;
import uf.services.TeamService;
import uf.services.PlaysOnService;
import uf.services.TeamStatsService;

public class Application {
	
	public JFrame frame;
	private PlayerService playerService = null;
	private PointService pointService;
	private ThrowService throwService;
	private TeamService teamService;
	private PlaysOnService playsOnService;
	private GameService gameService;
	private PlayerStatsService playerStatsService;
	private TeamStatsService teamStatsService;
	
	private String[][] playerStatsData;
	private JTable playerStatsTable;
	
	private String[][] teamStatsData;
	private JTable teamStatsTable;
	
	private JPanel insertPanel;
	private JPanel viewPanel;
	
	public Application(PlayerService playerService, PointService pointService, ThrowService throwService, 
			TeamService teamService, PlaysOnService playsOnService, GameService gameService, PlayerStatsService pstats, TeamStatsService tstats) {
		this.playerService = playerService;
		this.pointService = pointService;
		this.throwService = throwService;
		this.teamService = teamService;
		this.playsOnService = playsOnService;
		this.gameService = gameService;
		this.playerStatsService = pstats;
		this.teamStatsService = tstats;
		
		this.playerStatsData = this.playerStatsService.statsByPlayer(null, null, null);
		this.teamStatsData = this.teamStatsService.statsByTeam(null);
		
		this.insertPanel = new JPanel();
		this.insertPanel.setLayout(null);
		this.insertPanel.setSize(800,1000);
		this.viewPanel = new JPanel();
		this.viewPanel.setLayout(null);
		this.viewPanel.setSize(800,1000);
		
		initializeFrame();
		initializeInsertUI();
		initializeViewUI();
		
		frame.getContentPane().add(insertPanel);
		
		frame.setLayout(null);
		frame.setVisible(true);
		
		
	}
	
	public void initializeFrame() {
		frame = new JFrame();
		frame.setSize(800,1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		addMenuBar();
		frame.setTitle("Ultimate Frisbee Stat Tracking App");
	}
	
	public void initializeInsertUI() {
		addTeamGuiStuff();
		addPointGuiStuff();
		addPlayerGuiStuff();
		//addThrowStuff();
		addPlaysOnGuiStuff();
		addImportSpreadSheetStuff();
	}
	
	public void initializeViewUI() {
		addPlayerStatsView();
		addTeamStatsView();
	}
	
	private void addTeamStatsView() {
		JLabel TeamNameLabel = new JLabel();
		TeamNameLabel.setText("Enter Team Name to search:");
		TeamNameLabel.setBounds(20, 520, 170, 30);
		this.viewPanel.add(TeamNameLabel);
		
		JTextField TeamNameText = new JTextField();
		TeamNameText.setEditable(true);
		TeamNameText.setBounds(20, 560, 170, 30);
		this.viewPanel.add(TeamNameText);
		
		JButton searchButton = new JButton();
		searchButton.setText("Search!");
		searchButton.setBounds(20, 600, 170, 30);
		
		String[] columnNames = {"Team", "Wins", "Losses", "Points For", "Points Against"};
		this.teamStatsTable = new JTable(this.teamStatsData, columnNames);
		
		JScrollPane sp = new JScrollPane(); 
        sp.getViewport().add(this.teamStatsTable);
        sp.setBounds(0, 700, 800, 300);
        this.viewPanel.add(sp);
		
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				teamStatsTable = new JTable(teamStatsService.statsByTeam(TeamNameText.getText()),
									columnNames);
				sp.getViewport().add(teamStatsTable);
			}
			
		});
		this.viewPanel.add(searchButton);
	}
	
	private void addPlayerStatsView() {
		JLabel USAUIDLabel = new JLabel();
		USAUIDLabel.setText("Enter USAUID to search:");
		USAUIDLabel.setBounds(20, 20, 150, 30);
		this.viewPanel.add(USAUIDLabel);
		
		JTextField USAUIDText = new JTextField();
		USAUIDText.setEditable(true);
		USAUIDText.setBounds(20, 60, 150, 30);
		this.viewPanel.add(USAUIDText);
		
		JLabel fNameLabel = new JLabel();
		fNameLabel.setText("Enter first name to search:");
		fNameLabel.setBounds(200, 20, 200, 30);
		this.viewPanel.add(fNameLabel);
		
		JTextField fNameText = new JTextField();
		fNameText.setEditable(true);
		fNameText.setBounds(200, 60, 150, 30);
		this.viewPanel.add(fNameText);
		
		JLabel lNameLabel = new JLabel();
		lNameLabel.setText("Enter last name to search:");
		lNameLabel.setBounds(380, 20, 200, 30);
		this.viewPanel.add(lNameLabel);
		
		JTextField lNameText = new JTextField();
		lNameText.setEditable(true);
		lNameText.setBounds(380, 60, 150, 30);
		this.viewPanel.add(lNameText);
		
		JButton searchButton = new JButton();
		searchButton.setText("Search!");
		searchButton.setBounds(200, 100, 150, 30);
		
		String[] columnNames = {"USAUID", "First Name", "Last Name", "Completed Passes", "Assists",
				"Points Scored", "Throwaways", "Completion Percentage"};
		this.playerStatsTable = new JTable(this.playerStatsData, columnNames);
		
		JScrollPane sp = new JScrollPane(); 
        sp.getViewport().add(this.playerStatsTable);
        sp.setBounds(0, 200, 800, 300);
        this.viewPanel.add(sp);
		
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				playerStatsTable = new JTable(playerStatsService.statsByPlayer(USAUIDText.getText(), fNameText.getText(), lNameText.getText()),
									columnNames);
				sp.getViewport().add(playerStatsTable);
			}
			
		});
		this.viewPanel.add(searchButton);
		
		
        
	}
	
	public void switchGui(JPanel panel) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel);
		
		frame.repaint();
	}
	
	public void addMenuBar() {
		//Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		JCheckBoxMenuItem cbMenuItem;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("Change Views...");

		JMenuItem insertViewButton = new JMenuItem("Insert Data View");
		menu.add(insertViewButton);
		insertViewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchGui(insertPanel);
				
			}
		});
		
		
		JMenuItem viewDataViewButton = new JMenuItem("View Data View");
		menu.add(viewDataViewButton);
		menuBar.add(menu);
		viewDataViewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchGui(viewPanel);
			}
		});


		frame.setJMenuBar(menuBar);
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
		
		insertPanel.add(teamTextField);
		insertPanel.add(teamButton);
		insertPanel.add(teamLabel);
		insertPanel.add(teamIDTextField);
		insertPanel.add(teamIDLabel);
		
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
		
		insertPanel.add(teamIDLabel);
		insertPanel.add(USAUIDLabel);
		insertPanel.add(teamIDTextField);
		insertPanel.add(PlayerIDTextField);
		insertPanel.add(addToTeamButton);
	}
	
	
	public void addImportSpreadSheetStuff() {
		
		JLabel importSpreadSheetLabel = new JLabel();
		importSpreadSheetLabel.setText("File Path");
		importSpreadSheetLabel.setBounds(20,900, 200, 30);
		
		JTextField spreadSheetTextField = new JTextField();
		spreadSheetTextField.setEditable(true);
		spreadSheetTextField.setBounds(100, 900, 400, 30);
		
		JButton importButton = new JButton();
		importButton.setText("Import SpreadSheet");
		importButton.setEnabled(true);
		importButton.setBounds(520, 900, 150, 30);
		
		insertPanel.add(importSpreadSheetLabel);
		insertPanel.add(spreadSheetTextField);
		insertPanel.add(importButton);
		
		importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SSParser s = new SSParser(spreadSheetTextField.getText());
				s.parse(throwService, playerService, pointService, teamService, gameService);
				
			}
			
		});
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
		
		insertPanel.add(USAUIDTextField);
		insertPanel.add(USAUIDLabel);
		insertPanel.add(fNameLabel);
		insertPanel.add(fNameTextField);
		insertPanel.add(lNameLabel);
		insertPanel.add(lNameTextField);
		insertPanel.add(podIDLabel);
		insertPanel.add(podIDTextField);

		insertPanel.add(playerButton);
	}
	
	public void addPointGuiStuff() {
		
		// GameID
		JLabel gameID = new JLabel();
		gameID.setText("GameID");
		gameID.setBounds(420,360, 200, 30);
		
		JTextField gameIDtext = new JTextField();
		gameIDtext.setEditable(true);
		gameIDtext.setBounds(520, 360, 200, 30);
		
		insertPanel.add(gameID);
		insertPanel.add(gameIDtext);
		
		// PointID
		JLabel pointID = new JLabel();
		pointID.setText("PointID");
		pointID.setBounds(20,360, 200, 30);
		
		JTextField pointIDtext = new JTextField();
		pointIDtext.setEditable(true);
		pointIDtext.setBounds(120, 360, 200, 30);
		
		insertPanel.add(pointIDtext);
		insertPanel.add(pointID);
		
		// Offense
		JLabel offteamLabel = new JLabel();
		offteamLabel.setText("Off Team");
		offteamLabel.setBounds(20,400, 200, 30);
		
		JTextField offteamTextField = new JTextField();
		offteamTextField.setEditable(true);
		offteamTextField.setBounds(120, 400, 200, 30);
		
		insertPanel.add(offteamTextField);
		insertPanel.add(offteamLabel);
		
		// Defense
		JLabel defteamLabel = new JLabel();
		defteamLabel.setText("Def Team");
		defteamLabel.setBounds(420,400, 200, 30);
		
		JTextField defteamTextField = new JTextField();
		defteamTextField.setEditable(true);
		defteamTextField.setBounds(520, 400, 200, 30);
		
		insertPanel.add(defteamTextField);
		insertPanel.add(defteamLabel);
		
		// Scoring Player
		JLabel scoringPlayerLabel = new JLabel();
		scoringPlayerLabel.setText("Scoring Player");
		scoringPlayerLabel.setBounds(20,450, 200, 30);
		
		JTextField scoringPlayerTextField = new JTextField();
		scoringPlayerTextField.setEditable(true);
		scoringPlayerTextField.setBounds(120, 450, 200, 30);
		
		insertPanel.add(scoringPlayerTextField);
		insertPanel.add(scoringPlayerLabel);
		
		// Assisting Player
		JLabel assistingPlayerLabel = new JLabel();
		assistingPlayerLabel.setText("Assisting Player");
		assistingPlayerLabel.setBounds(420,450, 200, 30);
		
		JTextField assistingPlayerTextField = new JTextField();
		assistingPlayerTextField.setEditable(true);
		assistingPlayerTextField.setBounds(520, 450, 200, 30);
		
		insertPanel.add(assistingPlayerLabel);
		insertPanel.add(assistingPlayerTextField);
		
		
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
		
		
		insertPanel.add(defteamButton);
	}
	
	
	// TODO: Add GameID Field so we can appropriately add throws
	
//	public void addThrowStuff() {
//		
//		JLabel throwingPlayerLabel = new JLabel();
//		throwingPlayerLabel.setText("Throwing Player");
//		throwingPlayerLabel.setBounds(20, 550, 200, 30);
//		
//		insertPanel.add(throwingPlayerLabel);
//		
//		JTextField throwingPlayerText = new JTextField();
//		throwingPlayerText.setEditable(true);
//		throwingPlayerText.setBounds(120, 550, 200, 30);
//		
//		insertPanel.add(throwingPlayerText);
//		
//		JLabel blockingPlayerLabel = new JLabel();
//		blockingPlayerLabel.setText("Blocking Player");
//		blockingPlayerLabel.setBounds(420, 550, 200, 30);
//		
//		insertPanel.add(blockingPlayerLabel);
//		
//		JTextField blockingPlayerText = new JTextField();
//		blockingPlayerText.setEditable(true);
//		blockingPlayerText.setBounds(520, 550, 200, 30);
//		
//		insertPanel.add(blockingPlayerText);
//		
//		JLabel catchingPlayerLabel = new JLabel();
//		catchingPlayerLabel.setText("Catching Player");
//		catchingPlayerLabel.setBounds(20, 600, 200, 30);
//		
//		insertPanel.add(catchingPlayerLabel);
//		
//		JTextField catchingPlayerText = new JTextField();
//		catchingPlayerText.setEditable(true);
//		catchingPlayerText.setBounds(120, 600, 200, 30);
//		
//		insertPanel.add(catchingPlayerText);
//		
//		JLabel hangtimeLabel = new JLabel();
//		hangtimeLabel.setText("Hangtime");
//		hangtimeLabel.setBounds(420, 600, 200, 30);
//		
//		insertPanel.add(hangtimeLabel);
//		
//		JTextField hangtimeText = new JTextField();
//		hangtimeText.setEditable(true);
//		hangtimeText.setBounds(520, 600, 200, 30);
//		
//		insertPanel.add(hangtimeText);
//		
//		JLabel type = new JLabel();
//		type.setText("Type");
//		type.setBounds(20, 650, 200, 30);
//		
//		insertPanel.add(type);
//		
//		String[] types = {"Completed Pass", "Blocked Pass", "Pull"};
//		JComboBox typeList = new JComboBox(types);
//		typeList.setBounds(120, 650, 200, 30);
//		
//		insertPanel.add(typeList);
//		
//		JLabel pointIDLabel = new JLabel();
//		pointIDLabel.setText("PointID");
//		pointIDLabel.setBounds(420, 650, 200, 30);
//		
//		insertPanel.add(pointIDLabel);
//		
//		JTextField pointIDText = new JTextField();
//		pointIDText.setEditable(true);
//		pointIDText.setBounds(520, 650, 200, 30);
//		
//		insertPanel.add(pointIDText);
//		
//		JLabel isGoalText = new JLabel();
//		isGoalText.setText("Is goal?");
//		isGoalText.setBounds(20, 700, 150, 30);
//		
//		insertPanel.add(isGoalText);
//		
//		JRadioButton yesButton = new JRadioButton("yes");
//		yesButton.setBounds(120, 700, 100, 30);
//		JRadioButton noButton = new JRadioButton("no");
//		noButton.setBounds(220, 700, 100, 30);
//		
//		insertPanel.add(yesButton);
//		insertPanel.add(noButton);
//		
//		ButtonGroup group = new ButtonGroup();
//		group.add(yesButton);
//		group.add(noButton);
//		
//		
//		JButton throwButton = new JButton();
//		throwButton.setText("Add Throw");
//		throwButton.setEnabled(true);
//		throwButton.setBounds(520, 700, 150, 30);
//		
//		throwButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(typeList.getSelectedItem().equals("Completed Pass")) {
//					if(yesButton.isSelected()) {
//						throwService.addCompletedPass(Integer.parseInt(pointIDText.getText()), Integer.parseInt(throwingPlayerText.getText()),
//								Integer.parseInt(catchingPlayerText.getText()), true);
//					} else if(noButton.isSelected()) {
//						throwService.addCompletedPass(Integer.parseInt(pointIDText.getText()), Integer.parseInt(throwingPlayerText.getText()),
//								Integer.parseInt(catchingPlayerText.getText()), false);
//					}
//				} else if(typeList.getSelectedItem().equals("Blocked Pass")) {
//					throwService.addBlockedPass(Integer.parseInt(pointIDText.getText()), Integer.parseInt(throwingPlayerText.getText()),
//							Integer.parseInt(blockingPlayerText.getText()));
//				} else if(typeList.getSelectedItem().equals("Pull")) {
//					throwService.addPull(Integer.parseInt(pointIDText.getText()), Integer.parseInt(throwingPlayerText.getText()),
//							Integer.parseInt(hangtimeText.getText()));
//				}
//			}
//			
//		});
//		
//		insertPanel.add(throwButton);
//		
//	}
	

}
