package me.a8kj.json.impl;

import me.a8kj.json.object.JsonArray;
import me.a8kj.json.object.JsonValue;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class SimpleJsonArray implements JsonArray {

    private List<JsonValue> values;

    public SimpleJsonArray() {
        this.values = new ArrayList<>();
    }

    @Override
    public void add(JsonValue value) {
        values.add(value);
    }

    @Override
    public JsonValue get(int index) {
        if (index < 0 || index >= values.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return values.get(index);
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public @NonNull String asString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < values.size(); i++) {
            JsonValue value = values.get(i);
            sb.append(value.asString());
            if (i < values.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public List<JsonValue> values() {
        return values;
    }
}
