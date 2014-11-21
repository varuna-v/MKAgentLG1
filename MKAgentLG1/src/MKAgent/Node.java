package src.MKAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbax9vv2 on 21/11/14.
 */
public class Node
{
    Side playerSide;
    Board state;
    double value;
    ArrayList<Node> children;

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
