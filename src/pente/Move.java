package pente;

import java.util.ArrayList;

public class Move implements Comparable<Move>{
	public int player, row, col;

	public Move(int p, int r, int c){
		player = p;
		row = r;
		col = c;
	}

	/*
		Compares two moves ignoring player. Compares row first, then column. Higher row/col returns
		1.
		@param other 	The move to compare this move to
		@return 		0 if the moves are the same, +/- 1 if this move is greater/less than
	*/
	public int compareTo(Move other){
		if(this.row == other.row){
			if(this.col == other.col)
				return 0;
			if(this.col < other.col)
				return -1;
			return 1;
		}
		if(this.row < other.row)
			return -1;
		return 1;
	}

	/*
		Finds a move in a given array list that is assumed to be sorted via binary search
		@param list 	A sorted array list of moves to search through
		@param move 	The move to find
		@return 		The index where the move is
	*/
	public static int find(ArrayList<Move> list, Move m){
		int min = 0;
		int max = list.size()-1;
		int i = (min+max)/2;
		int compare = m.compareTo(list.get(i));
		while(compare != 0 && min <= max){
			if(compare < 0)
				max = i-1;
			else
				min = i+1;
			i = (min + max)/2;
			compare = m.compareTo(list.get(i));
		}
		
		// Not found, return -1
		if(min > max)
			return -1;
		return i;
	}

	/*
		Inserts a move in a given array list that is assumed to be sorted
		@param list 	A sorted array list of moves to insert this move into
		@return 		The sorted list containing the new move
	*/
	public ArrayList<Move> insert(ArrayList<Move> list){
		int min = 0;
		int max = list.size();
		int i = 0;
		int compare = 0;
		while(min < max){
			i = (min + max)/2;
			compare = this.compareTo(list.get(i));
			if(compare < 0)
				max = i-1;
			else
				min = i+1;
		}

		list.add(i, this);
		return list;
	}

	@Override
	public String toString(){
		return String.format("Player:%d Row:%d Col:%d\n", this.player, this.row, this.col);
	}
}