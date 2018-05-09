import org.junit.Test;

import java.io.*;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LZWTest {

    byte[] comp(byte[]input) throws Exception {
        InputStream is = new ByteArrayInputStream(input);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        LZW.compress(is, baos);
        byte[] ba = baos.toByteArray();
        return ba;
    }

    byte[] decomp(byte[]input) throws Exception {
        InputStream is = new ByteArrayInputStream(input);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        LZW.decompress(is, baos);
        byte[] ba = baos.toByteArray();
        return ba;
    }

    @Test
    public void compress() throws Exception {
        assertArrayEquals(new byte[]{0,'a',0,'b',0,'c'},
                comp("abc".getBytes()));

        /*assertArrayEquals(new byte[]{0,'a',1,'a'},
                comp("aaa".getBytes()));*/

        /*assertArrayEquals(new byte[]{0,'a',0,'a'},
                comp("aa".getBytes()));

        assertArrayEquals(new byte[]{0, 'a', 1, 'a', 2, 'a', 3, 'a', 4, 'a', 3, 'a'},
                comp("aaaaaaaaaaaaaaaaaaa".getBytes()));

        assertArrayEquals(new byte[]{0,'a',1,'a',1,'b'},
                comp("aaaab".getBytes()));

        assertArrayEquals(new byte[]{0,'a',0,'b',0,'r',1,'c',1,'d',1,'b',3,'a'},
                comp("abracadabra".getBytes()));

        assertArrayEquals(new byte[]{0,'a',1,'b',2,'b',0,'b',2,'a',5,'b'},
                comp("aababbbabaabab".getBytes()));

        assertArrayEquals(new byte[]{0, 101, 0, 115, 0, 116, 1, 114, 0, 110, 0, 111, 0, 99, 0, 108,
                        1, 105, 0, 100, 6, 109, 0, 97, 2, 116, 6, 105, 10, 101, 0, 111},
                comp("esternocleidomastoideo".getBytes()));

        assertArrayEquals(new byte[]{0, (byte)195, 0, (byte)137, 0, 114, 0, 97, 0, 115, 0, 101, 0, 32,
                        0, 117, 0, 110, 4, 32, 0, 118, 6, 122, 0, 44, 7, 101, 9, 32, 8, 110,
                        7, 112, 4, 105, 5, 32, 0, 109, 8, 121, 7, 108, 6, 106, 4, 110, 0, 111,
                        0, 46, 26, 46},
                comp("Érase una vez, en un pais muy lejano...".getBytes()));

        byte[] ar = new byte[300];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = 'a';
        }

        assertArrayEquals(new byte[]{0, 97, 1, 97, 2, 97, 3, 97, 4, 97, 5, 97, 6, 97, 7, 97, 8, 97,
                        9, 97, 10, 97, 11, 97, 12, 97, 13, 97, 14, 97, 15,
                        97, 16, 97, 17, 97, 18, 97, 19, 97, 20, 97, 21, 97, 22, 97, 23, 97},
                comp(ar));

        byte[] ar2 = new byte[260];
        for (int i = 0; i < ar2.length; i++) {
            ar2[i] = (byte)(i % 255);
        }

        assertArrayEquals(new byte[]{0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7,
                        0, 8, 0, 9, 0, 10, 0, 11, 0, 12, 0, 13, 0, 14, 0, 15, 0, 16,
                        0, 17, 0, 18, 0, 19, 0, 20, 0, 21, 0, 22, 0, 23, 0, 24, 0, 25,
                        0, 26, 0, 27, 0, 28, 0, 29, 0, 30, 0, 31, 0, 32, 0, 33, 0, 34,
                        0, 35, 0, 36, 0, 37, 0, 38, 0, 39, 0, 40, 0, 41, 0, 42, 0, 43,
                        0, 44, 0, 45, 0, 46, 0, 47, 0, 48, 0, 49, 0, 50, 0, 51, 0, 52, 0,
                        53, 0, 54, 0, 55, 0, 56, 0, 57, 0, 58, 0, 59, 0, 60, 0, 61, 0,
                        62, 0, 63, 0, 64, 0, 65, 0, 66, 0, 67, 0, 68, 0, 69, 0, 70, 0,
                        71, 0, 72, 0, 73, 0, 74, 0, 75, 0, 76, 0, 77, 0, 78, 0, 79, 0,
                        80, 0, 81, 0, 82, 0, 83, 0, 84, 0, 85, 0, 86, 0, 87, 0, 88, 0,
                        89, 0, 90, 0, 91, 0, 92, 0, 93, 0, 94, 0, 95, 0, 96, 0, 97, 0,
                        98, 0, 99, 0, 100, 0, 101, 0, 102, 0, 103, 0, 104, 0, 105, 0,
                        106, 0, 107, 0, 108, 0, 109, 0, 110, 0, 111, 0, 112, 0, 113, 0,
                        114, 0, 115, 0, 116, 0, 117, 0, 118, 0, 119, 0, 120, 0, 121, 0,
                        122, 0, 123, 0, 124, 0, 125, 0, 126, 0, 127, 0, (byte)128, 0,
                        (byte)129, 0, (byte)130, 0, (byte)131, 0, (byte)132, 0,
                        (byte)133, 0, (byte)134, 0, (byte)135, 0, (byte)136, 0,
                        (byte)137, 0, (byte)138, 0, (byte)139, 0, (byte)140, 0,
                        (byte)141, 0, (byte)142, 0, (byte)143, 0, (byte)144, 0,
                        (byte)145, 0, (byte)146, 0, (byte)147, 0, (byte)148, 0,
                        (byte)149, 0, (byte)150, 0, (byte)151, 0, (byte)152, 0,
                        (byte)153, 0, (byte)154, 0, (byte)155, 0, (byte)156, 0,
                        (byte)157, 0, (byte)158, 0, (byte)159, 0, (byte)160, 0,
                        (byte)161, 0, (byte)162, 0, (byte)163, 0, (byte)164, 0,
                        (byte)165, 0, (byte)166, 0, (byte)167, 0, (byte)168, 0,
                        (byte)169, 0, (byte)170, 0, (byte)171, 0, (byte)172, 0,
                        (byte)173, 0, (byte)174, 0, (byte)175, 0, (byte)176, 0,
                        (byte)177, 0, (byte)178, 0, (byte)179, 0, (byte)180, 0,
                        (byte)181, 0, (byte)182, 0, (byte)183, 0, (byte)184, 0,
                        (byte)185, 0, (byte)186, 0, (byte)187, 0, (byte)188, 0,
                        (byte)189, 0, (byte)190, 0, (byte)191, 0, (byte)192, 0,
                        (byte)193, 0, (byte)194, 0, (byte)195, 0, (byte)196, 0,
                        (byte)197, 0, (byte)198, 0, (byte)199, 0, (byte)200, 0,
                        (byte)201, 0, (byte)202, 0, (byte)203, 0, (byte)204, 0,
                        (byte)205, 0, (byte)206, 0, (byte)207, 0, (byte)208, 0,
                        (byte)209, 0, (byte)210, 0, (byte)211, 0, (byte)212, 0,
                        (byte)213, 0, (byte)214, 0, (byte)215, 0, (byte)216, 0,
                        (byte)217, 0, (byte)218, 0, (byte)219, 0, (byte)220, 0,
                        (byte)221, 0, (byte)222, 0, (byte)223, 0, (byte)224, 0,
                        (byte)225, 0, (byte)226, 0, (byte)227, 0, (byte)228, 0,
                        (byte)229, 0, (byte)230, 0, (byte)231, 0, (byte)232, 0,
                        (byte)233, 0, (byte)234, 0, (byte)235, 0, (byte)236, 0,
                        (byte)237, 0, (byte)238, 0, (byte)239, 0, (byte)240, 0,
                        (byte)241, 0, (byte)242, 0, (byte)243, 0, (byte)244, 0,
                        (byte)245, 0, (byte)246, 0, (byte)247, 0, (byte)248, 0,
                        (byte)249, 0, (byte)250, 0, (byte)251, 0, (byte)252, 0,
                        (byte)253, 0, (byte)254, 1, 1, 0, 2, 0, 3, 0, 4},
                comp(ar2));
    }*/}

    @Test
    public void decompress() throws Exception {
        assertArrayEquals("ab".getBytes(), decomp(new byte[]{0,'a',0,'b'}));
        assertArrayEquals("abba".getBytes(), decomp(new byte[]{0,'a',0,'b',2,'a'}));
        assertArrayEquals("abc".getBytes(), decomp(new byte[]{0,'a',0,'b',0,'c'}));
        assertArrayEquals("aba".getBytes(), decomp(new byte[]{0,'a',0,'b',0,'a'}));
        assertArrayEquals("abaaabbaaabbbbbaaaababbbaaa".getBytes(), decomp(new byte[]{0, 97, 0, 98, 1, 97, 1, 98,
                2, 97, 3, 98, 2, 98, 7, 97, 3, 97, 5, 98, 8, 97, 0, 97}));

        assertArrayEquals("avui fa bon dia".getBytes(), decomp(new byte[]{0, 97, 0, 118, 0,
                117, 0, 105, 0, 32, 0, 102, 1,
                32, 0, 98, 0, 111, 0, 110, 5, 100, 4, 97}));

        assertArrayEquals("Paraules màgiques".getBytes(), decomp(new byte[]{
                0, 80, 0, 97, 0, 114, 2, 117, 0, 108, 0, 101, 0, 115, 0, 32, 0, 109, 0,
                (byte)195, 0, (byte)160, 0, 103, 0, 105, 0, 113, 0, 117, 6, 115
        }));
    }

    @Test
    public void testFile() throws Exception {
        String s1 = Utils.directory + Utils.testFile;
        String s2 = s1 + ".comp.lzw";
        String s3 = s1 + ".decomp.lzw";

        InputStream is = new BufferedInputStream(new FileInputStream(s1));
        OutputStream os = new BufferedOutputStream(new FileOutputStream(s2));
        LZW.compress(is, os);
        is.close();
        os.close();

        is = new BufferedInputStream(new FileInputStream(s2));
        os = new BufferedOutputStream(new FileOutputStream(s3));
        LZW.decompress(is,os);
        is.close();
        os.close();

        assertEquals("f6fff1b8c86f98ec29c34e0228fe2cd1", Utils.md5(new File(s2)));
        assertEquals("bbcd6a7fc6f98ee378f9d2631dbedfc9", Utils.md5(new File(s3)));
    }

}