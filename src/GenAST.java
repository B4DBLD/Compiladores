import java.util.List;
import java.util.Stack;

public class GenAST {
    private final List<Token> postfija;
    private final Stack<Nodo> pila;

    public GenAST(List<Token> postfija) {
        this.postfija = postfija;
        this.pila = new Stack<>();
    }

    public Arbol GenAST() {
        Stack<Nodo> pilaPadres = new Stack<>();
        Nodo raiz = new Nodo(null);

        pilaPadres.push(raiz);
        Nodo padre = raiz;

        for (Token t : postfija) {
            if (t.tipo == TipoToken.EOF) {
                break;
            }

            if (t.Keyword()) {
                Nodo n = new Nodo(t);

                padre = pilaPadres.peek();
                padre.insertarSiguienteHijo(n);

                pilaPadres.push(n);
                padre = n;

            } else if (t.isOperand()) {
                Nodo n = new Nodo(t);
                pila.push(n);
            } else if (t.isOperator()) {
                int aridad = t.aridity();
                Nodo n = new Nodo(t);
                for (int i = 1; i <= aridad; i++) {
                    if (!pila.isEmpty()) {
                        Nodo nodoAux = pila.pop();
                        n.insertarHijo(nodoAux);
                    }

                }
                pila.push(n);
            } else if (t.tipo == TipoToken.SEMICOLON) {

                if (pila.isEmpty()) {
                    /*
                    Si la pila esta vacía es porque t es un punto y coma
                    que cierra una estructura de control
                     */
                    pilaPadres.pop();
                    padre = pilaPadres.peek();
                } else {
                    Nodo n = pila.pop();

                    if (padre.getValue().tipo == TipoToken.VAR) {
                        /*
                        En el caso del VAR, es necesario eliminar el igual que
                        pudiera aparecer en la raíz del nodo n.
                         */
                        if (n.getValue().tipo == TipoToken.EQUAL) {
                            padre.insertarHijos(n.getHijos());
                        } else {
                            padre.insertarSiguienteHijo(n);
                        }
                        pilaPadres.pop();
                        padre = pilaPadres.peek();
                    } else if (padre.getValue().tipo == TipoToken.PRINT) {
                        padre.insertarSiguienteHijo(n);
                        pilaPadres.pop();
                        padre = pilaPadres.peek();
                    }
                    else {
                        padre.insertarSiguienteHijo(n);
                    }
                }
            }
        }

        // Suponiendo que en la pila sólamente queda un nodo
        // Nodo nodoAux = pila.pop();

        return new Arbol(raiz);
    }


}
