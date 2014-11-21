package src.MKAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbax9vv2 on 21/11/14.
 */
public class Node
{

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


}
