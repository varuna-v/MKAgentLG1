package src.MKAgent;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class TreeBuilder implements Runnable {
    private static Node _currentNode;

    Thread treeBuilderThread;

    public static Node getCurrentNode() {
        return _currentNode;
    }

    public static void initialiseTree() {
        _currentNode = new Node(Side.NORTH);
    }

    public static void buildTree(Node startNode) {
        if (startNode == null) {
            throw new IllegalArgumentException("startNode");
        }
        if (startNode.children == null)
            buildNextLayer(startNode);

        if (startNode.children != null) {
            for (Node child : startNode.children) {
                buildTree(child);
            }
        }
    }

    public static void buildNextLayer(Node node) {
        ArrayList<Node> kids = new ArrayList<Node>();
        for (int i = 1; i <= node.state.getNoOfHoles(); i++) {
            Move move = new Move(node.playerMakingMove, i);
            if (Kalah.isLegalMove(node.state, move)) {
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
        Collections.sort(kids, Collections.reverseOrder());
        node.children = kids;

    }

    public static Board getCurrentBoard() {
        if (_currentNode != null) {
            return _currentNode.state;
        } else
            return null;
    }

    public static void UpdateTree(int move, Side side) {
        //TODO
        // throw new NotImplementedException();
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

    @Override
    public void run() {
        buildTree(_currentNode);
    }

    public void start() {
        if (treeBuilderThread == null) {
            treeBuilderThread = new Thread(this, "treeBuilderThread");
            treeBuilderThread.start();
        }
    }

    public void  stop() {
        if (treeBuilderThread != null && treeBuilderThread.isAlive())
            treeBuilderThread.
    }
}
