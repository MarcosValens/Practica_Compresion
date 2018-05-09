import java.io.InputStream;
import java.io.OutputStream;

public class LZW {

    public static void compress(InputStream is, OutputStream os) throws Exception {
        token[] arrayToken = new token[256];
        byte byteValue;
        byte nextChar;
        byte position = 1;
        StringBuilder value = new StringBuilder();
        int arrayPosition = 0;
        boolean first = true;
        while ((byteValue = (byte) is.read()) != -1) {
            token tok = new token(position, byteValue, value, first);

            //comprobamos si el caracter ya esta en el diccionario
            for (int i = 0; i <= arrayPosition-1; i++) {
                //si no esta lo insertamos como el primer caracter que encuentra de ese tipo
                if (byteValue != arrayToken[i].byteValue && i == arrayPosition-1){
                    position++;
                    tok.first = true;
                    tok.value.append((char)byteValue);
                    value.setLength(0);
                    arrayToken[arrayPosition] = tok;
                    arrayPosition++;
                }
                //si el caracter ya esta en el diccionario cogemos la posicion en la que se encuentra y le añadimos el siguiente caracter
                else if(byteValue == arrayToken[i].byteValue && position > 2){
                    position++;
                    tok.byteValue = (byte)is.read();
                    tok.first = false;
                    tok.position = arrayToken[i].position;
                    arrayToken[arrayPosition] = tok;
                }
            }
//introducimos el primer caracter del diccionario
            if (first) {
                tok.value.append((char)byteValue);
                arrayToken[arrayPosition] = tok;
                position++;
                arrayPosition++;
                value.setLength(0);
                first = false;
            }

            /*else{

            } else if ((nextChar = (byte) is.read()) == inputChar) {

            }*/
        }
        for (int i = 0; i < arrayPosition; i++) {
            if (arrayToken[i].first){
                os.write((byte)0);
                os.write(arrayToken[i].byteValue);
            } else {
                os.write(arrayToken[i].position);
                os.write(arrayToken[i].byteValue);
            }
        }
        /*byte[] byteArray = inputArray(is);*/
        /*List<Byte> byteList = new ArrayList<>();
        int byteRead;
        int pos = 1;
        for (int i = 0; i < byteArray.length; i++) {
            if (i==0){
                os.write(0);
                os.write(byteArray[0]);
                byteList.add(byteArray[0]);
            }
            for (ListIterator<Byte> j = byteList.listIterator(); j.hasNext();){
                if (byteArray[i] == j.next()){
                    os.write(pos);
                    os.write(byteArray[i]);
                }
            }
        }
        *//*List <Byte> iterableByteList = new ArrayList<>();

        while ((byteRead = is.read()) != -1){
            byteRead = (char)byteRead;
            iterableByteList.add((byte)byteRead);
        }
        for (ListIterator<Byte> i = iterableByteList.listIterator(); i.hasNext();) {
            byte item = i.next();
            if (byteList.isEmpty()){
                byteList.add(item);
                System.out.println(byteList);
                os.write(0);
                os.write(item);
            }
            else {
                for (ListIterator<Byte> j = byteList.listIterator(); j.hasNext();){
                    if (i==j){
                        byteList.add(j.next());
                        os.write();
                    }
                }
            }
        }*//*
         *//*os.write(0);
        os.write(bytes[0]);
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 1; j < bytes.length; j++) {
                if (bytes[i]==bytes[j]){

                }
            }*//*
         */
    }

    public static class token {
        byte position;
        byte byteValue;
        StringBuilder value;
        boolean first;

        public token(byte position, byte byteValue, StringBuilder value, boolean first) {
            this.position = position;
            this.byteValue = byteValue;
            this.value = value;
            this.first = first;
        }
    }

    /*public static byte[] inputArray(InputStream input) throws Exception
    {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1)
        {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }*/
    public static void decompress(InputStream is, OutputStream os) throws Exception {

    }
}
