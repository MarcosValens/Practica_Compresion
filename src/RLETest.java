import java.io.*;

import static org.junit.Assert.*;

public class RLETest {

    private void test1(byte[] expected, byte[] input) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        RLE.compress(new ByteArrayInputStream(input), bos);
        assertArrayEquals(expected, bos.toByteArray());
    }

    private void test2(byte[] expected, byte[] input) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        RLE.decompress(new ByteArrayInputStream(input), bos);
        assertArrayEquals(expected, bos.toByteArray());
    }

    @org.junit.Test
    public void compress() throws Exception {
        test1(new byte[]{1, 1, 4}, new byte[]{1, 1, 1, 1, 1, 1});
        test1(new byte[]{1, 1, 0}, new byte[]{1, 1});
        test1(new byte[]{1, 2, 3}, new byte[]{1, 2, 3});
        test1(new byte[]{1, 1, 0, 2, 2, 0, 3, 3, 0}, new byte[]{1, 1, 2, 2, 3, 3});
        test1(new byte[]{1, 2, 3, 3, 1, 4}, new byte[]{1, 2, 3, 3, 3, 4});
        test1(new byte[]{1, 2, 3, 3, 0}, new byte[]{1, 2, 3, 3});
        byte[] ar;


        ar = new byte[260];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = 100;
        }

        test1(new byte[]{100, 100, (byte) 255, 100, 100, 1}, ar);

        ar = new byte[550];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = 100;
        }

        test1(new byte[]{100, 100, (byte) 255, 100, 100, (byte) 255, 100, 100, 34}, ar);
        ar = new byte[1000];
        for (int i = 0; i < 1000; i++) {
            ar[i] = (byte) 255;
        }
        test1(new byte[]{(byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 227}, ar);
    }

    @org.junit.Test
    public void decompress() throws Exception {
        test2(new byte[]{5, 5}, new byte[]{5, 5, 0});
        test2(new byte[]{1, 2, 3}, new byte[]{1, 2, 3});
        test2(new byte[]{1, 2, 2, 3}, new byte[]{1, 2, 2, 0, 3});
        test2(new byte[]{1, 2, 3, 3, 3, 3}, new byte[]{1, 2, 3, 3, 2});
        test2(new byte[]{5}, new byte[]{5});
        test2(new byte[]{1, 1, 2, 2, 3, 3, 4, 4}, new byte[]{1, 1, 0, 2, 2, 0, 3, 3, 0, 4, 4, 0});
        test2(new byte[]{5, 5, 5, 5, 5, 5, 5}, new byte[]{5, 5, 5});
        test2(new byte[]{0, 0, 0, 0, 0}, new byte[]{0, 0, 3});

        byte[] ar;

        ar = new byte[202];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = 55;
        }
        test2(ar, new byte[]{55, 55, (byte) 200});

        ar = new byte[259];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = 55;
        }
        test2(ar, new byte[]{55, 55, (byte) 255, 55, 55, 0});

        ar = new byte[15934];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = 55;
        }
        test2(ar, new byte[]{55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255, 55, 55, (byte) 255,});

        ar = new byte[1000];{
            for (int i = 0; i < 1000; i++) {
                ar[i] = (byte) 255;
            }
        }
        test2(ar, new byte[]{(byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 227});

        ar = new byte[1000];
            for (int i = 0; i < 1000; i++) {
                ar[i] = (byte) 0;
            }
        test2(ar, new byte[]{(byte) 0, (byte) 0, (byte) 255, (byte) 0, (byte) 0, (byte) 255, (byte) 0, (byte) 0, (byte) 255, (byte) 0, (byte) 0, (byte) 227});
    }

    @org.junit.Test
    public void testFile() throws Exception {
        String s1 = Utils.directory + Utils.testFile;
        String s2 = s1 + ".comp.rle";
        String s3 = s1 + ".decomp.rle";

        InputStream is = new BufferedInputStream(new FileInputStream(s1));
        OutputStream os = new BufferedOutputStream(new FileOutputStream(s2));
        RLE.compress(is, os);
        is.close();
        os.close();

        is = new BufferedInputStream(new FileInputStream(s2));
        os = new BufferedOutputStream(new FileOutputStream(s3));
        RLE.decompress(is, os);
        is.close();
        os.close();

        assertEquals("53b64328a6be29b9444dd5fded78d39a", Utils.md5(new File(s2)));
        assertEquals("bbcd6a7fc6f98ee378f9d2631dbedfc9", Utils.md5(new File(s3)));
    }


}