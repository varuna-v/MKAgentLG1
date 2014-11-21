package src.MKAgent;

import java.util.ArrayList;

/**
 * Created by mbax9vv2 on 21/11/14.
 */
public class Node
{
    Side playerMakingMove;
    Board state;
    double value;
    public ArrayList<Node> children;

    public Node(Side playerMakingMove)
    {
        this.playerMakingMove = playerMakingMove;
        state = new Board(7,7);
        value = Integer.MIN_VALUE;
        children = null;
    }

    public Node(Side playerMakingMove, Board state)
    {
        this.playerMakingMove = playerMakingMove;
        this.state = state;
        value = Integer.MIN_VALUE;
        children = null;
    }

    //TODO
    int propagate(int value)
    {
        int maxValue = Integer.MIN_VALUE;
        for(Node child : children)
        {

        }
        return maxValue;
    }
    //TODO

    void setValue(int newVal)
    {
        value = newVal;
    }

    public int getValue()
    {
        //TODO
        if (children == null || children.size() == 0)
            return  Integer.MIN_VALUE;
    }
}
