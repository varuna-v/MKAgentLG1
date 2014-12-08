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
    int pruneValue;
    int value;
    ArrayList<Node> children;
    Side ourPlayer;
    final static int extraMoveConstant = 8;
    int lastMoveToGetHere;
    boolean completedAttemptToBuildChildren = false;
    boolean completedBuildToRequiredDepth = false;
    boolean isFirstMove;
    boolean isSecondMove;

    int interestedInValuesAbove = -9999;
    int interestedInValuesBelow = 9999;

    private int minNumberOfSeedsForSureWin()
    {
        return (state.getNoOfHoles() * state.getNoOfHoles()) + 1;
    }

    public boolean isMaxNode()
    {
        return playerMakingMove == ourPlayer;
    }

    public static final int maxValue = 9999;
    public static final int minValue = -9999;

    public Node(Side playerMakingMove, Side us)
    {
        this.playerMakingMove = playerMakingMove;
        state = new Board(7, 7);
        this.ourPlayer = us;
        value = 0;
        pruneValue = value;
        children = null;
        isFirstMove = true;
        isSecondMove = false;
    }

    public Node(Side playerMakingMove, Board state, Side us)
    {
        this.playerMakingMove = playerMakingMove;
        this.ourPlayer = us;
        this.state = state;
        children = null;
        value = 0;
        isFirstMove = false;
        isSecondMove = false;
        defaultPrunevalue();
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
        defaultPrunevalue();
        isFirstMove = false;
        isSecondMove = false;
    }
/*
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
    }*/
    private void defaultPrunevalue(){
        int minNumberOfSeedsForSureWin = minNumberOfSeedsForSureWin();
        this.pruneValue = state.getSeedsInStore(this.ourPlayer);
        if (state.getSeedsInStore(this.ourPlayer) >= minNumberOfSeedsForSureWin)
            this.pruneValue = maxValue;
        else if (state.getSeedsInStore(this.ourPlayer.opposite()) >= minNumberOfSeedsForSureWin)
            this.pruneValue = minValue;
    }

    private void evaluateBasedOnThisNode(Node parentNode)
    {
        int minNumberOfSeedsForSureWin = minNumberOfSeedsForSureWin();
        this.value = state.getSeedsInStore(this.ourPlayer) - parentNode.state.getSeedsInStore(parentNode.ourPlayer);
        this.value -= (state.getSeedsInStore(this.ourPlayer.opposite()) - parentNode.state.getSeedsInStore(parentNode.ourPlayer.opposite()));
        if (state.getSeedsInStore(this.ourPlayer) >= minNumberOfSeedsForSureWin)
            this.value = maxValue;
        else if (state.getSeedsInStore(this.ourPlayer.opposite()) >= minNumberOfSeedsForSureWin)
            this.value = minValue;
        else if (this.playerMakingMove == parentNode.getPMM())
        {
            if (this.isMaxNode())
                this.value += (extraMoveConstant + lastMoveToGetHere);
            else
                this.value -= (extraMoveConstant + lastMoveToGetHere);
        }

        else
        {
            if (this.isMaxNode())
                this.value -= lastMoveToGetHere;
            else
                this.value += lastMoveToGetHere;
        }
    }

    public Side getPMM()
    {
        return this.playerMakingMove;
    }

    @Override
    public int compareTo(Node other)
    {
        if (this.pruneValue > other.pruneValue)
            return -1;
        else if (this.pruneValue > other.pruneValue)
            return 1;
        else
        {
            if (this.value > other.value)
                return -1;
            else if (this.value < other.value)
                return 1;
            else
                return 0;
        }
    }

}
