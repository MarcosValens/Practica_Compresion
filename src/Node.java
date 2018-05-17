public class Node {
    int frequency;
    int byteValue;
    int direction;
    Node left;
    Node right;
    public Node (int frequency,int byteValue, int direction,Node left, Node right){
        this.frequency = frequency;
        this.byteValue = byteValue;
        this.direction = direction;
        this.left = left;
        this.right = right;
    }
}
