package pente;

import pente.Game;
import pente.PenteApplet;
import pente.Move;
import java.lang.Runnable;

public class BrowserGame extends Game implements Runnable{
    private PenteApplet appletContext;

    public BrowserGame(int numPlayers, int size, PenteApplet context){
        super(numPlayers, size);
        this.appletContext = context;
    }

    public void run(){
        this.playGame();
    }

    /**
    * Waits for a mouse click from the user and returns the corresponding move
    */
    public int[] getMoveFromClick(){
        int[] currentMove = this.appletContext.getCurrentMove();
        this.appletContext.resetCurrentMove();
        try{
            while(currentMove[0] == -1 && currentMove[1] == -1){
                // This sucks but I don't feel like figuring out signals right now
                Thread.sleep(100);
                currentMove = this.appletContext.getCurrentMove();
            } 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentMove;
    }

    /**
    * Overrides the print from the base Game.java to display on the canvas
    */
    @Override
    public void displayBoard(){
        this.appletContext.repaint();
    }
}
