package pente;

import pente.Game;
import pente.Player;
import pente.Players.*;

public class Pente{

	public static void main(String[] args){
		Game game = new Game(2, 19);
		game.addPlayer(1, new RandomPlayer(1));
		game.addPlayer(2, new RandomPlayer(2));
		int winner = game.playGame();

		if(winner == -1){
			System.out.println("No one wins!");
		}
		else{
			System.out.println(String.format("Winner is player %d!", winner));
		}
	}
}