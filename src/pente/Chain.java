package pente;

public class Chain{
	public static enum DIRECTION{
		RIGHT_UP, RIGHT, RIGHT_DOWN, DOWN
	}
	private int startR, startC;
	private DIRECTION direction;
	private int length;
	private int player;
	private int targetLength;

	public Chain(int r, int c, DIRECTION dir, int len, int p){
		startR = r;
		startC = c;
		direction = dir;
		if(len < 5 && len > 1){
			length = len;
		}
		player = p;
		targetLength = 5;
	}

	public Chain(int r, int c, DIRECTION dir, int len, int p, int targetLen){
		startR = r;
		startC = c;
		direction = dir;
		if(len < 5 && len > 1){
			length = len;
		}
		player = p;
		// For non-two player games where chains must be 4 long
		targetLength = targetLen;
	}

	public boolean isInChain(int r, int c){
		if(direction == DIRECTION.RIGHT){
			return (r - startR <= length) && (r - startR >= 0);
		}
		if(direction == DIRECTION.DOWN){
			return (c - startC <= length) && (c - startC >= 0);
		}
		if(direction == DIRECTION.RIGHT_UP){
			return (r - startR <= length) && (r - startR >= 0) && (startC - c <= length) && (startC - c >= 0);
		}
		return (r - startR <= length) && (r - startR >= 0) && (c - startC <= length) && (c - startC >= 0);
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

	public int getX(){
		return startR;
	}

	public int getY(){
		return startC;
	}

	public int getLength(){
		return length;
	}

	public int getColor(){
		return player;
	}
}