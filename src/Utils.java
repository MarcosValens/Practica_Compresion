import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class Utils {
    public static final String directory = "/home/marcos/Documentos/Clase/Programacion/Practica6/src/";
    public static final String testFile = "im.bmp";

    public static  String md5(File f) throws Exception {
        InputStream is = new FileInputStream(f);
        byte[] data = new byte[is.available()];
        is.read(data);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        return toHex(md.digest());
    }

    private static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi).toLowerCase();
    }
}
