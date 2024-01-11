import java.util.List;

public class ParserC {
    private final List<Token> tokens;
    private int i = 0;
    private Token preanalisis;
    public ParserC(List<Token> tokens) {this.tokens = tokens;}

    // ...
    // ...
    // ...

    public void parse() {
        i = 0;
        preanalisis = tokens.get(i);
        PROGRAM();
        if ( !(Interprete.existenErrores) && !(preanalisis.tipo == TipoToken.EOF)) {
            Interprete.error(preanalisis.numeroLinea, "Error  No se esperaba el token (PARSE): " + preanalisis.tipo);
        } else if (!(Interprete.existenErrores) && (preanalisis.tipo == TipoToken.EOF)) {
        }
    }

    private void PROGRAM(){
        DECL();
    }

    private void DECL(){
        if (preanalisis.tipo == TipoToken.FUN) {
            FUN_DECL();
            DECL();
        } else if (preanalisis.tipo == TipoToken.VAR) {
            VAR_DECL();
            DECL();
        } else if (preanalisis.tipo == TipoToken.LESS ||
                   preanalisis.tipo == TipoToken.TRUE ||
                   preanalisis.tipo == TipoToken.FALSE ||
                   preanalisis.tipo == TipoToken.NULL ||
                   preanalisis.tipo == TipoToken.NUMBER ||
                   preanalisis.tipo == TipoToken.STRING ||
                   preanalisis.tipo == TipoToken.IDENTIFIER ||
                   preanalisis.tipo == TipoToken.LEFT_PAREN ||
                   preanalisis.tipo == TipoToken.FOR ||
                   preanalisis.tipo == TipoToken.IF ||
                   preanalisis.tipo == TipoToken.PRINT ||
                   preanalisis.tipo == TipoToken.RETURN ||
                   preanalisis.tipo == TipoToken.WHILE ||
                   preanalisis.tipo == TipoToken.LEFT_BRACE){
            STMT();
            DECL();


        } else {
        }
    }

    private void FUN_DECL(){
        if (preanalisis.tipo == TipoToken.FUN) {
            match(TipoToken.FUN);
            FUNCTION();
        } else {
            Interprete.error(preanalisis.numeroLinea, "Error  No se esperaba el token (FUN_DECL): " + preanalisis.lexema);
        }
    }

    private void VAR_DECL(){
        if (preanalisis.tipo == TipoToken.VAR) {
            match(TipoToken.VAR);
            match(TipoToken.IDENTIFIER);
            VAR_INIT();
            match(TipoToken.SEMICOLON);
        } else {
            Interprete.error(preanalisis.numeroLinea, "Error  No se esperaba el token (VAR_DECL): " + preanalisis.lexema);
        }
    }

    private void VAR_INIT(){
        if (preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            EXPRESSION();
        }
    }

    private void STMT(){
         if (preanalisis.tipo == TipoToken.FOR) {
            FOR_STMT();
        } else if (preanalisis.tipo == TipoToken.IF) {
            IF_STMT();
        } else if (preanalisis.tipo == TipoToken.PRINT) {
            PRINT_STMT();
        } else if (preanalisis.tipo == TipoToken.RETURN) {
            RETURN_STMT();
        } else if (preanalisis.tipo == TipoToken.WHILE) {
            WHILE_STMT();
        } else if (preanalisis.tipo == TipoToken.LEFT_BRACE) {
            BLOCK();
        } else {
            EXPR_STMT();
        }
    }

    private void EXPR_STMT(){
        EXPRESSION();
        match(TipoToken.SEMICOLON);
    }

    private void FOR_STMT(){
        if (preanalisis.tipo == TipoToken.FOR) {
            match(TipoToken.FOR);
            match(TipoToken.LEFT_PAREN);
            FOR_STMT_1();
            FOR_STMT_2();
            FOR_STMT_3();
            match(TipoToken.RIGHT_PAREN);
            STMT();
        } else {
            Interprete.error(preanalisis.numeroLinea, "Error  No se esperaba el token (FOR_STMT): " + preanalisis.lexema);
        }
    }

    private void FOR_STMT_1(){
        if (preanalisis.tipo == TipoToken.VAR) {
            VAR_DECL();
        } else if (preanalisis.tipo == TipoToken.SEMICOLON) {
            match(TipoToken.SEMICOLON);

        } else {
            EXPR_STMT();
        }
    }

