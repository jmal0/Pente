package pente;

public class Board{
	// Array representing board. 
	private int[][] board;
	private int[][] table;
	// Height and width of board (board is assumed to be square)
	private int size;
	// Used to have easy access to number of pieces on board. Needed for quick comparisons
	private int numPieces;
	// Same dealio
	private int moveNum;

	public Board(int dimension, int numPlayers){
		size = dimension;
		board = new int[size][size];
		table = new int[size*size][numPlayers];
		numPieces = 0;
	}

	public void makeMove(Move m){
		if(board[m.row][m.col] == 0){
			board[m.row][m.col] = m.player;

			this.updateHash();
			moveNum++;
		}
		else
			throw new IllegalArgumentException(String.format("Invalid move, position %d, %d is occupied", m.row, m.col));
	}

	/*
		??
		@param player Number of the player 
		@param length
		@return
	*/
	public void lookForChains(int player, int length){

	}
	
	/*
		Checks if tracked number of pieces is equal to the number of spaces on the board
		@return 	Whether or not the board is full
	*/
	public boolean boardFull(){
		return numPieces == size*size;
	}
//TODO haha yeah lol
	public int getWinner(){
		return -1;
	}
	
	public boolean gameOver(){
		return false;
	}
//
	// Getters

	public int[][] getBoard(){
		return board;
	}

	public int getNumPieces(){
		return numPieces;
	}

	public int getSize(){
		return size;
	}

	/*
		Returns a string representing the board. Pieces are displayed according to the number of the
		player that placed them. Empty locations are displayed as zeros
		@return 	the string representing the board
	*/
	@Override
	public String toString(){
		String text = "";
		for(int r = 0; r < size; r++){
			for(int c = 0; c < size; c++){
				text += Integer.toString(board[r][c]);
			}
			text += "\n";
		}

		return text;
	}

	// TODO add captured pieces
	/*
		Checks if two boards are the same. First a quick check is done to see if the boards contain
		the same number of pieces. If they do, the board array is checked element by element
		@return 	whether or not the boards are the same
	*/
	public boolean equals(Board other){
		if(numPieces != other.getNumPieces())
			return false;
		if(size != other.getSize())
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

	public void updateHash(){}

	/*
	*/
	/*
	public static ?? getHash(Board b){
		return ??;
	}
	*/
}