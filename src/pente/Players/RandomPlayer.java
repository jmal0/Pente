package pente;

import pente.Player;
import pente.Move;
import java.util.Random;
import java.util.ArrayList;

public class RandomPlayer extends Player{
	private Random rand;

	public RandomPlayer(int num){
		super(num);
		rand = new Random();
	}

	public Move getMove(Board boardState){
		ArrayList<int[]> moves = boardState.getMoves();
		int[] move = moves.get(rand.nextInt(moves.size()));
		return new Move(this.getNumber(), move[0], move[1]);
	}	
}