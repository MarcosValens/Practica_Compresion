import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class RLE {

    public static void compress(InputStream is, OutputStream os) throws Exception {
        //declaracion de variables
        int byteRead;
        int pos = 0;
        int repeated = 0;
        boolean firstRepeat = true;
        List<Byte> byteList = new ArrayList<>();
        //lectura del InputStream
        while ((byteRead = is.read()) != -1) {
            //si el array no esta vacio
            if (!byteList.isEmpty()) {
                //si es el segundo byte que se repite
                if (byteList.get(pos - 1) != (byte) byteRead && !firstRepeat && repeated == 0) {
                    byteList.add((byte) 0);
                    pos++;
                    byteList.add((byte) byteRead);
                    pos++;
                    os.write(0);
                    os.write(byteRead);
                    firstRepeat = true;
                }
                //si es el tercer byte que se repite (empezara a contar)
                else if (byteList.get(pos - 1) == (byte) byteRead && firstRepeat) {
                    byteList.add((byte) byteRead);
                    pos++;
                    os.write(byteRead);
                    firstRepeat = false;
                }

                //empieza a contar cuantas veces se repite el byte
                else if (byteList.get(pos - 1) == (byte) byteRead && !firstRepeat && repeated <= 254) {
                    repeated++;
                }

                //si el numero no se repite y repeated es mayor que 0 o 255
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
                //si es un byte nuevo
                else {
                    byteList.add((byte) byteRead);
                    os.write(byteRead);
                    pos++;
                }
            }
            //si es el primer numero del array
            else {
                byteList.add((byte) byteRead);
                os.write(byteRead);
                pos++;
            }
        }
        //si se acaba el documento y solo se repite dos veces
        if (repeated == 0 && byteList.get(pos - 1).equals(byteList.get(pos - 2))) {
            os.write((byte)0);
        }
        //si se acaba el documento y hay que poner el numero de repeticiones
        else if (repeated > 0) {
            os.write(repeated);
        }
        os.close();
    }

    public static void decompress(InputStream is, OutputStream os) throws Exception {
        int byteRead;
        int pos = 0;
        boolean firstRepeat = false;
        boolean maxRepeat = false;
        boolean minRepeat = false;
        List<Integer> byteList = new ArrayList<>();
        while ((byteRead = is.read()) != -1) {
            //si la lista no esta vacia
            if (byteList.isEmpty()) {
                byteList.add(byteRead);
                os.write(byteRead);
                pos++;
            }
            //si contiene numeros
            else {
                //si es la cantidad de veces que se repite un numero
                if (firstRepeat && !maxRepeat && !minRepeat && byteList.get(pos - 1).equals(byteList.get(pos - 2))) {
                    if (byteRead != 0) {
                        for (int i = 0; i < byteRead; i++) {
                            byteList.add(byteList.get(pos - 1));
                            os.write(byteList.get(pos - 1));
                            pos++;
                        }
                        //si se repite 255 veces
                        if (byteRead == 255) {
                            maxRepeat = true;
                        } else {
                            maxRepeat = false;
                            firstRepeat = false;
                        }
                        //si se repite solo 2 veces
                    } else minRepeat = true;
                }
                //si es el mismo numero que el anterior
                else if (byteRead == byteList.get(pos - 1) && !maxRepeat) {
                    byteList.add(byteRead);
                    os.write(byteRead);
                    firstRepeat = true;
                    pos++;
                }
                //si es un numero diferente (no contador)
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


