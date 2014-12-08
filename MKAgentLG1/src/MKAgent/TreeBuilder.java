package src.MKAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

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

    private final int desiredTreeDepth = 3;  // pruning will therefore be based on a node's value based on (7-1=) 6 children

    public void quickBuildTreeForCurrentNode()
    {
        stop();
        buildTree();
        //_currentNode.evaluateBasedOnChildren();
    }

    public void buildTree()
    {
        buildTree(_currentNode, desiredTreeDepth);
        _currentNode.completedBuildToRequiredDepth = true;
    }

    private void buildTree(Node startNode, int depth)
    {
        if (depth >= 0)
        {
            if (startNode == null)
            {
                throw new IllegalArgumentException("startNode");
            }
            if (!startNode.completedAttemptToBuildChildren)
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

    public int alphabetaPruning(Node node, int alpha, int beta)
    {
        if (node.children == null || node.children.size() < 1)
        {
            //node.pruneValue = node.value;
            return node.pruneValue;
        }
        if (node.isMaxNode())
        {
            for (int i = 0; i < node.children.size(); i++)
            {
                alpha = Math.max(alpha, alphabetaPruning(node.children.get(i), alpha, beta));
                if (beta <= alpha)
                    break;
            }
            alpha = node.pruneValue;
            //node.pruneValue = alpha;
            return alpha;
        }
        else
        {
            for (int i = node.children.size() - 1; i >= 0; i--)
            {
                beta = Math.min(beta, alphabetaPruning(node.children.get(i), alpha, beta));
                if (beta <= alpha)
                    break;
            }
            beta = node.pruneValue;
            //node.pruneValue = beta;
            return beta;
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
                    if (node.isFirstMove)
                    {
                        child.isSecondMove = true;
                    }
                    kids.add(child);
                }
                catch (CloneNotSupportedException e)
                {
                    System.out.println("Evil Clone");
                }
            }
        }
        if (node.isSecondMove)
        {
            try
            {
                Board childBoard = node.state.clone();
                Node child = new Node(Side.NORTH, childBoard, node.ourPlayer.opposite(), node, -1);
                kids.add(child);
            }
            catch (CloneNotSupportedException e)
            {
                System.out.println("Evil Clone");
            }
        }
        Collections.sort(kids);
        node.children = kids;
        node.completedAttemptToBuildChildren = true;
    }

    public Board getCurrentBoard()
    {
        if (_currentNode != null)
        {
            return _currentNode.state;
        }
        else
            return null;
    }

    public void UpdateTree(int move, Side side)
    {
        //side may not be needed
        // pauseProgram(15000);
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
            buildTree();
            //pruneTree();
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
