package me.a8kj.json.resolver.impl;

import me.a8kj.json.resolver.JsonValueResolver;
import me.a8kj.json.values.JsonBoolean;


public class BooleanValueResolver implements JsonValueResolver<JsonBoolean> {

 
    @Override
    public boolean canResolve(String value) {
        return value.equals("true") || value.equals("false");
    }

   
    @Override
    public JsonBoolean resolve(String value) {
        return new JsonBoolean(Boolean.parseBoolean(value));
    }
}
