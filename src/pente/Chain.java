package pente;

import java.util.ArrayList;

public class Chain implements Comparable<Chain>{
	// Pairs of integers describing x and y directions of possible chains
	public static final int[][] DIRECTIONS = {{1,-1},{1,0},{1,1},{0,1}};
	// Directions
	public static final int RIGHT_UP = 0;
	public static final int RIGHT = 1;
	public static final int RIGHT_DOWN = 2;
	public static final int DOWN = 3;
	// Integer referring to one of previously mentioned directions
	private int direction;
	// Start index on board of chain
	private int startR, startC;
	// Length of chain
	private int length;
	// Player comprising chain
	private int player;

	public Chain(int r, int c, int dir, int len, int p){
		startR = r;
		startC = c;
		direction = dir;
		length = len;
		player = p;
	}

	public boolean isInChain(int r, int c){
		if(direction == RIGHT){
			return (r - startR <= length) && (r - startR >= 0);
		}
		if(direction == DOWN){
			return (c - startC <= length) && (c - startC >= 0);
		}
		if(direction == RIGHT_UP){
			return (r - startR <= length) && (r - startR >= 0) && (startC - c <= length) && (startC - c >= 0);
		}
		return (r - startR <= length) && (r - startR >= 0) && (c - startC <= length) && (c - startC >= 0);
	}

	/*
		Finds chains originating at r, c in board b
		@param b 	The board array to search
		@param r 	The row index of the position to test
		@param c 	The column index of the position to test
	*/
	public static ArrayList<Chain> findChains(int[][] b, int r, int c){
		ArrayList<Chain> chains = new ArrayList<Chain>();
		int size = b.length;
		// Iterate through possible directions
		for(int i = 0; i < Chain.DIRECTIONS.length; i++){
			// The board position that would come before this position in a chain
			int[] prevLoc = {r - Chain.DIRECTIONS[i][0], c - Chain.DIRECTIONS[i][1]};
			// Check if previous board position is valid or if it is already in a chain
			if(prevLoc[0] >= 0 && prevLoc[1] >= 0 && prevLoc[1] < size){
				if(b[prevLoc[0]][prevLoc[1]] == b[r][c])
					continue;
			}
			// Continue searching through board until edge or end of chain is reached
			int length = 1;
			int[] nextLoc = {r + Chain.DIRECTIONS[i][0], c + Chain.DIRECTIONS[i][1]};
			while(nextLoc[0] < size && nextLoc[1] < size && nextLoc[1] >= 0 && b[nextLoc[0]][nextLoc[1]] == b[r][c]){
				nextLoc[0] += Chain.DIRECTIONS[i][0];
				nextLoc[1] += Chain.DIRECTIONS[i][1];
				length++;
			}
			if(length > 1){
				chains.add(new Chain(r, c, i, length, b[r][c]));
			}
		}
		return chains;
	}

	//TODO
	public int getDistanceFromChain(int r, int c){
		return 0;
	}
	// TODO
	public boolean partiallyBlocksChain(int r, int c){
		return false;
	}
	// TODO
	public boolean completelyBlocksChain(int r, int c){
		return false;	
	}

	/*
		Compares two chains by the following order: player number, length, direction, start column, start row
		@param other 	The chain to compare to
		@return 		0 if the chains are identical, +/- 1 if Chain is greater/less according to properties
	*/
	public int compareTo(Chain other){
		if(this.player == other.getPlayer()){
			if(this.length == other.getLength()){
				if(this.direction == other.getDirection()){
					if(this.startC == other.getY()){
						if(this.startR == other.getX())
							return 0;
						if(this.startR < other.getX())
							return -1;
						return 1;
					}
					if(this.startC < other.getY())
						return -1;
					return 1;
				}
				if(this.direction < other.getDirection())
					return -1;
				return 1;
			}
			if(this.length < other.getLength())
				return -1;
			return 1;
		}
		if(this.player < other.getPlayer())
			return -1;
		return 1;
	}

	public int getX(){
		return this.startR;
	}

	public int getY(){
		return this.startC;
	}

	public int getDirection(){
		return this.direction;
	}

	public int getPlayer(){
		return this.player;
	}

	public int getLength(){
		return this.length;
	}

	@Override
	public String toString(){
		return String.format("Chain at %d, %d with direction %d of length %d for player %d \n", startR, startC, direction, length, player);
	}
}