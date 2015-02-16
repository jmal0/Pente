package pente;

import pente.Board;
import pente.Player;
import java.util.ArrayList;

public class Game{
	private Board board;
	private Player[] players;

	public Game(int numPlayers, int dimension){
		players = new Player[numPlayers];
		board = new Board(dimension, numPlayers);
	}

	public void addPlayer(int which, Player p){
		players[which-1] = p;
	}

	/*
		Plays the game, asking each player for their move on their turn. Displays board and captures between moves
		@return The number of the winner
	*/
	public int playGame(){
		// First player is player 2 (first move automatically played)
		int nextPlayer = 1;
		boolean success;
		while(!board.gameOver()){
			// Request move from player whose turn it is
			Move m = players[nextPlayer].getMove(board);
			System.out.println(m);
			success = board.makeMove(m);
			if(!success){
				System.out.println("ERROR");
				System.exit(-1);
			}

			printCaptures();
			displayBoard();
			System.out.println();
			
			nextPlayer = (nextPlayer + 1)%players.length;

			try{
				Thread.sleep(1000);
			} catch(InterruptedException ex){
				Thread.currentThread().interrupt();
			}
		}

		return board.getWinner();
	}

	/*
		Prints the board to stdout. Override this method to display by another means
	*/
	public void displayBoard(){
		System.out.println(board);
	}

	public void printCaptures(){
		int[] captures = this.board.getCaptures();
		for(int i = 0; i < captures.length; i++){
			System.out.println(String.format("Player %d: %d captures", i+1, captures[i]));
		}
	}
}