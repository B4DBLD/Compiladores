import java.util.Objects;
public class SolArit {
    private final Nodo nodo;

    public SolArit(Nodo nodo) {
        this.nodo = nodo;
    }
    public Object solve() {
        return solve(nodo);
    }


    private Object solve(Nodo n) {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        // No tiene hijos, es un operando
        if (n.getHijos() == null) {
            if (n.getValue().tipo == TipoToken.NUMBER || n.getValue().tipo == TipoToken.STRING) {
                return n.getValue().literal;
            } else if (n.getValue().tipo == TipoToken.IDENTIFIER) {
                return tablaSimbolos.obtener(n.getValue().lexema);
            } else if (n.getValue().tipo == TipoToken.TRUE || n.getValue().tipo == TipoToken.FALSE) {
                return Boolean.parseBoolean(n.getValue().lexema);
            }
        }

        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = solve(izq);
        Object resultadoDerecho = solve(der);

        if(resultadoIzquierdo instanceof Integer && resultadoDerecho instanceof Integer) {
            switch (n.getValue().tipo) {
                case PLUS:
                    return ((Integer) resultadoIzquierdo + (Integer) resultadoDerecho);
                case MINUS:
                    return ((Integer) resultadoIzquierdo - (Integer) resultadoDerecho);
                case STAR:
                    return ((Integer) resultadoIzquierdo * (Integer) resultadoDerecho);
                case SLASH:
                    return ((Integer) resultadoIzquierdo / (Integer) resultadoDerecho);
            }
        }

        // Operaciones con double
        if(resultadoIzquierdo instanceof Float && resultadoDerecho instanceof Float) {
            switch (n.getValue().tipo) {
                case PLUS:
                    return ((Float) resultadoIzquierdo + (Float) resultadoDerecho);
                case MINUS:
                    return ((Float) resultadoIzquierdo - (Float) resultadoDerecho);
                case STAR:
                    return ((Float) resultadoIzquierdo * (Float) resultadoDerecho);
                case SLASH:
                    return ((Float) resultadoIzquierdo / (Float) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String) {
            boolean equals = String.valueOf(resultadoIzquierdo).equals(String.valueOf(resultadoDerecho));
            switch (n.getValue().tipo) {
                case PLUS:
                    return String.valueOf(resultadoIzquierdo).concat(String.valueOf(resultadoDerecho));
            }
        }

        else if (resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean) {
            switch (n.getValue().tipo) {
                case AND:
                    return ((Boolean) resultadoIzquierdo && (Boolean) resultadoDerecho);
                case OR:
                    return ((Boolean) resultadoIzquierdo || (Boolean) resultadoDerecho);
                case EQUAL:
                    return resultadoIzquierdo == resultadoDerecho;
                case BANG_EQUAL:
                    return resultadoIzquierdo != resultadoDerecho;
            }
        }
        else if (resultadoIzquierdo instanceof Double && resultadoDerecho instanceof String ||
                resultadoIzquierdo instanceof String && resultadoDerecho instanceof Double) {
            if (Objects.requireNonNull(n.getValue().tipo) == TipoToken.PLUS) {
                return resultadoIzquierdo + resultadoDerecho.toString();
            }
        }
        else {
            throw new RuntimeException("Type mismatch");
        }

            return null;
    }
}
