package pente;

import java.util.ArrayList;
import java.util.Collections;

public class Board{
	// Height and width of board (board is assumed to be square)
	private final int size;
	// Array representing board. 
	private int[][] board;
	// Array indicating how many of each players pieces have been captured 
	private int[] capturedPieces;
	// Again, used to efficiently keep track of remaining moves
	private ArrayList<Move> validMoves;
	// Same dealio
	private int moveNum;
	// Number of winning player (-1 if none), used to see if game is over
	private int winner;
	// List of contiguous spaces containing same player
	private ArrayList<Chain> chains;
	// Length of chain needed to win
	private static final int WIN_LENGTH = 5;
	// Number of players in game
	private final int numPlayers;
	// Number of captures needed to win game
	private static final int WINNING_CAPTURES = 10;


	public Board(int dimension, int numPlayers){
		this.size = dimension;
		this.board = new int[size][size];
		this.capturedPieces = new int[numPlayers];
		this.validMoves = new ArrayList<Move>();
		this.updateChains();
		System.out.println(this.chains);
		this.winner = -1;
		this.numPlayers = numPlayers;
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
		// Update list of valid moves
		this.validMoves.remove(Collections.binarySearch(validMoves, m));
		// Remove any pairs
		this.makeCaptures(m);
		System.out.println(this);
		// Update list of chains
		this.updateChains();
		System.out.println(this.chains);
		this.updateHash(); // This doesn't do anything yet
		moveNum++;

		return true;
	}

	/*
		Finds all sequences locations with same player or no player
	*/
	public void updateChains(){
		// Re-initialize list of chains
		this.chains = new ArrayList<Chain>(); 
		int r, c, where;
		// Search for chains at all board positions
		for(r = 0; r < this.size; r++){
			for(c = 0; c < this.size; c++){
				// Find chains starting at index r,c
				ArrayList<Chain> newChains = Chain.findChains(this.board, r, c);
				// Insert new chains
				for(Chain chain : newChains){
					where = -Collections.binarySearch(this.chains, chain) - 1;
					if(where < 0)
						continue;
					chains.add(where, chain);
					if(chain.getPlayer() != 0 && chain.getLength() >= Board.WIN_LENGTH)
						this.winner = chain.getPlayer();
				}
			}
		}
	}
	
	/*
		Gets all chains for a certain player
		@return All chains on the board for the given player
	*/
	public ArrayList<Chain> getPlayerChains(int player){
		ArrayList<Chain> playerChains = new ArrayList<Chain>();
		int i = 0;
		// Find first instance of a chain of this player
		while(i < this.chains.size() && this.chains.get(i).getPlayer() != player){
			i++;
		}

		// Add all chains of this player
		while(i < this.chains.size() && this.chains.get(i).getPlayer() == player){
			playerChains.add(this.chains.get(i));
			i++;
		}
		return playerChains;
	}

	/*
		Checks for any pairs captured by this move. Updates board, list of captures, and winner
		(if necessary)
		@param m 	Current move
	*/
	public void makeCaptures(Move m){
		int xDir, yDir, where;
		Move newMove;
		for(xDir = -1; xDir <= 1; xDir++){
			for(yDir = -1; yDir <= 1; yDir++){
				// Check if pair and opposite end is entirely on the board. Skip if not
				if(m.row + 3*xDir < 0 || m.row + 3*xDir >= this.size || m.col + 3*yDir < 0 || m.col + 3*yDir >= this.size)
					continue;
				// Check that pair of locations are empty and do not belong to the moving player
				if(this.board[m.row + xDir][m.col + yDir] == m.player)
					continue;
				if(this.board[m.row + xDir][m.col + yDir] == 0)
					continue;
				if(this.board[m.row + 2*xDir][m.col + 2*yDir] == m.player)
					continue;
				if(this.board[m.row + 2*xDir][m.col + 2*yDir] == 0)
					continue;
				// Check if this player is covering opposite end of chain. Skip if not
				if(this.board[m.row + 3*xDir][m.col + 3*yDir] != m.player)
					continue;
				// A capture was made, clear pair, add back those locations as valid moves and
				// update captures
				this.capturedPieces[m.player - 1]++;
				this.capturedPieces[m.player - 1]++;
				this.board[m.row + xDir][m.col + yDir] = 0;
				this.board[m.row + 2*xDir][m.col + 2*yDir] = 0;
				newMove = new Move(0, m.row + xDir, m.col + yDir);
				where = -Collections.binarySearch(this.validMoves, newMove) - 1;
				validMoves.add(where, newMove);
				newMove = new Move(0, m.row + 2*xDir, m.col + 2*yDir);
				where = -Collections.binarySearch(this.validMoves, newMove) - 1;
				validMoves.add(where, newMove);

				System.out.println("Capture made");
				if(this.capturedPieces[m.player - 1] >= Board.WINNING_CAPTURES)
					this.winner = m.player;
			}
		}
	}

	/*
		Checks if board is full or if someone has via pente or captures
		@return 	Whether or not the game is over
	*/
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

	public int getWinner(){
		return this.winner;
	}

	/*
		Returns a string representing the board. Pieces are displayed according to the number of the
		player that placed them. Empty locations are displayed as zeros
		@return 	the string representing the board
	*/
	@Override
	public String toString(){
		String text = "     ";
		int i, r, c;
		// Column header
		for(i = 0; i < this.size; i++){
			text += String.format("%-3d", i);
		}
		text += "\n";
		for(r = 0; r < this.size; r++){
			text += String.format("%-5d", r);
			for(c = 0; c < this.size; c++){
				text += String.format("%-3d", this.board[r][c]);
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