package pente;

import pente.Player;
import pente.Move;
import pente.MoveEvaluation;
import pente.Board;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class AIPlayer extends Player{
    private HashMap<Long, BoardEvaluation> evaluated;
    private int depth;

    public AIPlayer(int number, int evaluationDepth){
        super(number);
        this.depth = evaluationDepth;
        this.evaluated = new HashMap<Long, BoardEvaluation>(1000);
    }

    /**
    * Stupid for now, takes move with best evaluation
    */
    public Move getMove(Board boardState){
        // TODO: make a threshold
        List<Move> movesToConsider = this.getMovesToConsider(boardState, 1, this.number);

        // Search first layer, find best move from evaluations
        int best = Integer.MIN_VALUE;
        int eval;
        Move bestMove = movesToConsider.get(0);
        for(Move m : movesToConsider){
            // Deep copy current board before making move
            Board childState = new Board(boardState);
            childState.makeMove(new Move(this.number, m.row, m.col));
            eval = searchNode(childState, this.depth, best, Integer.MAX_VALUE, false);
            if(eval > best){
                best = eval;
                bestMove = m;
            }
        }

        // Set the player for the move before returning it
        return new Move(this.number, bestMove.row, bestMove.col);
    }

    /**
    * Evaluates all possible moves to determine which the current player should consider
    * @param b  The current board state
    * @return   A list of valid moves that had an evaluation score above the specified threshold
    */
    private List<Move> getMovesToConsider(Board b, int threshold, int player){
        List<Move> validMoves = b.getMoves();
        List<MoveEvaluation> moveEvaluations = new ArrayList<MoveEvaluation>(validMoves.size());
        for(Move m : validMoves){
            moveEvaluations.add(new MoveEvaluation(m, b, player));
        }

        // Add moves to list to consider ordered from best to worst
        Collections.sort(moveEvaluations);
        int nextEval;
        int i = moveEvaluations.size();

        List<Move> consideredMoves = new ArrayList(validMoves.size());        
        do{
            i--;
            nextEval = moveEvaluations.get(i).evaluation;
            consideredMoves.add(moveEvaluations.get(i).move);
        } while(i > 0 && nextEval > threshold);

        return consideredMoves;
    }

    /**
    * Alpha-beta pruning search. Evaluates all children of the given board to the specified move
    * depth. Alpha and beta serve as the current evaluation baseline
    * @param b          The board state that is being searched
    * @param depth      How many levels deeper the search should continue. Decrements with each move
    * @param alpha      Current best maximizing evaluation
    * @param beta       Current best minimizing evaluation
    * @param maximize   Whether the search should maximize or minimize 
    * @return           The evaluation of this node
    */
    private int searchNode(Board b, int depth, int alpha, int beta, boolean maximize){
        // TODO: don't search children of already evaluated board?
        BoardEvaluation eval = this.evaluateBoard(b);

        // Don't search any deeper
        if(depth == 0){
            return eval.score;
        }
        // Game over, can't search further. Return win/lose/draw
        if(b.gameOver()){
            int winner = b.getWinner();
            if(winner == -1)
                return 0;
            return winner == this.number ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        int currentPlayer = b.getCurrentPlayer();
        boolean maximizeNext = b.getNextPlayer() == this.number;
        List<Move> moves = this.getMovesToConsider(b, 1, currentPlayer); // TODO: threshold
        System.out.println(String.format("Evaluating %d moves", moves.size()));
        if(maximize){
            int best = Integer.MIN_VALUE;
            for(Move m : moves){
                // Deep copy current board before making move
                Board childState = new Board(b);
                childState.makeMove(new Move(currentPlayer, m.row, m.col));
                best = Math.max(best, searchNode(childState, depth-1, alpha, beta, maximizeNext));
                alpha = Math.max(alpha, best);
                if(beta <= alpha)
                    break;
            }
            return best;
        }
        else{
            int best = Integer.MAX_VALUE;
            for(Move m : moves){
                // Deep copy current board before making move
                Board childState = new Board(b);
                childState.makeMove(new Move(currentPlayer, m.row, m.col));
                best = Math.max(best, searchNode(childState, depth-1, alpha, beta, maximizeNext));
                beta = Math.min(beta, best);
                if(beta <= alpha)
                    break;
            }
            return best;
        }
    }

    /**
    * Evaluates the given board if it is not already in the hashmap
    * @param b  The board to evaluate
    * @return   The newly created evaluation of this board or the already existing one
    */
    public BoardEvaluation evaluateBoard(Board b){
        if(this.evaluated.containsKey(b.getHash())){
            return evaluated.get(b.getHash());
        }
        else{
            BoardEvaluation newEvaluation = new BoardEvaluation(b);
            evaluated.put(b.getHash(), newEvaluation);
            return newEvaluation;
        }
    }
}