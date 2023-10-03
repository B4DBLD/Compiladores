import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    public Scanner(String source){
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        String lexema = "";
        int estado = 0;
        char c;

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);

            switch (estado){
                case 0:
                    if(c == '>'){
                        estado = 1;
                        lexema += c;
                    } else if (c == '<') {
                        estado = 4;
                        lexema += c;

                    } else if (c == '=') {
                        estado = 6;
                        lexema += c;
                    } else if (c ==  '!') {
                        estado = 8;
                        lexema += c;
                    } else if (Character.isLetter(c)) {
                        estado = 10;
                        lexema += c;
                    } else if (Character.isDigit(c)) {
                        estado = 11;
                        lexema += c;

                    }
                    break;
                case 1:
                    if(c == '='){
                        estado = 2;
                        lexema += c;
                    } else {
                        TipoToken gt = palabrasReservadas.get(lexema);

                        if (gt == null){
                            Token gtt = new Token(TipoToken.GREATER, lexema);
                            tokens.add(gtt);
                        }
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 2:
                    TipoToken ge = palabrasReservadas.get(lexema);

                    if (ge == null){
                        Token get = new Token(TipoToken.GREATER_EQUAL,lexema);
                        tokens.add(get);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 4:
                    if(c == '='){
                        estado = 5;
                        lexema += c;
                    } else {
                        TipoToken lt = palabrasReservadas.get(lexema);

                        if (lt == null){
                            Token ltt = new Token(TipoToken.LESS, lexema);
                            tokens.add(ltt);
                        }
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 5:
                    TipoToken le = palabrasReservadas.get(lexema);

                    if (le == null){
                        Token let = new Token(TipoToken.LESS_EQUAL, lexema);
                        tokens.add(let);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 6:
                    if(c == '='){
                        estado = 7;
                        lexema += c;
                    }else{
                        TipoToken et = palabrasReservadas.get(lexema);

                        if (et == null){
                            Token ett = new Token(TipoToken.EQUAL, lexema);
                            tokens.add(ett);
                        }
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 7:
                    TipoToken ee = palabrasReservadas.get(lexema);

                    if (ee == null){
                        Token eet = new Token(TipoToken.EQUAL_EQUAL, lexema);
                        tokens.add(eet);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 8:
                    if(c == '='){
                        estado = 9;
                        lexema += c;
                    }else{
                        TipoToken bt = palabrasReservadas.get(lexema);

                        if (bt == null){
                            Token btt = new Token(TipoToken.BANG, lexema);
                            tokens.add(btt);
                        }
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 9:
                    TipoToken bet = palabrasReservadas.get(lexema);

                    if (bet == null){
                        Token bett = new Token(TipoToken.BANG_EQUAL, lexema);
                        tokens.add(bett);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 10:
                    if(Character.isLetter(c) || Character.isDigit(c)){
                        estado = 10;
                        lexema += c;
                    } else {
                        // Vamos a crear el Token de identificador o palabra reservada
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if(tt == null){
                            Token t = new Token(TipoToken.IDENTIFIER, lexema);
                            tokens.add(t);
                        }
                        else{
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                        }

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 11:
                    if(Character.isDigit(c)){
                        estado = 11;
                        lexema += c;
                    } else if (c == '.') {
                        
                    } else if (c == 'E') {
                        
                    }else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                    }
                    break;

            }
        }


        return tokens;
    }
}
