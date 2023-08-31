package data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalStore {
    private static final ThreadLocal<Map<String, Object>> localStore = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public static void setObject(String key, Object object){
        // Store the global variable
        localStore.get().put(key, object);
    }

    public static Object getObject(String key){
        // Retrieve global variable
        return localStore.get().get(key);
    }
}
