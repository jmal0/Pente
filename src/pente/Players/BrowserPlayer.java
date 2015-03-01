package pente.Players;

import pente.Player;
import pente.Board;
import pente.Move;
import pente.Game;
import pente.BrowserGame;
import java.util.ArrayList;
import java.util.Collections;

public class BrowserPlayer extends Player{
    private BrowserGame gameContext;

    public BrowserPlayer(int num, Game g){
        super(num);
        this.gameContext = (BrowserGame) g;
    }

    /**
    * Gets move from click input
    */
    public Move getMove(Board boardState){
        ArrayList<Move> moves = boardState.getMoves();
        Move m;
        int[] rc;
        do{
            rc = this.gameContext.getMoveFromClick();
            m = new Move(this.getNumber(), rc[0], rc[1]);
            // Move is in list. Exit loop
            if(Collections.binarySearch(moves, m) >= 0)
                break;
            // Not in list, invalid
            System.out.println("Invalid move");
        } while(true);

        return m;
    }
}