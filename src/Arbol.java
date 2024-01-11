public class Arbol {
    private final Nodo raiz;
    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            try {
                Token t = n.getValue();
                switch (t.tipo) {
                    // Operadores aritméticos
                    case PLUS:
                    case MINUS:
                    case STAR:
                    case SLASH:
                        SolArit solver = new SolArit(n);
                        solver.solve();
                        break;
                    case LESS:
                    case GREATER:
                    case LESS_EQUAL:
                    case GREATER_EQUAL:
                    case EQUAL_EQUAL:
                    case BANG_EQUAL:
                        SolRel relSolver = new SolRel(n);
                        relSolver.solve();
                        break;
                    case AND:
                    case OR:
                        SolLog logSolver = new SolLog(n);
                        logSolver.solve();
                        break;
                    case VAR:
                        VarSol varSolver = new VarSol(n);
                        varSolver.solve();
                        break;
                    case IF:
                        IfSol ifSolver = new IfSol(n);
                        ifSolver.solve();
                        break;
                    case FOR:
                        ForSol forSolver = new ForSol(n);
                        forSolver.solve();
                        break;
                    case WHILE:
                        WhileSol whileSolver = new WhileSol(n);
                        whileSolver.solve();
                        break;
                    case PRINT:
                        PrintSol printSolver = new PrintSol(n);
                        printSolver.solve();
                        break;
                    case EQUAL:
                        EQEQSol eqSolver = new EQEQSol(n);
                        eqSolver.solve();
                    default:
                        break;

                }
            }
            catch (Exception e){
                Interprete.existenErrores = true;
                Interprete.error(n.getValue().numeroLinea, e.getMessage());
                break;
            }

        }
    }

}
