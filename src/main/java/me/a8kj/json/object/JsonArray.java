package me.a8kj.json.object;

import java.util.List;

import me.a8kj.json.JsonStructural;

public interface JsonArray extends JsonValue, JsonStructural {

    void add(JsonValue value);

    JsonValue get(int index);

    List<JsonValue> values();

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }
}
