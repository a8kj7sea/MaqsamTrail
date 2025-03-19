package me.a8kj.json.object;

import me.a8kj.json.JsonStructural;

public interface JsonObject extends JsonValue, JsonStructural {

    void put(String key, JsonValue value);

    JsonValue get(String key);

    boolean containsKey(String key);

    void remove(String key);

    void clear();

    void update(String key, JsonValue newValue);

    Iterable<String> keys();

    Iterable<JsonValue> values();
}
