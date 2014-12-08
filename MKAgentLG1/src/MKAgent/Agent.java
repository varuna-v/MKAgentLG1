package src.MKAgent;

import java.util.Collections;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class Agent
{
    public static int getNextBestMove(TreeBuilder treeBuilder, Side side)
    {
        Node currentNode = treeBuilder.getCurrentNode();
        if (!currentNode.completedBuildToRequiredDepth)
        {
            //TODO implement timer check
            treeBuilder.quickBuildTreeForCurrentNode();
        }
        Board state = treeBuilder.getCurrentBoard();
        int moveHole = -17;
        if (currentNode != null && currentNode.children != null && currentNode.children.size() > 0)
        {
            treeBuilder.alphabetaPruning(currentNode, Node.minValue, Node.maxValue);
            Collections.sort(currentNode.children);
            moveHole = currentNode.children.get(0).lastMoveToGetHere;
            /*for (Node c : currentNode.children)
            {
                if (c.pruneValue == (currentNode.pruneValue - currentNode.value))
                {
                    moveHole = c.lastMoveToGetHere;
                    break;
                }
            }*/
        }
        else
        {
            moveHole = getNextLegalMove(currentNode.state, side);
        }


        if (moveHole == -1 && !currentNode.isSecondMove)
            moveHole = getNextLegalMove(currentNode.state, side);
        else if (moveHole < 1)
        {
            moveHole = getNextLegalMove(currentNode.state, side);
        }
        else
        {
            Move move = new Move(side, moveHole);
            if (!Kalah.isLegalMove(currentNode.state, move))
                moveHole = getNextLegalMove(currentNode.state, side);
        }

        return moveHole;
    }

    private static int getNextLegalMove(Board state, Side side)
    {
        for (int i = 7; i >= 1; i--)
        {
            Move move = new Move(side, i);
            if (Kalah.isLegalMove(state, move))
                return i;
        }
        return 7;
    }
}
