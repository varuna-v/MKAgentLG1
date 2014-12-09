package src.MKAgent;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class Agent
{
    public static Move getNextBestMove(TreeBuilder treeBuilder, Side side)
    {
        Node currentNode = treeBuilder.getCurrentNode();
        if (!currentNode.completedBuildToRequiredDepth)
        {
            //TODO implement timer check
            treeBuilder.quickBuildTreeForCurrentNode();
        }
        Board state = treeBuilder.getCurrentBoard();
        Move move;
        int holeNumberToMove = currentNode.children.get(0).lastMoveToGetHere;
        move = new Move(side, holeNumberToMove);
        if (currentNode != null && currentNode.children != null && currentNode.children.size() > 0)
        {
            treeBuilder.alphabetaPruning(currentNode, -9999, 9999);
            for(Node c:currentNode.children){
                if(c.pruneValue == (currentNode.pruneValue-currentNode.value)){
                    holeNumberToMove = c.lastMoveToGetHere;
                    move = new Move(side, holeNumberToMove);
                    if(Kalah.isLegalMove(currentNode.state, move))
                        break;
                    else
                        move = getNextLegalMove(state, side);

                }
            }

        }
        else
        {
            move = getNextLegalMove(state, side);
        }
        return move;
    }

    private static Move getNextLegalMove(Board state, Side side)
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
