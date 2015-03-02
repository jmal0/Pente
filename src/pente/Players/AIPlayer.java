package pente;

import pente.Player;
import pente.Move;
import pente.Board;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class AIPlayer extends Player{
    public static final int[][] PLAYER_WEIGHTS = AIPlayer.loadMatrixFromFile("/PlayerWeights.csv");
    public static final int[][] DISTANCE_WEIGHTS = AIPlayer.loadMatrixFromFile("/DistanceWeights.csv");

    public AIPlayer(int number){
        super(number);
    }

    /**
    * Stupid for now, takes move with best evaluation
    */
    public Move getMove(Board boardState){
        // TODO: make a threshold
        ArrayList<Move> movesToConsider = this.getMovesToConsider(boardState, 0);
        // Return move with best evaluation from list
        Move m = movesToConsider.get(movesToConsider.size()-1);
        return new Move(this.getNumber(), m.row, m.col);
    }

    /**
    * Evaluates the current list of possible moves to determine which should be considered
    * @param b  The current board state
    * @return   A list of valid moves that had an evaluation score above the specified threshold
    */
    private ArrayList<Move> getMovesToConsider(Board b, int threshold){
        ArrayList<Move> validMoves = b.getMoves();
        ArrayList<Move> consideredMoves = new ArrayList(validMoves.size());
        int[] evaluations = new int[validMoves.size()];
        int[][] board = b.getBoard();
        int x, y, range;
        int i = 0;
        for(Move m : validMoves){
            range = (AIPlayer.DISTANCE_WEIGHTS.length - 1)/2;
            for(y = m.row-range; y <= m.row+range; y++){
                for(x = m.col-range; x <= m.col+range; x++){
                    if(x > -1 && y > -1 && x < b.getSize() && y < b.getSize()){
                        evaluations[i] += AIPlayer.PLAYER_WEIGHTS[board[y][x]][this.getNumber()-1] * AIPlayer.DISTANCE_WEIGHTS[y-m.row+range][x-m.col+range];
                    }
                }
            }
            i++;
        }

        // Add moves to list to consider ordered from best to worst
        Arrays.sort(evaluations);
        for(i = evaluations.length - 1; i >= 0; i--){
            if(evaluations[i] > threshold){
                consideredMoves.add(validMoves.get(i));
            }
        }

        // Have to return at least one move, return best option
        if(consideredMoves.size() == 0){
            consideredMoves.add(validMoves.get(evaluations.length - 1));
        }

        return consideredMoves;
    }

    /**
    * Load matrix from resource
    * @param url    File location of the resource to be loaded
    * @return       A matrix of long values from the text file
    */
    private static int[][] loadMatrixFromFile(String url){
        try{
            InputStream in = Board.class.getResourceAsStream(url); 
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
}

/*

def Move getMove(Board boardState):
    all_moves = boardState.getValidMoves()
    return


def int searchNode(node, desiredDepth)
    if(moveDepth == desiredDepth)
        evaluation = evaluateBoard(node)
        add evaluation to evaluated
        return evaluation.score

    for move in all_moves_to_check:
        scores(this move) = searchNode(Node for move)
    
    if node.moveNum is own move:
        return max(score)
    else:
        return min(score)
*/