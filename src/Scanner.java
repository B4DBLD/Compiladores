/*
Ávila Juárez Alexis Aramis
Pérez Ortiz Saúl
Spindola Reyes Arturo Israel

 */

import java.math.BigDecimal;
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
        int linea = 1;
        String cadenaLimpia = "";
        char c;

        leerCadena: for(int i=0; i<source.length(); i++){
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

                    } else if (c == '"') {
                        estado = 17;
                        lexema += c;
                    } else if (c =='/') {
                        estado = 19;
                        lexema += c;
                        
                    } else if (c == '+') {
                        estado = 24;
                        lexema += c;

                    } else if (c == '-') {
                        estado = 25;
                        lexema += c;

                    } else if (c == '*') {
                        estado = 26;
                        lexema += c;

                    } else if (c == '{') {
                        estado = 27;
                        lexema += c;
                    } else if (c == '}') {
                        estado = 28;
                        lexema += c;
                    } else if (c == '(') {
                        estado = 29;
                        lexema += c;
                    } else if ( c == ')') {
                        estado = 30;
                        lexema += c;
                    } else if (c == ',') {
                        estado = 31;
                        lexema += c;
                    } else if (c == '.') {
                        estado = 32;
                        lexema += c;
                    } else if (c == ';') {
                        estado = 33;
                        lexema += c;
                    } else if (c == '\n') {
                        linea++;
                    }
                    else {
                        if (c != '\n' && c != '\r' && c != '\t' && c != ' ' ) {
                            Interprete.error(linea, "Error lexico, caracter invalido");
                            estado = 0;
                            lexema = "";
                            break leerCadena;
                        }
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
                        estado = 12;
                        lexema += c;

                        
                    } else if (c == 'E') {
                        estado = 14;
                        lexema += c;
                    }else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema), linea);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 12:
                    if(Character.isDigit(c)){
                        estado = 13;
                        lexema += c;
                    }
                    break;
                case 13:
                    if(Character.isDigit(c)){
                        estado = 13;
                        lexema += c;
                    } else if (c == 'E'){
                        estado = 14;
                        lexema += c;
                    }else {
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema), linea);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 14:
                    if(Character.isDigit(c)){
                        estado = 16;
                        lexema += c;
                    } else if (c == '+' || c == '-'){
                        estado = 15;
                        lexema += c;
                    }
                    break;
                case 15:
                    if(Character.isDigit(c)){
                        estado = 16;
                        lexema += c;
                    }
                    break;
                case 16:
                    if(Character.isDigit(c)){
                        estado = 16;
                        lexema += c;
                    } else {
                        BigDecimal exponente = new BigDecimal(lexema);
                        Token t = new Token(TipoToken.NUMBER, lexema, exponente.toPlainString(), linea);
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 17:
                    if (c == '\n') {
                        Interprete.error(linea, "Error lexico, Se esperaba un caracter valido en su lugar se obtuvo un salto de linea");
                        estado = 0;
                        lexema = "";
                        i--;
                        break leerCadena;
                    } else if (c == '"'){
                        estado = 18;
                        lexema += c;
                    } else {
                        estado = 17;
                        lexema += c;

                    }

                    break;
                case 18:
                    cadenaLimpia = lexema.substring(1, lexema.length()-1);
                    Token s = new Token(TipoToken.STRING, lexema, cadenaLimpia, linea);
                    tokens.add(s);
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                    case 19:
                    if (c == '/') {
                        estado = 20;
                        lexema += c;
                    } else if (c == '*') {
                        estado = 21;
                        lexema += c;

                    }else {
                        TipoToken st = palabrasReservadas.get(lexema);

                        if(st == null){
                            Token stt  = new Token(TipoToken.SLASH, lexema);
                            tokens.add(stt);
                        }
                        else{
                            Token t = new Token(st, lexema);
                            tokens.add(t);
                        }

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                        break;
                case 20:
                    if (c == '\n'){
                        estado = 0;
                        lexema = "";
                        i--;
                    }else {
                        estado = 20;
                        lexema += c;
                    }
                    break;
                case 21:
                    if(c == '*') {
                        estado = 22;
                        lexema += c;
                    }else {
                        estado = 21;
                        lexema += c;
                    }
                    break;
                case 22:
                    if (c == '*') {
                        estado = 22;
                        lexema += c;
                    } else if (c == '/') {
                        estado = 23;
                        lexema += c;
                    } else {
                        estado = 21;
                        lexema += c;
                    }
                    break;
                case 23:
                    //No genera token
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 24:
                    TipoToken pt = palabrasReservadas.get(lexema);
                    if(pt == null){
                        Token ptt  = new Token(TipoToken.PLUS, lexema);
                        tokens.add(ptt);
                    }
                    else{
                        Token t = new Token(pt, lexema);
                        tokens.add(t);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 25:
                    TipoToken mt = palabrasReservadas.get(lexema);
                    if(mt == null){
                        Token mtt  = new Token(TipoToken.MINUS, lexema);
                        tokens.add(mtt);
                    }
                    else{
                        Token t = new Token(mt, lexema);
                        tokens.add(t);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 26:
                    TipoToken ast = palabrasReservadas.get(lexema);
                    if(ast == null){
                        Token astt  = new Token(TipoToken.STAR, lexema);
                        tokens.add(astt);
                    }
                    else{
                        Token t = new Token(ast, lexema);
                        tokens.add(t);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 27:
                    TipoToken lb = palabrasReservadas.get(lexema);
                    if(lb == null){
                        Token lbt  = new Token(TipoToken.LEFT_BRACE, lexema);
                        tokens.add(lbt);
                    }
                    else{
                        Token t = new Token(lb, lexema);
                        tokens.add(t);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 28:
                    TipoToken rb = palabrasReservadas.get(lexema);
                    if(rb == null){
                        Token rbt  = new Token(TipoToken.RIGHT_BRACE, lexema);
                        tokens.add(rbt);
                    }
                    else{
                        Token t = new Token(rb, lexema);
                        tokens.add(t);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 29:
                    TipoToken lp = palabrasReservadas.get(lexema);
                    if(lp == null){
                        Token lpt  = new Token(TipoToken.LEFT_PAREN, lexema);
                        tokens.add(lpt);
                    }
                    else{
                        Token t = new Token(lp, lexema);
                        tokens.add(t);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 30:
                    TipoToken rp = palabrasReservadas.get(lexema);
                    if(rp == null){
                        Token rpt  = new Token(TipoToken.RIGHT_PAREN, lexema);
                        tokens.add(rpt);
                    }
                    else{
                        Token t = new Token(rp, lexema);
                        tokens.add(t);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 31:
                    TipoToken cm = palabrasReservadas.get(lexema);
                    if(cm == null){
                        Token cmt  = new Token(TipoToken.COMMA, lexema);
                        tokens.add(cmt);
                    }
                    else{
                        Token t = new Token(cm, lexema);
                        tokens.add(t);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 32:
                    TipoToken dt = palabrasReservadas.get(lexema);
                    if(dt == null){
                        Token dtt  = new Token(TipoToken.DOT, lexema);
                        tokens.add(dtt);
                    }
                    else{
                        Token t = new Token(dt, lexema);
                        tokens.add(t);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                    break;
                case 33:
                    TipoToken sct = palabrasReservadas.get(lexema);
                    if(sct == null){
                        Token sctt  = new Token(TipoToken.SEMICOLON, lexema);
                        tokens.add(sctt);
                    }
                    else{
                        Token t = new Token(sct, lexema);
                        tokens.add(t);
                    }estado = 0;
                    lexema = "";
                    i--;

                    break;




            }
        }
        Token t = new Token(TipoToken.EOF, "");
        tokens.add(t);


        return tokens;
    }
}
