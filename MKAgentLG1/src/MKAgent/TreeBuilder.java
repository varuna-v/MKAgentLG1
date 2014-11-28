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
        _currentNode = new Node(Side.NORTH);
    }

    public static void buildNextLayer(Node node){
       ArrayList<Node> kids = new ArrayList<Node>();
       for (int i = 1; i <= node.state.getNoOfHoles(); i++){
           Move move = new Move(node.playerMakingMove, i);
           if(Kalah.isLegalMove(node.state, move)){
               Board childBoard = null;
               try {
                   childBoard = node.state.clone();
                   Side nextPlayer = Kalah.makeMove(childBoard, move);
                   Node child = new Node(nextPlayer, childBoard, node.ourPlayer, node, i);
                   //child.value = childBoard.getSeedsInStore(node.playerMakingMove)- node.state.getSeedsInStore(node.playerMakingMove) + i;
                   kids.add(child);
               } catch (CloneNotSupportedException e) {
                   System.out.println("Evil Clone");
               }
           }
       }
        Collections.sort(kids);
        node.children = kids;

        if (depth > 0 && startNode.children != null)
        {
            for (Node child : startNode.children)
            {
                build(child, depth - 1);
            }
        }
    }

    public static Board getCurrentBoard()
    {
        if (_currentNode != null)
        {
            return _currentNode.state;
        }
        else
            return null;
    }

    public static void UpdateTree(int move, Side side)
    {
        //TODO
       // throw new NotImplementedException();
    }
}
