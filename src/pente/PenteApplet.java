package pente;

import pente.BrowserGame;
import pente.Board;
import pente.Players.*;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PenteApplet extends Applet{
    private BrowserGame game;
    private Thread gameThread;
    private Color[] colors;
    private int spacing, border;
    private int[] currentMove;
    private MouseListener mouseListener;

    /**
    * Initializes the game and the applet
    */
    @Override
    public void init(){
        setBackground(new Color(128,128,128));
        this.game = new BrowserGame(2, 19, this);
        // Dimensions of the board drawing (assumes height and width are the same)
        this.spacing = (getWidth()-10)/this.game.getBoard().getSize();
        this.border = 5+this.spacing/2;
        // Initialize players TODO: make an interface to start a game
        this.game.addPlayer(1, new BrowserPlayer(1, this.game));
        this.game.addPlayer(2, new AIPlayer(2, 6));
        this.colors = new Color[]{Color.BLACK, Color.WHITE};
        // Start game thread
        this.currentMove = new int[] {-1, -1};
        mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent){
                clickHandler(mouseEvent);
            }
        };
        addMouseListener(mouseListener);
        gameThread = new Thread(this.game);
        gameThread.start();
    }

    /**
    * Called for each move. Draws the new board then waits for the user to input a move
    */
    public void paint(Graphics g){
        this.drawBoard(g);
    }

    private void drawBoard(Graphics g){
        int size = this.game.getBoard().getSize();
        // Draw rows
        int i;
        for(i = 0; i < size; i++){
            g.drawLine(this.border, this.border+i*this.spacing, getWidth()-this.border, this.border+i*this.spacing);
        }
        // Draw columns
        for(i = 0; i < size; i++){
            g.drawLine(this.border+i*this.spacing, this.border, this.border+i*this.spacing, getHeight()-this.border);
        }

        // Draw pieces
        int r, c;
        int[][] board = this.game.getBoard().getBoard();
        for(r = 0; r < size; r++){
            for(c = 0; c < size; c++){
                if(board[r][c] != 0){
                    g.setColor(colors[board[r][c] - 1]);
                    g.fillOval((int) (this.border + (c-.5)*this.spacing), (int) (this.border + (r-.5)*this.spacing), spacing, spacing);
                }
            }
        }
    }

    public void clickHandler(MouseEvent e){
        this.currentMove = this.getRowColFromMouseXY(e.getX(), e.getY());
    }

    /**
    * Calculates the row column index on the board corresponding to a click xy location in pixels
    * @return A length 2 array containing the row and column of the clicked position
    */
    public int[] getRowColFromMouseXY(int x, int y){
        return new int[] { (int) Math.round((y-this.border)/((double) this.spacing)),
                           (int) Math.round((x-this.border)/((double) this.spacing)) };
    }

    /**
    * Resets the selected move to an invalid move so that a player can wait for a new move
    */
    public void resetCurrentMove(){
        this.currentMove[0] = -1;
        this.currentMove[1] = -1;
    }

    public int[] getCurrentMove(){
        return this.currentMove;
    }
}