    private void FOR_STMT_2(){
        if (preanalisis.tipo == TipoToken.SEMICOLON) {
            match(TipoToken.SEMICOLON);
        } else {
            EXPRESSION();
            match(TipoToken.SEMICOLON);
        }
    }

    private void FOR_STMT_3(){
        EXPRESSION();
    }

    private void IF_STMT(){
        if (preanalisis.tipo == TipoToken.IF) {
            match(TipoToken.IF);
            match(TipoToken.LEFT_PAREN);
            EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
            STMT();
            ELSE_STMT();
        } else {
            Interprete.error(preanalisis.numeroLinea, "Error  No se esperaba el token (IF_STMT): " + preanalisis.lexema);
        }
    }

    private void ELSE_STMT(){
        if (preanalisis.tipo == TipoToken.ELSE) {
            match(TipoToken.ELSE);
            STMT();
        }
    }

    private void PRINT_STMT(){
        if (preanalisis.tipo == TipoToken.PRINT) {
            match(TipoToken.PRINT);
            EXPRESSION();
            match(TipoToken.SEMICOLON);
        } else {
            Interprete.error(preanalisis.numeroLinea, "Error  No se esperaba el token (PRINT_STMT): " + preanalisis.lexema);
        }
    }

    private void RETURN_STMT(){
        if (preanalisis.tipo == TipoToken.RETURN) {
            match(TipoToken.RETURN);
            RETURN_EXP_OPC();
            match(TipoToken.SEMICOLON);
        } else {
            Interprete.error(preanalisis.numeroLinea, "Error  No se esperaba el token (RETURN_STMT): " + preanalisis.lexema);
        }

    }

    private void RETURN_EXP_OPC(){
        EXPRESSION();
    }

    private void WHILE_STMT(){
        if (preanalisis.tipo == TipoToken.WHILE) {
            match(TipoToken.WHILE);
            match(TipoToken.LEFT_PAREN);
            EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
            STMT();
        } else {
            Interprete.error(preanalisis.numeroLinea, "Error  No se esperaba el token (WHILE_STMT): " + preanalisis.lexema);
        }

    }

    private void BLOCK(){
        match(TipoToken.LEFT_BRACE);
        DECL();
        match(TipoToken.RIGHT_BRACE);
    }

    private void EXPRESSION(){
        ASSIG();
    }

    private void ASSIG(){
        LOGIC_OR();
        ASSIG_OPC();
    }

    private void ASSIG_OPC(){
        if (preanalisis.tipo == TipoToken.EQUAL) {
            match(TipoToken.EQUAL);
            EXPRESSION();
        }
    }

    private void LOGIC_OR(){
        LOGIC_AND();
        LOGIC_OR_2();
    }

    private void LOGIC_OR_2(){
        if (preanalisis.tipo == TipoToken.OR) {
            match(TipoToken.OR);
            LOGIC_AND();
            LOGIC_OR_2();
        }
    }

    private void LOGIC_AND(){
        EQUALITY();
        LOGIC_AND_2();
    }

    private void LOGIC_AND_2(){
        if (preanalisis.tipo == TipoToken.AND) {
            match(TipoToken.AND);
            EQUALITY();
            LOGIC_AND_2();
        }
    }

    private void EQUALITY(){
        COMPARISION();
        EQUALITY_2();
    }

    private void EQUALITY_2(){
        if (preanalisis.tipo == TipoToken.EQUAL_EQUAL) {
            match(TipoToken.EQUAL_EQUAL);
            COMPARISION();
            EQUALITY_2();
        } else if (preanalisis.tipo == TipoToken.BANG_EQUAL) {
            match(TipoToken.BANG_EQUAL);
            COMPARISION();
            EQUALITY_2();
        }
    }

    private void COMPARISION(){
        TERM();
        COMPARISION_2();
    }

