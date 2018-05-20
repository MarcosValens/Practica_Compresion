/**********************************************Creacion del objeto Node************************************************/
public class Node {
    int frequency;
    int byteValue;
    Node left;
    Node right;
    public Node (int frequency, int byteValue, Node left, Node right){
        this.frequency = frequency;
        this.byteValue = byteValue;
        this.left = left;
        this.right = right;
    }
}
