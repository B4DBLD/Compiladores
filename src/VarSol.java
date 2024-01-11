public class VarSol {
    private final Nodo nodo;

    public VarSol(Nodo nodo) {
        this.nodo = nodo;
    }

    public void solve() {
        solve(nodo);
    }

    private void solve(Nodo n) {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        if (nodo.getHijos().size() == 2) {
            String key = "";
            Object value = 0;
            for (Nodo i : nodo.getHijos()) {
                if (i.getValue().tipo == TipoToken.IDENTIFIER) {
                    key = i.getValue().lexema;
                }
                if (tablaSimbolos.existeIdentificador(key)) {
                    throw new RuntimeException("Error: La variable " + key + " ya estaba definida");
                } else if (i.getValue().tipo == TipoToken.NUMBER || i.getValue().tipo == TipoToken.STRING || i.getValue().tipo == TipoToken.TRUE || i.getValue().tipo == TipoToken.FALSE) {
                    value = i.getValue().literal;
                }
            }
            tablaSimbolos.asignarValor(key, value);
        } else if (nodo.getHijos().size() == 1) {
            if (nodo.getHijos().get(0).getValue().tipo == TipoToken.IDENTIFIER) {
                String key = nodo.getHijos().get(0).getValue().lexema;
                if (tablaSimbolos.obtener(key) != null) {
                    throw new RuntimeException("Error: La variable " + key + " ya estaba definida");
                }
                tablaSimbolos.asignarValor(key, null);
            }
        } else {
            throw new RuntimeException("Error al declarar la variable");
        }
    }
}
