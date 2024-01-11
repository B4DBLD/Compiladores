import java.util.List;
public class ForSol {
    private Nodo nodo;

    public ForSol(Nodo nodo) {
        this.nodo = nodo;
    }

    public void solve() {
        solve(nodo);
    }

    private void solve(Nodo n) {
        SolRel conditionSolver;
        Arbol arbol;

        List<Nodo> children = n.getHijos();
        Nodo assignDeclaration = children.get(0);
        Nodo condition = children.get(1);
        Nodo steps = children.get(2);

        Nodo parentBody = new Nodo(new Token(TipoToken.NULL, ""));
        parentBody.insertarHijos(children.subList(3, children.size()));
        parentBody.insertarHijo(steps);

        arbol = new Arbol(parentBody);

        // Es una declaración
        if (assignDeclaration.getValue().tipo == TipoToken.VAR) {
            VarSol varSolver = new VarSol(assignDeclaration);
            varSolver.solve();
        }
        // Es una asignación, la variable ya ha sido declarada
        else if (assignDeclaration.getValue().tipo == TipoToken.EQUAL_EQUAL) {
            EQEQSol assingSolver = new EQEQSol(assignDeclaration);
            assingSolver.solve();
        }



        conditionSolver = new SolRel(condition);
        Object conditionResult = conditionSolver.solve();

        if (conditionResult instanceof Boolean) {
            while ((Boolean) conditionResult) {
                arbol.recorrer();
                conditionResult = conditionSolver.solve();
            }
        }

    }
}
