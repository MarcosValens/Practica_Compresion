import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class HuffmanTest {
    byte[] comp(byte[]input) throws Exception {
        InputStream is = new ByteArrayInputStream(input);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Huffman.compress(is, baos);
        return baos.toByteArray();
    }

    @Test
    public void compress() throws Exception {
        byte[] ar;

        ar = "abb".getBytes();
        assertArrayEquals(new byte[]{96}, comp(ar) );

        ar = "abcaba".getBytes();
        assertArrayEquals(new byte[]{115, 0}, comp(ar) );

        ar = "abccccca".getBytes();
        assertArrayEquals(new byte[]{79, (byte) 160}, comp(ar) );

        ar = "abcccaaaaddddcbbbbbaaaabbbeeeeeeeeeeeeeeeaab".getBytes();
        assertArrayEquals(new byte[]{(byte)144,5,81,36,(byte)133,86,
                (byte)169,95,(byte)255,(byte)255,(byte)255,(byte)233}, comp(ar) );

    }

    @org.junit.Test
    public void testFile() throws Exception {
        String s1 = Utils.directory + Utils.testFile;
        String s2 = s1 + ".comp.huff";

        InputStream is = new BufferedInputStream(new FileInputStream(s1));
        OutputStream os = new BufferedOutputStream(new FileOutputStream(s2));
        Huffman.compress(is, os);
        is.close();
        os.close();

        assertEquals("f8e9bfe71be57779b802ee2444fdc7a5", Utils.md5(new File(s2)));
    }

}