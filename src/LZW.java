import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LZW {

    public static void compress(InputStream is, OutputStream os) throws Exception {
        Token[] arrayToken = new Token[257];
        int byteValue;
        while ((byteValue = is.read()) != -1) {
            Token tok = new Token(byteValue);
            //seleccionamos donde introducir el token
            int arrayPosition = isnull(arrayToken);
            //comprueba si ya existe el token en el diccionario
            exist(tok, arrayToken, is);
            arrayToken[arrayPosition] = tok;
            if (arrayPosition == 256){
                translateCompress(arrayToken, os);
                arrayToken = new Token[arrayToken.length];
            }
        }
        translateCompress(arrayToken, os);
        os.close();
    }


    public static void decompress(InputStream is, OutputStream os) throws Exception {
        Token[] arrayToken = new Token[257];
        int aux;
        int byteValue;
        int position = 0;
        int posRead = 0;
        int arrayPosition = 1;
        while ((aux = is.read()) != -1) {
            if (posRead % 2 == 0) {
                position = aux;
                posRead++;
            } else {
                byteValue = aux;
                Token tok = new Token(byteValue);
                tok.setPosition(position);
                arrayToken[arrayPosition] = tok;
                arrayPosition++;
                posRead++;
            }
            if (arrayPosition==257){
                translateDecompress(arrayToken, os);
                arrayToken = new Token[arrayToken.length];
                arrayPosition = 1;

            }
        }
        translateDecompress(arrayToken, os);
        os.close();
    }

    private static Token exist(Token token, Token[] arrayToken, InputStream is) throws IOException {
        //compara el token pasado con los del array
        for (int i = 1; i < arrayToken.length; i++) {
            //si encuentra un null es que el token no existe
            if (arrayToken[i] == null) {
                return token;
            }
            //si el token existe...
            else {
                assert token != null;
                if (token.byteValue == arrayToken[i].byteValue && token.position == arrayToken[i].position) {
                    int byteValue;
                    //leemos el siguiente byte, si este es -1 devolvemos el token actual
                    if ((byteValue = is.read()) == -1) {
                        return token;
                    }
                    //si no es el final del documento, modificamos el token con los nuevos parametros y volvemos a compararlo
                    else {
                        token.setPosition(i);
                        token.byteValue = byteValue;
                        token = exist(token, arrayToken, is);
                    }
                }
            }
        }
        return null;
    }

    private static int isnull(Token[] arrayToken) {
        int arrayPosition = 1;
        for (int i = 1; i < arrayToken.length; i++) {
            if (arrayToken[i] != null) {
                arrayPosition++;
            } else break;
        }
        return arrayPosition;
    }

    private static void translateCompress(Token[] tokenArray, OutputStream os) throws IOException {
        for (Token token : tokenArray) {
            if (token != null) {
                os.write(token.position);
                os.write((byte) token.byteValue);
            }
        }
    }

    private static void translateDecompress(Token[] tokenArray, OutputStream os) throws IOException {
        List<Integer> auxList = new ArrayList<>();
        for (Token token : tokenArray) {
            if (token != null) {
                if (token.position == 0) {
                    os.write((byte) token.byteValue);
                } else {
                    int aux = token.position;
                    auxList.clear();
                    auxList.add(token.byteValue);
                    while (aux != 0) {
                        auxList.add(tokenArray[aux].byteValue);
                        aux = tokenArray[aux].position;
                    }
                    Collections.reverse(auxList);
                    for (Integer anAuxList : auxList) {
                        os.write(anAuxList);
                    }
                }
            }
        }
    }

    public static class Token {
        int position;
        int byteValue;

        Token(int byteValue) {
            this.byteValue = byteValue;
        }

        void setPosition(int position) {
            this.position = position;
        }
    }
}




