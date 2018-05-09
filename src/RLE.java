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
        boolean contatorOn = false;
        List<Byte> byteList = new ArrayList<>();
        //lectura del InputStream
        while ((byteRead = is.read()) != -1) {
            //si el array no esta vacio
            if (!byteList.isEmpty()) {
                //si el array no esta vacio y es el segundo numero que se repite
                if (byteList.get(pos - 1) == (byte) byteRead && firstRepeat) {
                    byteList.add((byte) byteRead);
                    os.write(byteRead);
                    pos++;
                    firstRepeat = false;
                    contatorOn = true;
                }
                //si el numero anterior se a repetido 255 veces
                else if (byteList.get(pos - 1) == 255 && contatorOn) {
                    byteList.add((byte) byteRead);
                    os.write(byteRead);
                    pos++;
                    firstRepeat = true;
                }
                //si solo hay dos numeros repetidos
                else if (byteList.get(pos - 1) != (byte) byteRead && !firstRepeat && repeated == 0) {
                    byteList.add((byte) 0);
                    pos++;
                    byteList.add((byte) byteRead);
                    pos++;
                    os.write(0);
                    os.write(byteRead);
                    firstRepeat = true;
                }

                //si el array no esta vacio y es el tercer numero o mas que se repite hasta 255 veces
                else if (byteList.get(pos - 1) == (byte) byteRead && !firstRepeat && repeated <= 254) {
                    repeated++;
                }
                //si el array no esta vacio, el numero no se repite y repeated es mayor que 0
                else if (byteList.get(pos - 1) != (byte) byteRead && repeated > 0 || repeated == 255) {
                    byteList.add((byte) repeated);
                    os.write(repeated);
                    repeated = 0;
                    pos++;
                    byteList.add((byte) byteRead);
                    os.write(byteRead);
                    pos++;
                    firstRepeat = true;
                }
                //si es el tercer numero que se repite
                else if (byteList.get(pos - 1) == (byte) byteRead && repeated > 0) {
                    if (repeated == 255) {
                        byteList.add((byte) 255);
                        os.write(255);
                        repeated = 1;
                    } else {
                        byteList.add((byte) repeated);
                        os.write((byte) repeated);
                        repeated = 0;
                    }
                    pos++;
                }
                //si es un numero no se repite y repeated es 0
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
            if (byteList.size() >= 3 && byteList.get(pos - 3) == -1) {
                repeated++;
            }
            os.write(repeated);
        }
        //si se acaba el documento y hay que poner el numero de repeticiones
        else if (repeated > 0) {
            os.write(repeated);
        }
    }

    public static void decompress(InputStream is, OutputStream os) throws Exception {
        int byteRead;
        int pos = 0;
        int cont = 0;
        boolean firstRepeat = false;
        boolean maxRepeat = false;
        boolean minRepeat = false;
        List<Integer> byteList = new ArrayList<>();
        while ((byteRead = is.read()) != -1) {
            //si la lista no esta vacia
            /*if (!byteList.isEmpty()) {
                //si el numero se repite dos veces y tiene que empezar otro bucle
                if (cont == 2) {
                    maxRepeat = false;
                    cont = 0;
                }
                //si es el primer numero que se repite (justo antes de empezar a contar)
                if (byteList.get(pos - 1) == byteRead && !firstRepeat || minRepeat) {
                    byteList.add(byteRead);
                    os.write(byteRead);
                    pos++;
                    firstRepeat = true;
                    minRepeat = false;
                    if (maxRepeat) {
                        cont++;
                    }
                }
                //si es el tercer numero que se repite
                else if (pos >= 2 && byteList.get(pos - 1).equals(byteList.get(pos - 2)) && firstRepeat && !maxRepeat) {
                    if (byteRead != 0 && !minRepeat) {
                        for (int i = 0; i < byteRead; i++) {
                            byteList.add(byteList.get(pos - 1));
                            os.write(byteList.get(pos - 1));
                            pos++;
                            //si el numero se repite 255 veces
                            if (byteRead == 255) {
                                maxRepeat = true;
                            }
                        }
                        //inserta el numero que viene despues del que se a repetido solo dos veces
                    } else if (minRepeat){
                        byteList.add(byteRead);
                        os.write(byteRead);
                    }
                    //si solo se repite dos veces
                    else {
                        minRepeat = true;
                        firstRepeat = true;
                        continue;
                    }

                    firstRepeat = false;
                }

                else if (pos >= 2 && byteRead != byteList.get(pos - 1) && firstRepeat && !maxRepeat) {
                    if (byteRead == 0){
                        minRepeat = true;
                    }else {
                    byteList.add(byteRead);
                    os.write(byteRead);
                }}
                //si solo hay dos numeros repetidos
                else if (byteRead == 0 && cont == 0) {
                    firstRepeat = false;
                }
                //si los numeros no se repiten
                else {
                    byteList.add(byteRead);
                    os.write(byteRead);
                    pos++;
                    cont++;
                }
            }
            //si la lista esta vacia
            else {
                byteList.add(byteRead);
                os.write(byteRead);
                pos++;
            }*/
        }
    }
}


