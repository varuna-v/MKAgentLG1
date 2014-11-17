package src.MKAgent;

import src.MKAgent.Enums.PlayerSide;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class InputParser
{
    private static PlayerSide _thisAgentsSide;

    public static void parseInput(String inputMessage)
    {
        if (inputMessage == null || inputMessage.equals("") || !inputMessage.contains(";"))
        {
            throw new IllegalArgumentException("inputMessage");
        }
        String ucInputMessage = inputMessage.toUpperCase();
        String commands[] = ucInputMessage.split(";");

        if (commands.length < 1)
        {
            throw new IllegalArgumentException("inputMessage");
        }

        if (commands[0].equals("START"))
        {
            TreeBuilder.initialiseTree();
            _thisAgentsSide = PlayerSide.valueOf(commands[1]);
        }
        else if (commands[0].equals("CHANGE"))
        {
            try
            {
                int move = Integer.parseInt(commands[1]);
                PlayerSide nextSide = getPlayerSide(commands[3]);
                TreeBuilder.UpdateTree(move, nextSide);
            }
            catch (Exception ex)
            {
                throw new IllegalArgumentException("inputMessage");
            }
        }

    }

    private static PlayerSide getPlayerSide(String inputPlayerStr)
    {
        if (inputPlayerStr.equals("YOU"))
            return _thisAgentsSide;
        else
            if (_thisAgentsSide == PlayerSide.NORTH)
                return PlayerSide.SOUTH;
            else
                return PlayerSide.NORTH;
    }

}