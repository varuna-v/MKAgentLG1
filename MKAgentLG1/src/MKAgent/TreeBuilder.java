package src.MKAgent;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class TreeBuilder //implements Runnable
{
    private Node _currentNode;

    PrintWriter writer;

    //Thread treeBuilderThread;

    public Node getCurrentNode()
    {
        return _currentNode;
    }

    public TreeBuilder(Side thisAgentsSide)
    {
        _currentNode = new Node(Side.SOUTH, thisAgentsSide);
        buildTree();
    }

    private final int desiredTreeDepth = 6;

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

    public void buildNextLayer(Node node)
    {
        ArrayList<Node> kids = new ArrayList<Node>();
        for (int i = 1; i <= node.state.getNoOfHoles(); i++)
        {
            Move move = new Move(node.playerMakingTheNextMove, i);
            if (Kalah.isLegalMove(node.state, move))
            {
                Board childBoard = null;
                try
                {
                    childBoard = node.state.clone();
                    Side nextPlayer = Kalah.makeMove(childBoard, move);
                    if (node.depth == 1)
                        nextPlayer = node.playerMakingTheNextMove.opposite();
                    Node child = new Node(nextPlayer, childBoard, node.ourPlayersSide, node, i);
                    kids.add(child);
                }
                catch (CloneNotSupportedException e)
                {
                    System.out.println("Evil Clone");
                }
            }
        }
        if (node.depth == 2)
        {
            try
            {
                //give the move for swap the value 8
                Move move = new Move(node.playerMakingTheNextMove, 8);
                Board childBoard = node.state.clone();
                //the new node is: the original first player, the new board, our player swaps position,
                Node child = new Node(node.playerMakingTheNextMove, childBoard, node.ourPlayersSide.opposite(), node, 8);
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

    public double alphaBetaPruning(Node node, double alpha, double beta)
    {
        double futureMultiplier = 0.5;
        if (node.children == null || node.children.size() < 1)
        {
            node.pruneValue = node.heuristicValue;
            return node.heuristicValue;
        }
        if (node.isMaxNode())
        {
            for (int i = 0; i < node.children.size(); i++)
            {
                alpha = Math.max(alpha, alphaBetaPruning(node.children.get(i), alpha, beta));
                if (beta <= alpha)
                    break;
            }
            node.alpha += node.heuristicValue * futureMultiplier;
            node.pruneValue = alpha;
            return alpha;
        }
        else
        {
            for (int i = node.children.size() - 1; i >= 0; i--)
            {
                beta = Math.min(beta, alphaBetaPruning(node.children.get(i), alpha, beta));
                if (beta <= alpha)
                    break;
            }
            node.beta += node.heuristicValue * futureMultiplier;
            node.pruneValue = beta;
            return beta;
        }
    }

    public void UpdateTree(int move)
    {
        if (_currentNode != null && _currentNode.children != null)
        {
            for (Node child : _currentNode.children)
            {
                if (child.lastMoveToGetHere == move)
                {
                    _currentNode = child;
                }
            }
        }
    }
}

