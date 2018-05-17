import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Huffman {

    static void compress(InputStream is, OutputStream os) throws Exception {
        int byteValue;
        int[] frequency = new int[256];
        String[] dictionary = new String[256];
        List<Byte> input = new ArrayList<>();
        List<Node> tree = new ArrayList<>();
        while ((byteValue = is.read()) != -1) {
            frequency[byteValue]++;
            input.add((byte) byteValue);
        }
        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] != 0) {
                Node n = new Node(frequency[i], i,0, null, null);
                tree.add(n);
            }
            tree.sort(new Comparator<Node>() {
                @Override
                public int compare(Node node, Node node2) {
                    return Integer.compare(node.byteValue, node2.byteValue);
                }
            });
            while (tree.size() > 1) {
                Node littleLeft = littleNode(tree);
                Node littleRight = littleNode(tree);
                tree.add(new Node(littleLeft.frequency + littleRight.frequency,0, 0, littleLeft, littleRight));
            }
        }
    }

    public static Node littleNode(List<Node> tree) {
        Node lessFrequency = tree.get(0);
        for (int i = 0; i < tree.size(); i++) {
            if (lessFrequency.frequency > tree.get(i).frequency) {
                lessFrequency = tree.get(i);
            }
        }
        tree.remove(lessFrequency);
        return lessFrequency;
    }
    public static Array dictionary(List<Node> tree, Array dictionary){

    }
}


