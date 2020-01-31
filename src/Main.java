import sodabase.ui.ApplicationRunner;
import uf.services.DatabaseConnectionService;
import ui.Application;

public class Main {

	public static void main(String[] args) {
		
		DatabaseConnectionService dbService = new DatabaseConnectionService("golem.csse.rose-hulman.edu","UltimateFrisbeeDatabase20");
		
		if (dbService.connect("UFAdmin20", "")) {
			System.out.println("Connected to db");
		}
		
		Application app = new Application();
		
//		ApplicationRunner appRunner = new ApplicationRunner();
//		appRunner.runApplication(args);
	}

}
