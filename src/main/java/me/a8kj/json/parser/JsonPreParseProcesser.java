package me.a8kj.json.parser;

import lombok.AllArgsConstructor;
import me.a8kj.json.object.JsonValue;

@AllArgsConstructor
@Deprecated(forRemoval = true) // kiss law
public abstract class JsonPreParseProcesser<U extends JsonValue> {

    protected final String json;
    protected int index;

    public abstract U process();

}
