import java.util.List;

public class IfSol {
    private final Nodo nodo;

    public IfSol(Nodo nodo) {
        this.nodo = nodo;
    }

    public void solve() {
        solve(nodo);
    }

    private Object solve(Nodo n) {
        Arbol arbol;
        boolean rcond;

        List<Nodo> children = n.getHijos();


       if (children == null){
           if (n.getValue().tipo == TipoToken.NUMBER || n.getValue().tipo == TipoToken.STRING){
               return n.getValue().literal;
           }
           else if (n.getValue().tipo == TipoToken.IDENTIFIER){
               return TablaSimbolos.getInstance().obtener(n.getValue().lexema);
           }
       }

        Nodo condition = children.get(0);
       rcond = conditionSolver(condition);

       boolean elseExist = false;
       List<Nodo> body;
       List<Nodo> elseBody = null;

       if(children.get(children.size()-1).getValue().tipo == TipoToken.ELSE){
           elseExist = true;
           body = children.subList(1, children.size()-1);
           elseBody = children.get(children.size()-1).getHijos();
       }
       else{
           body = children.subList(1, children.size());

       }

       if(rcond){
           Nodo raiz = new Nodo(new Token(TipoToken.NULL, ""));
           raiz.insertarHijos(body);
           arbol = new Arbol(raiz);
           arbol.recorrer();
       }
       else if(elseExist){
           Nodo raiz = new Nodo(new Token(TipoToken.NULL, ""));
           raiz.insertarHijos(elseBody);
           arbol = new Arbol(raiz);
           arbol.recorrer();

       }


        return null;
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
