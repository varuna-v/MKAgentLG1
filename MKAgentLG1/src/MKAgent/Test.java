package src.MKAgent;
/**
 * Created by mbax2sp2 on 21/11/14.
 */
public class Test {

    public static void main(String[] args){
        try{
        Board myb = new Board(7,7);
        System.out.println("made board");
        Node root = new Node(Side.NORTH,myb);
        System.out.println("made node");
        TreeBuilder.buildNextLayer(root);
        System.out.println(root.children.size());
        for(int i = 0; i < root.children.size(); i++){
            System.out.println(root.children.get(i).value);
            System.out.println(root.children.get(i).state);
        }
        }catch(Exception e){
         System.out.println("Sorry");
        }
    }
}
