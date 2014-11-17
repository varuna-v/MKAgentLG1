package src.MKAgent;

import src.MKAgent.Enums.PlayerSide;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class Player
{
    private PlayerSide _side;

    private int _numberOfSeedsInPots[] = new int[7];

    private int _numberOfSeedsInStore;

    public Player(PlayerSide side)
    {
        _side = side;
        for (int numberOfSeedsInPot : _numberOfSeedsInPots)
        {
            numberOfSeedsInPot = 7;
        }
        _numberOfSeedsInStore = 0;

    }
}
