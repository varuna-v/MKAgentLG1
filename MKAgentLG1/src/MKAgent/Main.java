package src.MKAgent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by mbax9vv2 on 17/11/14.
 */
public class Main
{

    public static void Main(String args[])
    {
        try
        {
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String inputLine = bufferedReader.readLine();

            TreeBuilder gameTreeBuilder = new TreeBuilder();
            Agent agent = new Agent();
            Player nextPlayer = null;
            while (!inputLine.contains("END"))
            {
                try
                {
                    InputParser.parseInput(inputLine);

                    if (nextPlayer == Player.ThisAgent)
                    {
                        MoveSwap nextMove = agent.getNextMove(gameState);
                        System.out.println(nextMove.toString());
                    }


                }
                catch (Exception ex)
                {
                    //Implement fallback
                }
                inputLine = bufferedReader.readLine();
            }
        }
        catch (Exception ex)
        {
            //Implement fallback
        }
    }
}

