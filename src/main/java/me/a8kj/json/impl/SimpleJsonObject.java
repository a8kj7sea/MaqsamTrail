package me.a8kj.json.impl;

import java.util.HashMap;
import java.util.Map;

import me.a8kj.json.exception.InvalidKeyException;
import me.a8kj.json.object.JsonObject;
import me.a8kj.json.object.JsonValue;


public class SimpleJsonObject implements JsonObject {
    private final Map<String, JsonValue> values = new HashMap<>();

    @Override
    public void put(String key, JsonValue value) {
        values.put(key, value);
    }

    @Override
    public JsonValue get(String key) {
        return values.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return values.containsKey(key);
    }

    @Override
    public void remove(String key) {
        values.remove(key);
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public void update(String key, JsonValue newValue) {
        if (values.containsKey(key)) {
            values.put(key, newValue);
        } else {
            throw new InvalidKeyException("Invalid key: " + key, new IllegalArgumentException());
        }
    }

    @Override
    public Iterable<String> keys() {
        return values.keySet();
    }

    @Override
    public Iterable<JsonValue> values() {
        return values.values();
    }

    @Override
    public String asString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<String, JsonValue> entry : values.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\":");
            sb.append(entry.getValue().asString()).append(",");
        }
        if (sb.length() > 1)
            sb.setLength(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }

}
