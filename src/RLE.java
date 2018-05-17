import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class RLE {

    public static void compress(InputStream is, OutputStream os) throws Exception {
/***********************************************Declaracion de variables***********************************************/
        int byteRead;
        int pos = 0;
        int repeated = 0;
        boolean firstRepeat = true;
        List<Byte> byteList = new ArrayList<>();
/**********************************************Lectura del InputStream (1)*********************************************/
        while ((byteRead = is.read()) != -1) {
/********************************************Si la lista no esta vacia (1.1)*******************************************/
            if (!byteList.isEmpty()) {
/***************************************Si es el segundo byte que se repite (1.1.1)************************************/
                if (byteList.get(pos - 1) != (byte) byteRead && !firstRepeat && repeated == 0) {
                    byteList.add((byte) 0);
                    pos++;
                    byteList.add((byte) byteRead);
                    pos++;
                    os.write(0);
                    os.write(byteRead);
                    firstRepeat = true;
                }
/***************************************Si es el tercer byte que se repite (1.1.2)*************************************/
                else if (byteList.get(pos - 1) == (byte) byteRead && firstRepeat) {
                    byteList.add((byte) byteRead);
                    pos++;
                    os.write(byteRead);
                    firstRepeat = false;
                }
/********************************************Contador de repeticiones (1.1.3)******************************************/
                else if (byteList.get(pos - 1) == (byte) byteRead && !firstRepeat && repeated <= 254) {
                    repeated++;
                }
/*****************************Si es un byte diferente y repeated es no es 0 o es 255 (1.1.4)***************************/
                else if (byteList.get(pos - 1) != (byte) byteRead && repeated != 0 || repeated == 255) {
                    byteList.add((byte) repeated);
                    os.write(repeated);
                    repeated = 0;
                    pos++;
                    byteList.add((byte) byteRead);
                    os.write(byteRead);
                    pos++;
                    firstRepeat = true;
                }
/********************************************Si es un byte diferente(1.1.5)********************************************/
                else {
                    byteList.add((byte) byteRead);
                    os.write(byteRead);
                    pos++;
                }
            }
/*********************************************Si la lista esta vacia (1.2)*********************************************/
            else {
                byteList.add((byte) byteRead);
                os.write(byteRead);
                pos++;
            }
        }
/******************************Si solo hay dos repeticiones al final del InputStream (2.1)*****************************/
        if (repeated == 0 && byteList.get(pos - 1).equals(byteList.get(pos - 2))) {
            os.write((byte)0);
        }
/******************************Si hay mas de dos repeticiones al final del documento (2.2)*****************************/
        else if (repeated > 0) {
            os.write(repeated);
        }
        os.close();
    }

    public static void decompress(InputStream is, OutputStream os) throws Exception {
/***********************************************Declaracion de variables***********************************************/
        int byteRead;
        int pos = 0;
        boolean firstRepeat = false;
        boolean maxRepeat = false;
        boolean minRepeat = false;
        List<Integer> byteList = new ArrayList<>();
/**********************************************Lectura del InputStream (1)*********************************************/
        while ((byteRead = is.read()) != -1) {
/*********************************************Si la lista esta vacia (1.1)*********************************************/
            if (byteList.isEmpty()) {
                byteList.add(byteRead);
                os.write(byteRead);
                pos++;
            }
/********************************************Si la lista no esta vacia (1.2)*******************************************/
            else {
/**********************Si el byte leido es el numero de repeticiones que tiene un byte(1.2.1)**************************/
                if (firstRepeat && !maxRepeat && !minRepeat && byteList.get(pos - 1).equals(byteList.get(pos - 2))) {
/************************************Si el numero de repeticiones no es 0 (1.2.1.1)************************************/
                    if (byteRead != 0) {
/*******************************Introduce el numero de repeticiones del byte (1.2.1.1.1)*******************************/
                        for (int i = 0; i < byteRead; i++) {
                            byteList.add(byteList.get(pos - 1));
                            os.write(byteList.get(pos - 1));
                            pos++;
                        }
/**************************************Si el byte se repite 255 veces (1.2.1.1.2)**************************************/
                        if (byteRead == 255) {
                            maxRepeat = true;
                        }
/********************Si el byte se repite mas de 2 veces pero menos de 255 veces (1.2.1.1.3)***************************/
                        else {
                            maxRepeat = false;
                            firstRepeat = false;
                        }
/************************************Si el byte se repite solo dos veces (1.2.1.2)*************************************/
                    } else minRepeat = true;
                }
/*************************************Si es el segundo byte que se repite (1.2.2)**************************************/
                else if (byteRead == byteList.get(pos - 1) && !maxRepeat) {
                    byteList.add(byteRead);
                    os.write(byteRead);
                    firstRepeat = true;
                    pos++;
                }
/*************************************Si es un byte diferente al anterior (1.2.3)**************************************/
                else {
                    byteList.add(byteRead);
                    os.write(byteRead);
                    pos++;
                    minRepeat = false;
                    firstRepeat = false;
                    maxRepeat = false;
                }
            }
        }
        os.close();
    }
}