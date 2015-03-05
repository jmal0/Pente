package pente;

import pente.Move;
import pente.Board;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class MoveEvaluation implements Comparable<MoveEvaluation>{
    public static final int[][] PLAYER_WEIGHTS = MoveEvaluation.loadMatrixFromFile("/PlayerWeights.csv");
    public static final int[][] DISTANCE_WEIGHTS = MoveEvaluation.loadMatrixFromFile("/DistanceWeights.csv");
    private Move m;
    public final int evaluation;

    public MoveEvaluation(Move move, Board context, Player p){
        this.m = move;
        this.evaluation = evaluate(move, context, p);
    }

    /**
    *
    */
    public int evaluate(Move m, Board b, Player p){
        int x,y;
        int evaluation = 0;
        int range = (MoveEvaluation.DISTANCE_WEIGHTS.length - 1)/2;
        int[][] board = b.getBoard();
        for(y = m.row-range; y <= m.row+range; y++){
            for(x = m.col-range; x <= m.col+range; x++){
                if(x > -1 && y > -1 && x < b.getSize() && y < b.getSize()){
                    evaluation += MoveEvaluation.PLAYER_WEIGHTS[board[y][x]][p.getNumber()-1] * MoveEvaluation.DISTANCE_WEIGHTS[y-m.row+range][x-m.col+range];
                }
            }
        }
        return evaluation;
    }

    public int compareTo(MoveEvaluation other){
        if(this.evaluation < other.evaluation)
            return -1;
        if(this.evaluation > other.evaluation)
            return 1;
        return this.m.compareTo(other.getMove());
    }

    /**
    * Load matrix from resource
    * @param url    File location of the resource to be loaded
    * @return       A matrix of long values from the text file
    */
    private static int[][] loadMatrixFromFile(String url){
        try{
            InputStream in = MoveEvaluation.class.getResourceAsStream(url); 
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            int r = 1;
            ArrayList<int[]> matrix = new ArrayList<int[]>();
            String line = reader.readLine();
            while(line != null){
                String[] nums = line.split(",");
                matrix.add(new int[nums.length]);
                for(int c = 0; c < nums.length; c++){
                    matrix.get(r-1)[c] = Integer.parseInt(nums[c]);
                }
                r++;
                line = reader.readLine();
            }
            reader.close();
            in.close();

            return matrix.toArray(new int[matrix.size()][matrix.get(0).length]);
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public Move getMove(){
        return this.m;
    }
}