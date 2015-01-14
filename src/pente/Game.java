package pente;

public class Game{
	private Board board;
	private Player[] players;

	public Game(int numPlayers, int dimension){
		players = new Player[numPlayers];
		for(int i = 0; i < numPlayers; i++){
			players[i] = i+1;
		}

		Board = new Board(dimension, numPlayers);
	}
}