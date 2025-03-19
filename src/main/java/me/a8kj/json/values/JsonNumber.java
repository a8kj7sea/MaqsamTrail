package me.a8kj.json.values;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.json.object.JsonValue;

public class JsonNumber implements JsonValue {

    @Getter
    private final double value;

    public JsonNumber(int value) {
        this.value = value;
    }

    public JsonNumber(double value) {
        this.value = value;
    }

    public JsonNumber(long value) {
        this.value = value;
    }

    @Override
    public @NonNull String asString() {
        if (value == (int) value) {
            return String.format("%d", (int) value);
        } else if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%s", value);
        }
    }
}
