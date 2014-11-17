package src.MKAgent;

import java.util.List;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class Node
{
    private List<Node> _children;

    private BoardState _boardState;

    public void initialise()
    {
        _boardState = new BoardState();
    }
}
