package me.a8kj.json.values;

import lombok.NonNull;
import me.a8kj.json.object.JsonValue;

public class JsonNull implements JsonValue {

    public static final JsonNull INSTANCE = new JsonNull();

    @Override
    public @NonNull String asString() {
        return "null";
    }

}
