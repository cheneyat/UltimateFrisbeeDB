import java.util.Properties;

import javax.swing.JOptionPane;

import ploading.PWrapper;
import sodabase.ui.ApplicationRunner;
import ssparsing.SSParser;
import uf.services.DatabaseConnectionService;
import uf.services.GameService;
import uf.services.PlayerService;
import uf.services.PlayerStatsService;
import uf.services.TeamStatsService;
import uf.services.PointService;
import uf.services.ThrowService;
import uf.services.TeamService;
import uf.services.PlaysOnService;
import ui.Application;

public class Main {

	public static void main(String[] args) {
		
		PWrapper pwrap = new PWrapper("src/app.properties");
		DatabaseConnectionService dbService = new DatabaseConnectionService(pwrap.getValue("serverName"), pwrap.getValue("databaseName"));
		if (dbService.connect(pwrap.getValue("serverUsername"), pwrap.getValue("serverPassword"))) {
			JOptionPane.showMessageDialog(null, "Connected to db");
			Application app = new Application(new PlayerService(dbService), new PointService(dbService), new ThrowService(dbService), new TeamService(dbService), new PlaysOnService(dbService), new GameService(dbService),
					new PlayerStatsService(dbService), new TeamStatsService(dbService));
		}
		else {
			JOptionPane.showMessageDialog(null, "Couldn't connect to database");
		}
		
		
//		ApplicationRunner appRunner = new ApplicationRunner();
//		appRunner.runApplication(args);
	}

}