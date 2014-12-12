package src.MKAgent;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class Agent
{
    public static int getNextBestMove(TreeBuilder treeBuilder, Side side)
    {
        if (!treeBuilder.getCurrentNode().completedBuildToRequiredDepth)
        {
            treeBuilder.buildTree();
        }

        Node currentNode = treeBuilder.getCurrentNode();
        int holeNumberToMove = currentNode.children.get(0).lastMoveToGetHere;

        if (currentNode.children != null && currentNode.children.size() > 0)
        {
            if (currentNode.depth == 1)
                treeBuilder.alphaBetaPruning(currentNode, -9999, 9999);
            else
                treeBuilder.alphaBetaPruning(currentNode, currentNode.alpha, currentNode.beta);

            Node favChild = currentNode.children.get(0);
            for (Node c : currentNode.children)
            {
                if (c.pruneValue > favChild.pruneValue)
                {
                    favChild = c;
                }
                else if (c.pruneValue == favChild.pruneValue)
                {
                    if (c.heuristicValue > favChild.heuristicValue)
                    {
                        favChild = c;
                    }
                }
            }
            holeNumberToMove = favChild.lastMoveToGetHere;
        }

        Move move = null;
        boolean couldNotFindMove = false;
        if (holeNumberToMove == 8 && currentNode.depth != 2)
            couldNotFindMove = true;
        else
        {
            move = new Move(side, holeNumberToMove);
            if (!Kalah.isLegalMove(currentNode.state, move))
                couldNotFindMove = true;
        }

        if (couldNotFindMove)
        {
            move = getNextLegalMove(currentNode.state, side);
            holeNumberToMove = move.getHole();
        }

        return holeNumberToMove;

    }

    public static Move getNextLegalMove(Board state, Side side)
    {
        for (int i = 7; i >= 1; i--)
        {
            Move move = new Move(side, i);
            if (Kalah.isLegalMove(state, move))
                return move;
        }
        return null;
    }


}
