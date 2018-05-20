import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LZW {

    public static void compress(InputStream is, OutputStream os) throws Exception {
/***********************************************Declaracion de variables***********************************************/
        Token[] arrayToken = new Token[257];
        int byteValue;
/***********************************************Lectura del InputStream************************************************/
        while ((byteValue = is.read()) != -1) {
            Token tok = new Token(byteValue);
/******************************************Obtenencion de posicion en el array*****************************************/
            int arrayPosition = isNull(arrayToken);
/***************************************Comprueba si existe el token y lo guarda***************************************/
            tok = exist(tok, arrayToken, is);
            arrayToken[arrayPosition] = tok;
/***********************Si el array esta lleno escribe sus valores en el OutputStream y reinicia el array**************/
            if (arrayPosition == 256){
                translateCompress(arrayToken, os);
                arrayToken = new Token[arrayToken.length];
            }
        }
/*********************************Escribe los valores del array y cierra el OutputStream*******************************/
        translateCompress(arrayToken, os);
        os.close();
    }


    public static void decompress(InputStream is, OutputStream os) throws Exception {
/***********************************************Declaracion de variables***********************************************/
        Token[] arrayToken = new Token[257];
        int byteValue;
        int position = 0;
        int posRead = 0;
        int arrayPosition = 1;
/***********************************************Lectura del InputStream************************************************/
        while ((byteValue = is.read()) != -1) {
/***********************************Si el byte leido hace referencia a una posicion************************************/
            if (posRead % 2 == 0) {
                position = byteValue;
                posRead++;
            }
/*************************************Si el byte leido hace referencia a un byte***************************************/
            else {
                Token tok = new Token(byteValue);
                tok.setPosition(position);
                arrayToken[arrayPosition] = tok;
                arrayPosition++;
                posRead++;
            }
/*************************************Si el array esta lleno escribe el contenido**************************************/
            if (arrayPosition==257){
                translateDecompress(arrayToken, os);
                arrayToken = new Token[arrayToken.length];
                arrayPosition = 1;

            }
        }
/********************************Escribe el contenido del array y cierra el OutputStream*******************************/
        translateDecompress(arrayToken, os);
        os.close();
    }

    private static Token exist(Token token, Token[] arrayToken, InputStream is) throws IOException {
/****************************************Recorre el array para comparar tokens*****************************************/
        for (int i = 1; i < arrayToken.length; i++) {
/******************************Si encuentra un null es que no existe y devuelve el token*******************************/
            if (arrayToken[i] == null) {
                return token;
            }
/************************************************Comparacion de tokens*************************************************/
            else {
                if (token.byteValue == arrayToken[i].byteValue && token.position == arrayToken[i].position) {
                    int byteValue;
/**********************Lectura del siguiente, si llega al final del InputStream devuelve el token**********************/
                    if ((byteValue = is.read()) == -1) {
                        return token;
                    }
/************************************Modifica el token actual y vuelve a comparar**************************************/
                    else {
                        token.setPosition(i);
                        token.byteValue = byteValue;
                        exist(token, arrayToken, is);
                    }
                }
            }
        }
        return null;
    }

/***********************************Obtencion de la posicion donde insertar el token***********************************/
    private static int isNull(Token[] arrayToken) {
        int arrayPosition = 1;
        for (int i = 1; i < arrayToken.length; i++) {
            if (arrayToken[i] != null) {
                arrayPosition++;
            } else break;
        }
        return arrayPosition;
    }

/*********************************Escritura del OutputStream para la funcion compress**********************************/
    private static void translateCompress(Token[] arrayToken, OutputStream os) throws IOException {
        for (Token token : arrayToken) {
            if (token != null) {
                os.write(token.position);
                os.write((byte) token.byteValue);
            }
        }
    }

/********************************Escritura del OutputStream para la funcion decompress*********************************/
    private static void translateDecompress(Token[] tokenArray, OutputStream os) throws IOException {
        List<Integer> auxList = new ArrayList<>();
        for (Token token : tokenArray) {
            if (token != null) {
/***************************Si el token tiene posicion cero, escribe el valor de byteValue*****************************/
                if (token.position == 0) {
                    os.write(token.byteValue);
                }
/****************************************Si el token hace referencia a un byte*****************************************/
                else {
                    int aux = token.position;
/************************************Vacias auxList y guardas el valor de byteValue************************************/
                    auxList.clear();
                    auxList.add(token.byteValue);
                    /*Mientras aux no sea cero, añades el valor de byteValue del token que esta en la posicion aux del array en la lista y*/
/********************modificas aux con la posicion del token que esta en la posicion aux del array*********************/
                    while (aux != 0) {
                        auxList.add(tokenArray[aux].byteValue);
                        aux = tokenArray[aux].position;
                    }
/************************************Invierte la lista y escribe en el OutputStream************************************/
                    Collections.reverse(auxList);
                    for (Integer anAuxList : auxList) {
                        os.write(anAuxList);
                    }
                }
            }
        }
    }
}