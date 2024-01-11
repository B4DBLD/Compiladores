import java.util.List;
public class EQEQSol {
    private Nodo nodo;

    public EQEQSol(Nodo nodo) {
        this.nodo = nodo;
    }

    public void solve() {
        solve(nodo);
    }

    private void solve(Nodo n) {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        SolArit arithmeticSolver;
        Arbol arbol;

        List<Nodo> children = n.getHijos();
        Nodo variable = children.get(0);
        Nodo value = children.get(1);

        arithmeticSolver = new SolArit(value);
        Object valueResult = arithmeticSolver.solve();

        tablaSimbolos.asignarValor(variable.getValue().lexema, valueResult);

    }
}
