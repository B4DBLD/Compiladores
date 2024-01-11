public class SolLog {
    private final Nodo nodo;

    public SolLog(Nodo nodo) {
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
            if (n.getValue().tipo == TipoToken.TRUE || n.getValue().tipo == TipoToken.FALSE) {
                return n.getValue().literal;
            } else if (n.getValue().tipo == TipoToken.IDENTIFIER) {
                return tablaSimbolos.obtener(n.getValue().lexema);
            }
        }

            Nodo izq = n.getHijos().get(0);
            Nodo der = n.getHijos().get(1);

            if (izq.getValue().isOperator() && der.getValue().isOperator()) {
                SolRel isol = new SolRel(izq);
                resultadoIzquierdo = isol.solve();
                SolRel dsol = new SolRel(der);
                resultadoDerecho = dsol.solve();

            } else if (izq.getValue().isOperator()) {
                SolRel isol = new SolRel(izq);
                resultadoIzquierdo = isol.solve();
                resultadoDerecho = solve(der);

            } else if (der.getValue().isOperator()) {
                resultadoIzquierdo = solve(izq);
                SolRel dsol = new SolRel(der);
                resultadoDerecho = dsol.solve();

            } else {
                resultadoIzquierdo = solve(izq);
                resultadoDerecho = solve(der);

            }

            if (resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean) {
                switch (n.getValue().tipo) {
                    case AND:
                        return ((Boolean) resultadoIzquierdo && (Boolean) resultadoDerecho);
                    case OR:
                        return ((Boolean) resultadoIzquierdo || (Boolean) resultadoDerecho);
                }
            }else{
                Interprete.error(n.getValue().numeroLinea, "Operación no válida");
            }

        return null;
    }

}
