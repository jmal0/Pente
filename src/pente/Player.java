package pente;

import pente.Move;

public abstract class Player{
	// Number representing player. Between 1 and the number of players in the game
	private int number;

	public Player(int num){
		number = num;
	}

	public int getNumber(){
		return number;
	}

	public abstract Move getMove(Board boardState);
}