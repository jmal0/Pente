package pente;

import java.util.ArrayList;

public class Board{
	// Height and width of board (board is assumed to be square)
	private int size;
	// Array representing board. 
	private int[][] board;
	// Array indicating how many of each players pieces have been captured 
	private int[] capturedPieces;
	// Same dealio
	private int moveNum;
	// Again, used to efficiently keep track of remaining moves
	private ArrayList<Move> validMoves;
	// Number of winning player  (-1 if none)
	private int winner;
	// List of contiguous spaces containing same player
	private ArrayList<Chain> chains;
	// Length of chain needed to win
	private final int winLength;


	public Board(int dimension, int numPlayers){
		this.size = dimension;
		this.board = new int[size][size];
		this.capturedPieces = new int[numPlayers];
		this.validMoves = new ArrayList<Move>();
		this.chains = new ArrayList<Chain>();
		this.updateChains();
		this.winner = -1;
		if(numPlayers == 2)
			this.winLength = 5;
		else
			this.winLength = 4;
		for(int r = 0; r < size; r++){
			for(int c = 0; c < size; c++){
				validMoves.add(new Move(0, r, c));
			}
		}
	}

	/*
		Makes the specified move if it is valid and returns whether or not it was
		@param m 	
	*/
	public boolean makeMove(Move m){
		if(this.board[m.row][m.col] != 0)
			return false;

		this.board[m.row][m.col] = m.player;
		this.validMoves.remove(Move.find(validMoves, m));
		this.updateChains();
		this.updateHash(); // This doesn't do anything yet
		moveNum++;

		return true;
	}

	/*
	*/
	public void updateChains(){
		int r, c;
		// Search for chains at all board positions
		for(r = 0; r < this.size; r++){
			for(c = 0; c < this.size; c++){
				// Find chains starting at index r,c
				ArrayList<Chain> newChains = Chain.findChains(this.board, r, c);
				// Insert new chains
				for(Chain chain : newChains){
					this.chains = chain.insert(this.chains);
					if(chain.getPlayer() != 0 && chain.getLength() >= this.winLength)
						this.winner = chain.getPlayer();
				}
			}
		}
		System.out.println(chains.size());
	}
	
	/*
		Checks if tracked number of pieces is equal to the number of spaces on the board
		@return 	Whether or not the board is full
	*/
	public boolean boardFull(){
		return this.validMoves.size() == 0;
	}

//TODO haha yeah lol
	public int getWinner(){
		return this.winner;
	}
	
	public boolean gameOver(){
		return this.winner != -1 || this.validMoves.size() == 0;
	}

	// Getters

	public int[][] getBoard(){
		return this.board;
	}

	public int getNumPieces(){
		return this.size*this.size - this.validMoves.size();
	}

	public int getSize(){
		return this.size;
	}

	public int[] getCaptures(){
		return this.capturedPieces;
	}

	public ArrayList<Move> getMoves(){
		return this.validMoves;
	}

	public ArrayList<Chain> getChains(){
		return this.chains;
	}

	/*
		Returns a string representing the board. Pieces are displayed according to the number of the
		player that placed them. Empty locations are displayed as zeros
		@return 	the string representing the board
	*/
	@Override
	public String toString(){
		String text = "";
		for(int r = 0; r < this.size; r++){
			for(int c = 0; c < this.size; c++){
				text += Integer.toString(this.board[r][c]);
			}
			text += "\n";
		}

		return text;
	}

	/*
		Checks if two boards are the same. First a quick check is done to see if the boards contain
		the same number of pieces. If they do, the board array is checked element by element
		@return 	whether or not the boards are the same
	*/
	/*
	public boolean equals(Board other){
		if(numPieces != other.getNumPieces())
			return false;
		if(size != other.getSize())
			return false;
		if(!capturedPieces.equals(other.getCaptures()))
			return false;
		int[][] otherBoard = other.getBoard();
		for(int r = 0; r < size; r++){
			for(int c = 0; c < size; c++){
				if(board[r][c] != otherBoard[r][c]){
					return false;
				}
			}
		}
		return true;
	}
	*/
	// TODO: maybe someday
	public void updateHash(){}

	/*
	*/
	/*
	public static ?? getHash(Board b){
		return ??;
	}
	*/
}