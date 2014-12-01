package src.MKAgent;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class TreeBuilder implements Runnable
{
    private Node _currentNode;

    Thread treeBuilderThread;

    public Node getCurrentNode()
    {
        return _currentNode;
    }

    public TreeBuilder(Side thisAgentsSide)
    {
        _currentNode = new Node(Side.SOUTH, thisAgentsSide);
    }

    public void buildTree(Node startNode, int depth)
    {
        if (depth != 0)
        {
            if (startNode == null)
            {
                throw new IllegalArgumentException("startNode");
            }
            if (startNode.children == null)
                buildNextLayer(startNode);

            if (startNode.children != null)
            {
                for (Node child : startNode.children)
                {
                    buildTree(child, depth - 1);
                }
            }
        }
    }

    public void buildNextLayer(Node node)
    {
        ArrayList<Node> kids = new ArrayList<Node>();
        for (int i = 1; i <= node.state.getNoOfHoles(); i++)
        {
            Move move = new Move(node.playerMakingMove, i);
            if (Kalah.isLegalMove(node.state, move))
            {
                Board childBoard = null;
                try
                {
                    childBoard = node.state.clone();
                    Side nextPlayer = Kalah.makeMove(childBoard, move);
                    Node child = new Node(nextPlayer, childBoard, node.ourPlayer, node, i);
                    //child.value = childBoard.getSeedsInStore(node.playerMakingMove)- node.state.getSeedsInStore(node.playerMakingMove) + i;
                    kids.add(child);
                }
                catch (CloneNotSupportedException e)
                {
                    System.out.println("Evil Clone");
                }
            }
        }
        Collections.sort(kids);
        node.children = kids;
    }

    public Board getCurrentBoard()
    {
        if (_currentNode != null)
        {
            return _currentNode.state;
        } else
            return null;
    }

    public void UpdateTree(int move, Side side)
    {
        pauseProgram(10000);
        if (_currentNode != null && _currentNode.children != null)
        {
            for (Node child : _currentNode.children)
            {
                if (child.lastMoveToGetHere == move)
                {
                    stop();
                    _currentNode = child;
                    treeBuilderThread.run();
                }
            }
        }
    }

    public void pauseProgram(long milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run()
    {
        try
        {
            buildTree(_currentNode, 5);
        }
        catch (Exception e)
        {
            System.out.println("Tree builder errored".concat(e.getMessage()));
        }
    }

    public void start()
    {
        if (treeBuilderThread == null)
        {
            treeBuilderThread = new Thread(this, "treeBuilderThread");
            treeBuilderThread.start();
        }
    }

    public void stop()
    {
        if (treeBuilderThread != null)
            treeBuilderThread.interrupt();
    }
}
