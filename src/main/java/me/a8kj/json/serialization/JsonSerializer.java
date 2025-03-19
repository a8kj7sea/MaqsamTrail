package me.a8kj.json.serialization;

import me.a8kj.json.object.JsonObject;

public interface JsonSerializer<U> {

    JsonObject toJson(U u);

    U fromJson(JsonObject jsonObject);
}
