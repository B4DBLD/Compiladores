/*
Ávila Juárez Alexis Aramis
Pérez Ortiz Saúl
Spindola Reyes Arturo Israel

 */

public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;

    final int numeroLinea;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
        this.numeroLinea = 0;
    }

    public Token(TipoToken tipo, String lexema, Object literal, int numeroLinea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.numeroLinea = numeroLinea;
    }

    public String toString() {
        return "<" + tipo + " " + lexema + " " + literal + ">";
    }

    public boolean Keyword() {
        switch (tipo) {
            case VAR:
            case RETURN:
            case PRINT:
            case IF:
            case ELSE:
            case WHILE:
            case FOR:
                return true;
            default:
                return false;
        }
    }

    public boolean isControlStructure() {
        switch (tipo) {
            case IF:
            case WHILE:
            case FOR:
            case ELSE:
                return true;
            default:
                return false;
        }
    }

    public boolean isOperand() {
        switch (this.tipo) {
            case IDENTIFIER:
            case NUMBER:
            case STRING:
            case TRUE:
            case FALSE:
                return true;
            default:
                return false;
        }
    }

    public boolean  isOperator() {
        switch (this.tipo) {
            case PLUS:
            case MINUS:
            case STAR:
            case SLASH:
            case EQUAL_EQUAL:
            case BANG_EQUAL:
            case GREATER:
            case GREATER_EQUAL:
            case LESS:
            case LESS_EQUAL:
            case AND:
            case OR:
            case EQUAL:
                return true;
            default:
                return false;
        }
    }

    private int getPrecedence() {
        switch (this.tipo) {
            case STAR:
            case SLASH:
                return 7;
            case PLUS:
            case MINUS:
                return 6;
            case LESS:
            case LESS_EQUAL:
            case GREATER:
            case GREATER_EQUAL:
                return 5;
            case EQUAL_EQUAL:
            case BANG_EQUAL:
                return 4;
            case AND:
            case OR:
                return 3;
            case EQUAL:
                return 1;
        }

        return 0;
    }

    public int aridity() {
        switch (this.tipo) {
            case STAR:
            case SLASH:
            case PLUS:
            case MINUS:
            case EQUAL:
            case EQUAL_EQUAL:
            case BANG_EQUAL:
            case GREATER:
            case GREATER_EQUAL:
            case LESS:
            case LESS_EQUAL:
            case AND:
            case OR:
                return 2;
            default:
                break;
        }
        return 0;
    }

    public boolean greaterEqualPrecedence(Token t) {
        return this.getPrecedence() >= t.getPrecedence();
    }

}
