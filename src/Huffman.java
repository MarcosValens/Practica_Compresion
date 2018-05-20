import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class Huffman {

    static void compress(InputStream is, OutputStream os) throws Exception {
        int byteValue;
        int[] frequency = new int[256];
        StringBuilder direction = new StringBuilder();
        List<Byte> input = new ArrayList<>();
        HashMap<Byte, String> dictionary = new HashMap<>();
        List<Node> tree = new ArrayList<>();
        while ((byteValue = is.read()) != -1) {
            frequency[byteValue]++;
            input.add((byte) byteValue);
        }
        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] != 0) {
                Node n = new Node(frequency[i], i, null, null);
                tree.add(n);
            }
        }
        tree.sort(Comparator.comparingInt(node -> (byte)node.byteValue));
        while (tree.size() > 1) {
            Node littleLeft = littleNode(tree);
            Node littleRight = littleNode(tree);
            tree.add(new Node(littleLeft.frequency + littleRight.frequency, -9999, littleLeft, littleRight));
        }
        tour(tree.get(0), direction, dictionary);
        String binary = toBinary(dictionary,input);
        translate(binary,os);
    }

    public static Node littleNode(List<Node> tree) {
        Node lessFrequency = tree.get(0);
        for (Node aTree : tree) {
            if (lessFrequency.frequency > aTree.frequency) {
                lessFrequency = aTree;
            }
        }
        tree.remove(lessFrequency);
        return lessFrequency;
    }

    static void tour(Node node, StringBuilder direction, HashMap dictionary) {
        if (node.byteValue != -9999) {
            String path = direction.toString();
            dictionary.put((byte)node.byteValue, path);
        }
        if (node.left != null) {
            direction.append(0);
            tour(node.left, direction, dictionary);
        }
        if (node.right != null) {
            if (direction.length() > 0) {
                direction.setLength(direction.length() - 1);
            }
            direction.append(1);
            tour(node.right, direction, dictionary);
            direction.setLength(direction.length() - 1);
        }
    }

    static String toBinary(HashMap dictionary, List<Byte> input) {
        String binary = "";
        for (Byte anInput : input) {
            byte byte_ = anInput;
            binary += dictionary.get(byte_);
        }
        return binary;
    }

    static void translate(String binary, OutputStream os) throws IOException {
        String bits = "";
        for (int i = 0; i < binary.length(); i++) {
            bits += binary.charAt(i);
            if (bits.length() == 8){
                os.write(Integer.parseInt(bits, 2));
                bits = "";
            }
        }
        if (bits.length() < 7 && !bits.equals("")){
            int zeros = 7 - bits.length();
            for (int i = 0; i <= zeros ; i++) {
                bits += "0";
            }
            os.write(Integer.parseInt(bits, 2));
        }
    }
}