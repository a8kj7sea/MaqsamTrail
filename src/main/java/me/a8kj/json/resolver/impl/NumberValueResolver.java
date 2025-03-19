package me.a8kj.json.resolver.impl;

import me.a8kj.json.resolver.JsonValueResolver;
import me.a8kj.json.values.JsonNumber;


public class NumberValueResolver implements JsonValueResolver<JsonNumber> {

 
    @Override
    public boolean canResolve(String value) {
        return value.matches("-?\\d+(\\.\\d+)?");
    }

    
    @Override
    public JsonNumber resolve(String value) {
        return new JsonNumber(Double.parseDouble(value));
    }
}
