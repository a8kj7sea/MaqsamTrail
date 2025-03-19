package me.a8kj.json.resolver.impl;

import me.a8kj.json.resolver.JsonValueResolver;
import me.a8kj.json.values.JsonString;


public class StringValueResolver implements JsonValueResolver<JsonString> {

   
    @Override
    public boolean canResolve(String value) {
        return !value.isEmpty();
    }

   
    @Override
    public JsonString resolve(String value) {
        return new JsonString(value);
    }
}
