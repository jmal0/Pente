package pente;

import pente.Board;
import pente.Player;

public class Game{
	private Board board;
	private Player[] players;

	public Game(int numPlayers, int dimension){
		players = new Player[numPlayers];
		board = new Board(dimension, numPlayers);
	}

	public void addPlayer(int which, Player p){
		players[which] = p;
	}
	/*
		TODO: Account for players getting knocked out in multiplayer?
		@return The winner
	*/
	public Player playGame(){
		// First player is player 0
		int nextPlayer = 0;
		while(!board.gameOver()){
			// Request move from player whose turn it is
			Move m = players[nextPlayer].getMove(board);
			board.makeMove(m);

			displayBoard();
			
			nextPlayer = (nextPlayer + 1)%players.length;
		}

		return players[board.getWinner()];
	}

	/*
		Prints the board to stdout. Override this method to display by another means
	*/
	public void displayBoard(){
		System.out.println(board);
	}
}