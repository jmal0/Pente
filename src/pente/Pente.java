package pente;

import pente.Game;
import pente.Player;
import pente.RandomPlayer;

public class Pente{

	public static void main(String[] args){

		Game game = new Game(2, 19);
		game.addPlayer(1, new RandomPlayer(1));
		game.addPlayer(2, new RandomPlayer(2));
		game.playGame();
	}
}