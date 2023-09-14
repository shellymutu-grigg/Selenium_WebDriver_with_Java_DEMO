package data;

import java.util.Hashtable;
import java.util.Map;

public class LocalStore {
        private static final ThreadLocal<Map<String, Object>> localStore = ThreadLocal.withInitial(Hashtable::new);

        public static void setObject(String key, Object object){
            // Store the global variable
            localStore.get().put(key, object);
        }

        public static Object getObject(String key){
            // Retrieve global variable
            return localStore.get().get(key);
        }
}
