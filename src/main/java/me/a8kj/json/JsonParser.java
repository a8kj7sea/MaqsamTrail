package me.a8kj.json;

import lombok.NonNull;
import me.a8kj.json.object.JsonValue;

public interface JsonParser<U extends JsonValue & JsonStructural> {

    @NonNull
    U parse(@NonNull String json);
}
