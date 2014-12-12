package src.MKAgent;

import java.util.ArrayList;

/**
 * Created by mbax9vv2 on 21/11/14.
 */
public class Node implements Comparable<Node>
{
    private static final double SWAP_CONSTANT = 99; // a constant for the value of a swap node
    Side playerMakingMove; // the player currently making the move on this node
    Board state; // the state of the board in the current node
    double pruneValue; // the pruning value of the node
    double heuristicValue; // the value of the current node
    ArrayList<Node> children; //
    Side ourPlayer;
    final static int extraMoveConstant = 8;
    int lastMoveToGetHere;
    boolean completedAttemptToBuildChildren = false;
    boolean completedBuildToRequiredDepth = false;
    int depth;

    int interestedInValuesAbove = Integer.MIN_VALUE;
    int interestedInValuesBelow = Integer.MAX_VALUE;

    private int minNumberOfSeedsForSureWin()
    {
        return (state.getNoOfHoles() * state.getNoOfHoles()) + 1;
    }

    public boolean isMaxNode()
    {
        return playerMakingMove == ourPlayer;
    }

    public Node(Side playerMakingMove, Side us)
    {
        this.playerMakingMove = playerMakingMove;
        state = new Board(7, 7);
        this.ourPlayer = us;
        heuristicValue = 0;
        pruneValue = heuristicValue;
        children = null;
        depth = 1;
    }

    public Node(Side playerMakingMove, Board state, Side us)
    {
        this.playerMakingMove = playerMakingMove;
        this.ourPlayer = us;
        this.state = state;
        children = null;
        heuristicValue = 0;
        pruneValue = heuristicValue;
        //evaluate();
    }

    public Node(Side playerMakingMove, Board state, Side us, Node parentNode, int move)
    {
        this.playerMakingMove = playerMakingMove;
        this.ourPlayer = us;
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
        this.heuristicValue = state.getSeedsInStore(this.ourPlayer) - parentNode.state.getSeedsInStore(parentNode.ourPlayer);
        this.heuristicValue -= (state.getSeedsInStore(this.ourPlayer.opposite()) - parentNode.state.getSeedsInStore(parentNode.ourPlayer.opposite()));
        if (this.lastMoveToGetHere == 8)
        {
            this.heuristicValue = SWAP_CONSTANT;
        }
        else
        {
            if (state.getSeedsInStore(this.ourPlayer) == minNumberOfSeedsForSureWin)
                this.heuristicValue = 9999; // set the heuristic value to a silly number if it is a guaranteed win
            else if (state.getSeedsInStore(this.ourPlayer.opposite()) == minNumberOfSeedsForSureWin)
                this.heuristicValue = -9999; // set the heuristic value to a silly number if a guaranteed loss
            else if (this.playerMakingMove == parentNode.getPMM())
            {
                if (this.isMaxNode()) // check if the current node is a maximising node for our player
                {
                    this.heuristicValue += (extraMoveConstant + lastMoveToGetHere);
                    if (parentNode.state.getSeeds(ourPlayer.opposite(), (8 - lastMoveToGetHere)) == 0)
                        this.heuristicValue += parentNode.state.getSeeds(ourPlayer.opposite(), lastMoveToGetHere);
                }
                else
                {
                    this.heuristicValue -= (extraMoveConstant + lastMoveToGetHere);
                    if (parentNode.state.getSeeds(ourPlayer.opposite(), (8 - lastMoveToGetHere)) == 0)
                        this.heuristicValue -= parentNode.state.getSeeds(ourPlayer.opposite(), lastMoveToGetHere);
                }
            }
            else
            {
                if (this.isMaxNode())
                {
                    this.heuristicValue -= lastMoveToGetHere;
                    if (parentNode.state.getSeeds(ourPlayer.opposite(), (8 - lastMoveToGetHere)) == 0)
                        this.heuristicValue -= parentNode.state.getSeeds(ourPlayer.opposite(), lastMoveToGetHere);
                }
                else
                {
                    this.heuristicValue += lastMoveToGetHere;
                    if (parentNode.state.getSeeds(ourPlayer.opposite(), (8 - lastMoveToGetHere)) == 0)
                        this.heuristicValue += parentNode.state.getSeeds(ourPlayer.opposite(), lastMoveToGetHere);
                }

            }
        }
    }

    public Side getPMM()
    {
        return this.playerMakingMove;
    }

    @Override
    public int compareTo(Node other)
    {
        return this.heuristicValue > other.heuristicValue ? -1 : (this.heuristicValue < other.heuristicValue ? 1 : 0);
    }

}
