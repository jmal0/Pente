package pente.Players;

import pente.*;
import java.util.Random;
import java.util.ArrayList;

public class RandomPlayer extends Player{
	private Random rand;

	public RandomPlayer(int num){
		super(num);
		rand = new Random();
	}

	/**
	* Picks a random move from all possible moves
	*/
	public Move getMove(Board boardState){
		ArrayList<Move> moves = boardState.getMoves();
		Move move = moves.get(rand.nextInt(moves.size()));
		return new Move(this.getNumber(), move.row, move.col);
	}	
}