package src.MKAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbax9vv2 on 21/11/14.
 */
public class Node implements Comparable<Node>
{
    Side playerMakingMove;
    Board state;
    double value;
    ArrayList<Node> children;
    Side ourPlayer;
    final static int extraMoveConstant = 8;
    int lastMoveToGetHere;

    public Node(Side playerMakingMove, Side us)
    {
        this.playerMakingMove = playerMakingMove;
        state = new Board(7, 7);
        this.ourPlayer = us;
        value = Integer.MIN_VALUE;
        children = null;
    }

    public Node(Side playerMakingMove, Board state, Side us) {
        this.playerMakingMove = playerMakingMove;
        this.ourPlayer = us;
        this.state = state;
        children = null;
        value = 0;
        //evaluate();
    }

    public Node(Side playerMakingMove, Board state, Side us, Node parentNode, int move)
    {
        this.playerMakingMove = playerMakingMove;
        this.ourPlayer = us;
        this.state = state;
        children = null;
        lastMoveToGetHere = move;
        evaluate(parentNode, move);
    }

    private void evaluate(Node parentNode, int move)
    {

        this.value = state.getSeedsInStore(this.ourPlayer) - parentNode.state.getSeedsInStore(parentNode.ourPlayer);
        if (state.getSeedsInStore(this.ourPlayer) == (state.getNoOfHoles() * state.getNoOfHoles() + 1))
            this.value = Integer.MAX_VALUE;
        else if (state.getSeedsInStore(this.ourPlayer.opposite()) == (state.getNoOfHoles() * state.getNoOfHoles() + 1))
            this.value = Integer.MIN_VALUE;
            // if this is an extra turn move
        else if (this.playerMakingMove == parentNode.getPMM())
            this.value += extraMoveConstant + move;
        else
            this.value += move;
    }

    public Side getPMM()
    {
        return this.playerMakingMove;
    }

    @Override
    public int compareTo(Node other)
    {
        return this.value > other.value ? -1 : (this.value < other.value ? 1 : 0);
    }
}
