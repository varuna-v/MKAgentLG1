package src.MKAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbax9vv2 on 21/11/14.
 */
public class Node implements Comparable<Node>
{
    Side playerMakingMove;
    Board state;
    double value;
    ArrayList<Node> children;

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

        return 1;
    }

    @Override
    public int compareTo(Node other) {
        return this.value > other.value ? 1 : (this.value < other.value ? -1 : 0);
    }
}
