package ssparsing;

public class Game {

	public Team team1;
	public Team team2;
	
	public Game(String t1, String t2) {
		team1 = new Team(t1);
		team2 = new Team(t2);
		
	}
}
