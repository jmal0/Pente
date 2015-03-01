package pente;

import pente.*;
import pente.Players.*;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class PenteApplet extends Applet{
    private BrowserGame game;
    private Color[] colors;

    @Override
    public void init(){
        setBackground(new Color(128,128,128));
        repaint();
        this.game = new BrowserGame(2, 19);
        this.game.addPlayer(1, new RandomPlayer(1));
        this.game.addPlayer(2, new RandomPlayer(2));
        this.colors = new Color[]{Color.BLACK, Color.WHITE};
        int winner = this.game.playGame();
        if(winner == -1){
            System.out.println("No one wins!");
        }
        else{
            System.out.println(String.format("Winner is player %d!", winner));
        }
	}

    public void paint(Graphics g){
        this.game.drawBoard(g);
    }

    private class BrowserGame extends Game{
        private int border;

        public BrowserGame(int numPlayers, int size){
            super(numPlayers, size);
            border = 10;
        }

        /**
        * Overrides the print from the base game.java to display on the canvas
        */
        @Override
        public void displayBoard(){
            repaint();
        }

        private void drawBoard(Graphics g){
            int size = this.getBoard().getSize();
            int xStep = getWidth()/size;
            int yStep = getHeight()/size;
            // Draw rows
            int i;
            for(i = 0; i < size; i++){
                g.drawLine(border, border+i*xStep, getWidth(), border+i*xStep);
            }
            // Draw columns
            for(i = 0; i < size; i++){
                g.drawLine(border+i*yStep, border, border+i*yStep, getHeight());
            }

            // Draw pieces
            int r, c;
            int[][] board = game.getBoard().getBoard();
            for(r = 0; r < size; r++){
                for(c = 0; c < size; c++){
                    if(board[r][c] != 0){
                        g.setColor(colors[board[r][c] - 1]);
                        g.fillOval((int) (border + (r-.5)*xStep), (int) (border + (c-.5)*yStep), xStep, yStep);
                    }
                }
            }
        }
    }

}