import sodabase.ui.ApplicationRunner;
import uf.services.DatabaseConnectionService;
import uf.services.PlayerService;
import ui.Application;

public class Main {

	public static void main(String[] args) {
		
		DatabaseConnectionService dbService = new DatabaseConnectionService("golem.csse.rose-hulman.edu","UltimateFrisbeeDatabase20");
		
		if (dbService.connect("cheneyat", "")) {
			System.out.println("Connected to db");
		}
		
		Application app = new Application(new PlayerService(dbService));
		
//		ApplicationRunner appRunner = new ApplicationRunner();
//		appRunner.runApplication(args);
	}

}
