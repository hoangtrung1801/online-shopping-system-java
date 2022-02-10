package database;

import java.util.HashMap;
import java.util.Map;

public class Store {
  private static Map<String, Object> store = new HashMap<>();

  public static Object get(String key) {
    if(!store.containsKey(key)) return null;
    return store.get(key); 
  }
  public static void set(String key, Object obj) {
    store.put(key, obj);
  }
}
