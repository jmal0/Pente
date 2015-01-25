package pente;

import pente.Player;
import pente.Move;

public class RandomPlayer extends Player{

	public RandomPlayer(int num){
		super(num);
	}

	// TODO lol
	public Move getMove(Board boardState){
		return new Move(0,0,0);
	}	
}