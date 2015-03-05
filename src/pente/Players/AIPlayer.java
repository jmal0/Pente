package pente;

import pente.Player;
import pente.Move;
import pente.MoveEvaluation;
import pente.Board;
import java.util.Collections;
import java.util.ArrayList;

public class AIPlayer extends Player{
    
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
        Move m = movesToConsider.get(0);
        return new Move(this.getNumber(), m.row, m.col);
    }

    /**
    * Evaluates the current list of possible moves to determine which should be considered
    * @param b  The current board state
    * @return   A list of valid moves that had an evaluation score above the specified threshold
    */
    private ArrayList<Move> getMovesToConsider(Board b, int threshold){
        ArrayList<Move> validMoves = b.getMoves();
        ArrayList<MoveEvaluation> moveEvaluations = new ArrayList<MoveEvaluation>(validMoves.size());
        for(Move m : validMoves){
            moveEvaluations.add(new MoveEvaluation(m, b, this));
        }

        // Add moves to list to consider ordered from best to worst
        Collections.sort(moveEvaluations);
        int nextEval;
        int i = moveEvaluations.size();

        ArrayList<Move> consideredMoves = new ArrayList(validMoves.size());        
        do{
            i--;
            nextEval = moveEvaluations.get(i).evaluation;
            consideredMoves.add(moveEvaluations.get(i).getMove());
        } while(i > 0 && nextEval > threshold);

        return consideredMoves;
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