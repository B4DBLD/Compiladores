public class SolRel {
    private final Nodo nodo;

    public SolRel(Nodo nodo) {

        this.nodo = nodo;

    }


    public Object solve() {
        return solve(nodo);
    }

    private Object solve(Nodo n) {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        Object resultadoIzquierdo;
        Object resultadoDerecho;

        if (n.getHijos() == null) {
            if (n.getValue().tipo == TipoToken.NUMBER || n.getValue().tipo == TipoToken.STRING || n.getValue().tipo == TipoToken.TRUE || n.getValue().tipo == TipoToken.FALSE) {
                return n.getValue().literal;
            } else if (n.getValue().tipo == TipoToken.IDENTIFIER) {
                return tablaSimbolos.obtener(n.getValue().lexema);
            }
        }

            Nodo izq = n.getHijos().get(0);
            Nodo der = n.getHijos().get(1);

            if (izq.getValue().isOperator() && der.getValue().isOperator()) {
                SolArit isol = new SolArit(izq);
                resultadoIzquierdo = isol.solve();
                SolArit dsol = new SolArit(der);
                resultadoDerecho = dsol.solve();
                
            } else if (izq.getValue().isOperator()) {
                SolArit isol = new SolArit(izq);
                resultadoIzquierdo = isol.solve();
                resultadoDerecho = solve(der);

            } else if (der.getValue().isOperator()) {
                resultadoIzquierdo = solve(izq);
                SolArit dsol = new SolArit(der);
                resultadoDerecho = dsol.solve();

            } else{
                resultadoIzquierdo = solve(izq);
                resultadoDerecho = solve(der);

            }

            if (resultadoIzquierdo.getClass() == resultadoDerecho.getClass() && resultadoDerecho.getClass() == Float.class) {
                switch (n.getValue().tipo) {
                    case LESS:
                        return ((Float) resultadoIzquierdo < (Float) resultadoDerecho);
                    case GREATER:
                        return ((Float) resultadoIzquierdo > (Float) resultadoDerecho);
                    case LESS_EQUAL:
                        return ((Float) resultadoIzquierdo <= (Float) resultadoDerecho);
                    case GREATER_EQUAL:
                        return ((Float) resultadoIzquierdo >= (Float) resultadoDerecho);
                    case EQUAL_EQUAL:
                        return ( resultadoIzquierdo ==  resultadoDerecho);
                    case BANG_EQUAL:
                        return ( resultadoIzquierdo !=  resultadoDerecho);
                }
            } else if (resultadoIzquierdo.getClass() == resultadoDerecho.getClass() && resultadoDerecho.getClass() == Integer.class) {
                switch (n.getValue().tipo) {
                    case LESS:
                        return ((Integer) resultadoIzquierdo < (Integer) resultadoDerecho);
                    case GREATER:
                        return ((Integer) resultadoIzquierdo > (Integer) resultadoDerecho);
                    case LESS_EQUAL:
                        return ((Integer) resultadoIzquierdo <= (Integer) resultadoDerecho);
                    case GREATER_EQUAL:
                        return ((Integer) resultadoIzquierdo >= (Integer) resultadoDerecho);
                    case EQUAL_EQUAL:
                        return ( resultadoIzquierdo ==  resultadoDerecho);
                    case BANG_EQUAL:
                        return ( resultadoIzquierdo !=  resultadoDerecho);
                }

            } else if (resultadoIzquierdo.getClass() == resultadoDerecho.getClass() && resultadoDerecho.getClass() == String.class) {
                switch (n.getValue().tipo) {
                    case LESS:
                        return ((String) resultadoIzquierdo).compareTo((String) resultadoDerecho) < 0;
                    case GREATER:
                        return ((String) resultadoIzquierdo).compareTo((String) resultadoDerecho) > 0;
                    case LESS_EQUAL:
                        return ((String) resultadoIzquierdo).compareTo((String) resultadoDerecho) <= 0;
                    case GREATER_EQUAL:
                        return ((String) resultadoIzquierdo).compareTo((String) resultadoDerecho) >= 0;
                    case EQUAL_EQUAL:
                        return ((String) resultadoIzquierdo).compareTo((String) resultadoDerecho) == 0;
                    case BANG_EQUAL:
                        return ((String) resultadoIzquierdo).compareTo((String) resultadoDerecho) != 0;
                }
            } else if (resultadoIzquierdo.getClass() == resultadoDerecho.getClass() && resultadoDerecho.getClass() == Boolean.class) {
                switch (n.getValue().tipo) {
                    case EQUAL_EQUAL:
                        return resultadoIzquierdo == resultadoDerecho;
                    case BANG_EQUAL:
                        return resultadoIzquierdo != resultadoDerecho;
                }
            } else if (resultadoIzquierdo.getClass() == resultadoDerecho.getClass()) {
                if (n.getValue().tipo == TipoToken.EQUAL_EQUAL) {
                }else {
                    Interprete.error(n.getValue().numeroLinea, "Operación no válida");
                }
            }else {
                Interprete.error(n.getValue().numeroLinea, "Operación no válida");
            }

        return null;
    }
}
