//package src.MKAgent;
package src.MKAgent;

/**
 * Created by mbax2sp2 on 21/11/14.
 */
public class Test
{

    public static void main(String[] args)
    {
        try
        {
            Board myb = new Board(7, 7);
            System.out.println("made board");
            Node root;
            Node kid;

            TreeBuilder treeBuilder = new TreeBuilder(Side.NORTH);

            treeBuilder.start();
            sleepForABit();

            root = treeBuilder.getCurrentNode();
            System.out.println("Board " + root.state + "\n has " + root.children.size() + " kids");
            for (int i = 0; i < root.children.size(); i++)
            {
                System.out.println("Value = " + root.children.get(i).value + " | PruneValue = " +root.children.get(i).pruneValue);
                System.out.println(root.children.get(i).state);
            }
            treeBuilder.alphabetaPruning(root, -9999999, +9999999);

            root = treeBuilder.getCurrentNode();
            System.out.println("Value = " + root.value + " | PruneValue = " +root.pruneValue);
            System.out.println("Board " + root.state + "\n has " + root.children.size() + " kids");
            for (int i = 0; i < root.children.size(); i++)
            {
                System.out.println("Value = " + root.children.get(i).value + " | PruneValue = " +root.children.get(i).pruneValue);
                System.out.println(root.children.get(i).state);
            }

            /*
            kid = root.children.get(0);
            System.out.println("best child is");
            System.out.println(kid.value);
            System.out.println(kid.state);

            System.out.println("kids of best are");
            for (int i = 0; i < kid.children.size(); i++)
            {
                System.out.println(kid.children.get(i).value);
                System.out.println(kid.children.get(i).state);
            }

            System.out.println(root.children.get(0).value);
            System.out.println(root.children.get(0).state);
            treeBuilder.UpdateTree(root.children.get(0).lastMoveToGetHere, Side.SOUTH);
            sleepForABit();

            root = treeBuilder.getCurrentNode();
            System.out.println("Board " + root.state + "\n has " + root.children.size() + " kids");
            for (int i = 0; i < root.children.size(); i++)
            {
                System.out.println(root.children.get(i).value);
                System.out.println(root.children.get(i).state);
            }

            kid = root.children.get(0);
            System.out.println("best child is");
            System.out.println(kid.value);
            System.out.println(kid.state);

            System.out.println("kids of best are");
            for (int i = 0; i < kid.children.size(); i++)
            {
                System.out.println(kid.children.get(i).value);
                System.out.println(kid.children.get(i).state);
            }*/


        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void sleepForABit()
    {
        try
        {
            Thread.sleep(10000);
        }
        catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    private static void buildNextLayerTest()
    {
        Board myb = new Board(7, 7);
        System.out.println("made board");
        Node root = new Node(Side.SOUTH, myb, Side.SOUTH);
        System.out.println("made node");

        TreeBuilder treeBuilder = new TreeBuilder(Side.SOUTH);

        treeBuilder.buildNextLayer(root);
        System.out.println(root.children.size());
        for (int i = 0; i < root.children.size(); i++)
        {
            System.out.println(root.children.get(i).value);
            System.out.println(root.children.get(i).state);
        }

        Node kid = root.children.get(0);
        treeBuilder.buildNextLayer(kid);

        System.out.println("best child is");
        System.out.println(kid.value);
        System.out.println(kid.state);

        System.out.println("kids of best are");
        for (int i = 0; i < kid.children.size(); i++)
        {
            System.out.println(kid.children.get(i).value);
            System.out.println(kid.children.get(i).state);
        }
    }
}
