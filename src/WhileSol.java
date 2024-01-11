import java.util.List;

public class WhileSol {
    private final Nodo nodo;

    public WhileSol(Nodo nodo) {
        this.nodo = nodo;
    }

    public void solve() {
        solve(nodo);
    }

    private void solve(Nodo n) {
        Nodo Cond;
        List<Nodo> body;

        Cond = n.getHijos().get(0);
        body = n.getHijos().subList(1, n.getHijos().size());

        while(conditionSolver(Cond)){
            Nodo raiz = new Nodo(new Token(TipoToken.NULL, ""));
            raiz.insertarHijos(body);
            Arbol arbol = new Arbol(raiz);
            arbol.recorrer();

        }

    }

    private boolean conditionSolver(Nodo n) {
        boolean rCond;
        switch (n.getValue().tipo) {
            case GREATER_EQUAL:
            case EQUAL:
            case GREATER:
            case LESS_EQUAL:
            case LESS:
            case BANG_EQUAL:
                SolRel relSolver = new SolRel(n);
                rCond = (boolean) relSolver.solve();
                break;
            case AND:
            case OR:
                SolLog logSolver = new SolLog(n);
                rCond = (boolean) logSolver.solve();
                break;
            case TRUE:
                rCond = true;
                return rCond;
            case FALSE:
                rCond = false;
                return rCond;
            case IDENTIFIER:
                if (TablaSimbolos.getInstance().existeIdentificador(n.getValue().lexema)){
                    if (TablaSimbolos.getInstance().obtener(n.getValue().lexema) instanceof Boolean){
                        rCond = (boolean) TablaSimbolos.getInstance().obtener(n.getValue().lexema);
                        return rCond;
                    }
                    else{
                        throw new RuntimeException("Error: La variable " + n.getValue().lexema + " no es de tipo booleano");
                    }
                }
                else {
                    throw new RuntimeException("Error: La variable " + n.getValue().lexema + " no está definida");
                }
            default:
                throw new RuntimeException("Error: El resultado debe ser un boleano");
        }
        return rCond;
    }

}
