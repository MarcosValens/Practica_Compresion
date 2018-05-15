public class Token {

        int position;
        int byteValue;

        Token(int byteValue) {
            this.byteValue = byteValue;
        }

        void setPosition(int position) {
            this.position = position;
        }

}
