import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {

    private final Map<String, Object> values = new HashMap<>();
    private static TablaSimbolos instancia;

    public static TablaSimbolos getInstance() {
    if (instancia == null) {
            instancia = new TablaSimbolos();
        }
        return instancia;
    }

    public boolean existeIdentificador(String key) {
        return values.containsKey(key);
    }

    public Object obtener(String key) {
        if (existeIdentificador(key)) {
            return values.get(key);
        } else {
            throw new RuntimeException("Variable " + key + " no definida");
        }
    }

    public void asignarValor(String key, Object value) {
        if (existeIdentificador(key)) {
            values.put(key, value);
        } else {
            values.put(key, value);
        }
    }

    public void reasignar(String key, Object value) {
        values.put(key, value);
    }


}
