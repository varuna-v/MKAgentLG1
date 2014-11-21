package src.MKAgent;

import src.MKAgent.Enums.PlayerSide;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class TreeBuilder
{
    private static Node _currentNode;

    public static void initialiseTree()
    {
        _currentNode = new Node();
    }

    public static void build(Node startNode, int depth)
    {
        if (startNode == null) { startNode = _currentNode; }

        if (depth > 0)
        {
            for(Node child : startNode.children)
            {
                build(child, depth-1);
            }
        }
    }

    public static void UpdateTree(int move, PlayerSide playerSide)
    {
        throw new NotImplementedException();
    }
}
