import java.util.List;
public class PrintSol {
    private final Nodo nodo;

    public PrintSol(Nodo nodo) {
        this.nodo = nodo;
    }

    public void solve() {
        solve(nodo);
    }

    private void solve(Nodo n) {
        SolArit arithmeticSolver;

        if (n.getHijos() == null) {
            throw new RuntimeException("Args expected: 1. Received: 0.");
        }

        Nodo child = n.getHijos().get(0);
        arithmeticSolver = new SolArit(child);

        System.out.println(arithmeticSolver.solve());
    }
}
