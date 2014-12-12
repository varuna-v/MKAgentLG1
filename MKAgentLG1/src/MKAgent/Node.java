package src.MKAgent;

import java.util.ArrayList;

/**
 * Created by mbax9vv2 on 21/11/14.
 */
public class Node implements Comparable<Node>
{
    private static final double SWAP_CONSTANT = 1000; // a constant for the value of a swap node
    private final static int extraMoveConstant = 8;
    private final static int bigPotConstant = 7;

    public Side playerMakingTheNextMove; // the player currently making the move on this node
    public Board state; // the state of the board in the current node
    public double pruneValue; // the pruning value of the node
    public double heuristicValue; // the value of the current node
    public ArrayList<Node> children; //
    public Side ourPlayersSide;
    public int lastMoveToGetHere;
    public boolean completedAttemptToBuildChildren = false;
    public boolean completedBuildToRequiredDepth = false;
    public int depth;
    public double alpha;
    public double beta;

    private int minNumberOfSeedsForSureWin()
    {
        return (state.getNoOfHoles() * state.getNoOfHoles()) + 1;
    }

    public boolean isMaxNode()
    {
        return playerMakingTheNextMove == ourPlayersSide;
    }

    public Node(Side playerMakingTheNextMove, Side us)
    {
        this.playerMakingTheNextMove = playerMakingTheNextMove;
        state = new Board(7, 7);
        this.ourPlayersSide = us;
        heuristicValue = 0;
        pruneValue = heuristicValue;
        children = null;
        depth = 1;
    }

    public Node(Side playerMakingTheNextMove, Board state, Side us)
    {
        this.playerMakingTheNextMove = playerMakingTheNextMove;
        this.ourPlayersSide = us;
        this.state = state;
        children = null;
        heuristicValue = 0;
        pruneValue = heuristicValue;
        //evaluate();
    }

    public Node(Side playerMakingTheNextMove, Board state, Side us, Node parentNode, int move)
    {
        this.playerMakingTheNextMove = playerMakingTheNextMove;
        this.ourPlayersSide = us;
        this.state = state;
        children = null;
        lastMoveToGetHere = move;
        evaluateBasedOnThisNode(parentNode);
        pruneValue = heuristicValue;
        this.depth = parentNode.depth + 1;
    }

    public void evaluateBasedOnChildren()
    {
        if (this.children != null && this.children.size() > 0)
        {
            for (Node child : this.children)
            {
                child.evaluateBasedOnChildren();
            }
            if (isMaxNode())
            {
                this.heuristicValue += children.get(0).heuristicValue;
            }
            else
            {
                this.heuristicValue += children.get(children.size() - 1).heuristicValue;
            }
        }
    }

    // this errors out if the move# is for swap
    private void evaluateBasedOnThisNode(Node parentNode)
    {
        int minNumberOfSeedsForSureWin = minNumberOfSeedsForSureWin();
        this.heuristicValue = 0;
        if (this.lastMoveToGetHere == 8)
        {
            this.heuristicValue += SWAP_CONSTANT;
        }
        this.heuristicValue += state.getSeedsInStore(this.ourPlayersSide) - parentNode.state.getSeedsInStore(parentNode.ourPlayersSide);
        this.heuristicValue -= (state.getSeedsInStore(this.ourPlayersSide.opposite()) - parentNode.state.getSeedsInStore(parentNode.ourPlayersSide.opposite()));

        if (this.lastMoveToGetHere != 8)
            this.heuristicValue += parentNode.state.getSeeds(ourPlayersSide, lastMoveToGetHere) - bigPotConstant;

        if (state.getSeedsInStore(this.ourPlayersSide) >= minNumberOfSeedsForSureWin)
            this.heuristicValue = 9999; // set the heuristic value to a silly number if it is a guaranteed win
        else if (state.getSeedsInStore(this.ourPlayersSide.opposite()) >= minNumberOfSeedsForSureWin)
            this.heuristicValue = -9999; // set the heuristic value to a silly number if a guaranteed loss
        else if (this.playerMakingTheNextMove == parentNode.playerMakingTheNextMove)
        {
            this.heuristicValue += extraMoveConstant + lastMoveToGetHere;
              /*  if (parentNode.state.getSeeds(ourPlayersSide.opposite(), (8 - lastMoveToGetHere)) == 0)
                    this.heuristicValue += parentNode.state.getSeeds(ourPlayersSide.opposite(), lastMoveToGetHere);
                this.heuristicValue -= lastMoveToGetHere;
                if (parentNode.state.getSeeds(ourPlayersSide.opposite(), (8 - lastMoveToGetHere)) == 0)
                    this.heuristicValue -= parentNode.state.getSeeds(ourPlayersSide.opposite(), lastMoveToGetHere);*/
        }

    }

    @Override
    public int compareTo(Node other)
    {
        return this.heuristicValue > other.heuristicValue ? -1 : (this.heuristicValue < other.heuristicValue ? 1 : 0);
    }

}
