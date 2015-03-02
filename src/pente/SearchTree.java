package pente;

import pente.BoardEvaluation;
import pente.Board;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class SearchTree{
    private HashMap<Long, BoardEvaluation> evaluated;
    private Node root;

    public SearchTree(Board rootData){
        // 1000 is an arbitrary magic number, TODO: put thought into it
        this.evaluated = new HashMap<Long, BoardEvaluation>(1000);
        this.root = new Node(this.evaluateBoard(rootData), null);
    }

    public class Node implements Comparable<Node>{
        private BoardEvaluation data;
        private Node parent;
        private ArrayList<Node> children;

        public Node(BoardEvaluation b, Node p){
            this.data = b;
            this.parent = p;
            // 10 is also a magic number TODO: you know
            this.children = new ArrayList<Node>(10);
        }

        public void addChild(Board b){
            BoardEvaluation evaluation = evaluateBoard(b);
            Node newChild = new Node(evaluation, this);
            this.children.add(-Collections.binarySearch(this.children, newChild)-1, newChild);
        }

        // Most likely will only be used for setting something as the new root
        public void setParent(Node newParent){
            this.parent = newParent;
        }

        public BoardEvaluation getData(){
            return this.data;
        }

        public boolean isRoot(){
            return this.parent == null;
        }

        @Override
        public int compareTo(Node other){
            return this.data.compareTo(other.getData());
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
    
    // I'm not actually using this and I'm not sure if I have to
    /**
    * Removes the root layer of the tree and sets the new root
    * Also clears old boards from list
    */
    /*
    public void descendLayer(Node newRoot){
        this.root = newRoot;

        // Find all boards from previous moves and removes them
        int i = 0;
        int newLevel = newRoot.data.moveNum;
        while(evaluated.get(i).moveNum < newLevel){
            i++;
        }
        this.evaluated.subList(0, i+1).clear();
    }
    */
}
