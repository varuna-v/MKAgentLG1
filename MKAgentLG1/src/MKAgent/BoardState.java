package src.MKAgent;

import src.MKAgent.Enums.PlayerSide;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class BoardState
{
    private Player _player1;

    private Player _player2;

    public BoardState()
    {
        _player1 = new Player(PlayerSide.South);
        _player2 = new Player(PlayerSide.North);
    }

    public void swap()
    {
        Player tempPlayer = _player1;
        _player1 = _player2;
        _player2 = tempPlayer;
    }

}
