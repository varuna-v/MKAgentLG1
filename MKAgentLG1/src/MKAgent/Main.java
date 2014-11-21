package src.MKAgent;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * The main application class. It also provides methods for communication
 * with the game engine.
 */
public class Main
{
    /**
     * Input from the game engine.
     */
    private static Reader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Sends a message to the game engine.
     *
     * @param msg The message.
     */
    public static void sendMsg(String msg)
    {
        System.out.print(msg);
        System.out.flush();
    }

    /**
     * Receives a message from the game engine. Messages are terminated by
     * a '\n' character.
     *
     * @return The message.
     * @throws IOException if there has been an I/O error.
     */
    public static String recvMsg() throws IOException
    {
        StringBuilder message = new StringBuilder();
        int newCharacter;

        do
        {
            newCharacter = input.read();
            if (newCharacter == -1)
                throw new EOFException("Input ended unexpectedly.");
            message.append((char) newCharacter);
        } while ((char) newCharacter != '\n');

        return message.toString();
    }

    /**
     * The main method, invoked when the program is started.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args)
    {
        try
        {
            String receivedMessage = recvMsg();
            boolean areWeSouth = false;
            while (!receivedMessage.equals(""))
            {
                MsgType messageType = Protocol.getMessageType(receivedMessage);
                boolean areWeMakingTheNextMove = false;
                switch (messageType)
                {
                    case START:
                        areWeMakingTheNextMove = Protocol.interpretStartMsg(receivedMessage);
                        areWeSouth = areWeMakingTheNextMove;
                        TreeBuilder.initialiseTree();
                        break;
                    case STATE:
                        Protocol.MoveTurn moveTurn = Protocol.interpretStateMsg(receivedMessage, TreeBuilder.getCurrentBoard());
                        if (moveTurn.move == -1)
                        {
                            areWeSouth = false;
                        }
                        if (moveTurn.end)
                        {
                            System.exit(0);
                        }
                        areWeMakingTheNextMove = moveTurn.again;
                        boolean isSouthPlayingNext = false;
                        isSouthPlayingNext = (areWeMakingTheNextMove && areWeSouth) || (!areWeMakingTheNextMove && !areWeSouth);
                        Side nextSide = isSouthPlayingNext ? Side.SOUTH : Side.NORTH;
                        TreeBuilder.UpdateTree(moveTurn.move, nextSide);
                        break;
                    case END:
                        System.exit(0);
                }
                if (areWeMakingTheNextMove)
                {
                    //TODO
                    Agent.getNextBestMove();
                }
            }
        }
        catch (Exception ex)
        {
            //TODO
        }

    }
}