    private void COMPARISION_2(){
        if (preanalisis.tipo == TipoToken.GREATER) {
            match(TipoToken.GREATER);
            TERM();
            COMPARISION_2();
        } else if (preanalisis.tipo == TipoToken.GREATER_EQUAL) {
            match(TipoToken.GREATER_EQUAL);
            TERM();
            COMPARISION_2();
        } else if (preanalisis.tipo == TipoToken.LESS) {
            match(TipoToken.LESS);
            TERM();
            COMPARISION_2();
        } else if (preanalisis.tipo == TipoToken.LESS_EQUAL) {
            match(TipoToken.LESS_EQUAL);
            TERM();
            COMPARISION_2();
        }
    }

    private void TERM(){
        FACTOR();
        TERM_2();
    }

    private void TERM_2(){
        if (preanalisis.tipo == TipoToken.PLUS) {
            match(TipoToken.PLUS);
            FACTOR();
            TERM_2();
        } else if (preanalisis.tipo == TipoToken.MINUS) {
            match(TipoToken.MINUS);
            FACTOR();
            TERM_2();
        }
    }

    private void FACTOR(){
        UNARY();
        FACTOR_2();
    }

    private void FACTOR_2(){
        if(preanalisis.tipo == TipoToken.SLASH){
            match(TipoToken.SLASH);
            UNARY();
            FACTOR_2();
        } else if (preanalisis.tipo == TipoToken.STAR) {
            match(TipoToken.STAR);
            UNARY();
            FACTOR_2();
        }
    }

    private void UNARY(){
        if(preanalisis.tipo == TipoToken.BANG){
            match(TipoToken.BANG);
            UNARY();
        } else if (preanalisis.tipo == TipoToken.MINUS) {
            match(TipoToken.MINUS);
            UNARY();
        }
        else{
            CALL();
        }
    }

    private void CALL(){
        PRIMARY();
        CALL_2();
    }

    private void CALL_2(){
        if (preanalisis.tipo == TipoToken.LEFT_PAREN) {
            match(TipoToken.LEFT_PAREN);
            ARGS_OPC();
            match(TipoToken.RIGHT_PAREN);
            CALL_2();
        }
    }

    private void PRIMARY(){
        if (preanalisis.tipo == TipoToken.TRUE) {
            match(TipoToken.TRUE);
        } else if (preanalisis.tipo == TipoToken.FALSE) {
            match(TipoToken.FALSE);
        } else if (preanalisis.tipo == TipoToken.NULL) {
            match(TipoToken.NULL);
        } else if (preanalisis.tipo == TipoToken.NUMBER) {
            match(TipoToken.NUMBER);
        } else if (preanalisis.tipo == TipoToken.STRING) {
            match(TipoToken.STRING);
        } else if (preanalisis.tipo == TipoToken.IDENTIFIER) {
            match(TipoToken.IDENTIFIER);
        } else if (preanalisis.tipo == TipoToken.LEFT_PAREN) {
            match(TipoToken.LEFT_PAREN);
            EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
        }
    }

    private void FUNCTION(){
        match(TipoToken.IDENTIFIER);
        match(TipoToken.LEFT_PAREN);
        PARAMS_OPC();
        match(TipoToken.RIGHT_PAREN);
        BLOCK();
    }

    private void FUNCTIONS(){
        FUN_DECL();
        FUNCTIONS();
    }

    private void PARAMS_OPC(){
        PARAMS();
    }

    private void PARAMS(){
        if (preanalisis.tipo == TipoToken.IDENTIFIER) {
            match(TipoToken.IDENTIFIER);
            PARAMS_2();
        }
    }

    private void PARAMS_2(){
        if (preanalisis.tipo == TipoToken.COMMA) {
            match(TipoToken.COMMA);
            match(TipoToken.IDENTIFIER);
            PARAMS_2();
        }
    }

    private void ARGS_OPC(){
        EXPRESSION();
        ARGS();
    }

    private void ARGS(){
        if (preanalisis.tipo == TipoToken.COMMA) {
            match(TipoToken.COMMA);
            EXPRESSION();
            ARGS();
        }
    }

    private void match(TipoToken tt)  {
        if(preanalisis.tipo ==  tt){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            String message = "Error en la línea " +
                    preanalisis.numeroLinea +
                    ". Se esperaba " + preanalisis.tipo +
                    " pero se encontró " + tt;
            Interprete.existenErrores = true;
            Interprete.error(preanalisis.numeroLinea,message);
        }
    }

    private Token previous() {
        return this.tokens.get(i - 1);
    }
}