package src.MKAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mbax9vv2 on 21/11/14.
 */
public class Node implements Comparable<Node>
{
    Side playerMakingMove;
    Board state;
    double pruneValue;
    double value;
    ArrayList<Node> children;
    Side ourPlayer;
    final static int extraMoveConstant = 8;
    int lastMoveToGetHere;
    boolean completedAttemptToBuildChildren = false;
    boolean completedBuildToRequiredDepth = false;
    boolean isFirstMove = false;
    boolean isSecondMove = false;
    int noMoves;

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
        value = 0;
        pruneValue = value;
        children = null;
        isFirstMove = true;
        noMoves=0;
    }

    public Node(Side playerMakingMove, Board state, Side us)
    {
        this.playerMakingMove = playerMakingMove;
        this.ourPlayer = us;
        this.state = state;
        children = null;
        value = 0;
        pruneValue = value;
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
        pruneValue = value;
        this.noMoves=parentNode.noMoves+1;
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
                this.value += children.get(0).value;
            }
            else
            {
                this.value += children.get(children.size() - 1).value;
            }
        }
    }

    private void evaluateBasedOnThisNode(Node parentNode)
    {
        int minNumberOfSeedsForSureWin = minNumberOfSeedsForSureWin();
        this.value = state.getSeedsInStore(this.ourPlayer) - parentNode.state.getSeedsInStore(parentNode.ourPlayer);
        this.value -= (state.getSeedsInStore(this.ourPlayer.opposite()) - parentNode.state.getSeedsInStore(parentNode.ourPlayer.opposite()));
        if (state.getSeedsInStore(this.ourPlayer) == minNumberOfSeedsForSureWin)
            this.value = 9999;
        else if (state.getSeedsInStore(this.ourPlayer.opposite()) == minNumberOfSeedsForSureWin)
            this.value = -9999;
        else if (this.playerMakingMove == parentNode.getPMM()){
            if(this.isMaxNode()){
                this.value += (extraMoveConstant + lastMoveToGetHere);
                if(parentNode.state.getSeeds(ourPlayer.opposite(), (8-lastMoveToGetHere)) == 0)
                    this.value += parentNode.state.getSeeds(ourPlayer.opposite(), lastMoveToGetHere);
            }
            else{
                this.value -= (extraMoveConstant + lastMoveToGetHere);
                if(parentNode.state.getSeeds(ourPlayer.opposite(), (8-lastMoveToGetHere)) == 0)
                    this.value -= parentNode.state.getSeeds(ourPlayer.opposite(), lastMoveToGetHere);
            }
        }
        else {
            if(this.isMaxNode()){
                this.value -= lastMoveToGetHere;
                if(parentNode.state.getSeeds(ourPlayer.opposite(), (8-lastMoveToGetHere)) == 0)
                    this.value -= parentNode.state.getSeeds(ourPlayer.opposite(), lastMoveToGetHere);
            }
            else{
                this.value += lastMoveToGetHere;
                if(parentNode.state.getSeeds(ourPlayer.opposite(), (8-lastMoveToGetHere)) == 0)
                    this.value += parentNode.state.getSeeds(ourPlayer.opposite(), lastMoveToGetHere);
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
        return this.value > other.value ? -1 : (this.value < other.value ? 1 : 0);
    }

}
