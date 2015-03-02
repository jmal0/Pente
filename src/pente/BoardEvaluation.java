package pente;

import pente.Board;

public class BoardEvaluation implements Comparable<BoardEvaluation>{
    public final int moveNum;
    public final long hash;
    public final int score;

    public BoardEvaluation(Board b){
        hash = b.getHash();
        score = evaluateBoard(b);
        moveNum = b.getMoveNum();
    }

    /**
    * Compares evaluations. Order: moveNum, score, hash
    */
    @Override    
    public int compareTo(BoardEvaluation other){
        if(this.moveNum < other.moveNum)
            return -1;
        if(this.moveNum > other.moveNum)
            return 1;
        if(this.score < other.score)
            return -1;
        if(this.score > other.score)
            return 1;
        if(this.hash == other.hash)
            return 0;
        return this.hash < other.hash ? -1 : 1;
    }

    public int evaluateBoard(Board b){
        return 0;
    }

}