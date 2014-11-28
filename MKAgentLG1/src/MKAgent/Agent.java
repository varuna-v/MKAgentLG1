package src.MKAgent;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class Agent
{
    public static Move getNextBestMove(Board state, Side side)
    {
        //TODO
        for (int i = 7; i >= 1; i--)
        {
            Move move = new Move(side, i);
            if (Kalah.isLegalMove(state, move))
                return move;
        }
        return null;
    }
}
