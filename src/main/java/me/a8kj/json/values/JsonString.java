package me.a8kj.json.values;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.a8kj.json.object.JsonValue;

@RequiredArgsConstructor
@Getter
public class JsonString implements JsonValue {
    @Getter
    private final String value;

    @Override
    public @NonNull String asString() {
        return "\"" + value + "\"";
    }

}
