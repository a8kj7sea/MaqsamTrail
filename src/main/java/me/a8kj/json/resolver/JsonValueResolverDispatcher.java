package me.a8kj.json.resolver;

import java.util.HashSet;
import java.util.Set;

import me.a8kj.json.object.JsonValue;

public class JsonValueResolverDispatcher {

    private static final Set<JsonValueResolver<?>> resolvers = new HashSet<>();

    public static void registerResolver(JsonValueResolver<?> resolver) {
        resolvers.add(resolver);
    }

    public static JsonValue resolve(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Cannot resolve empty or null value");
        }
        for (JsonValueResolver<?> resolver : resolvers) {
            if (resolver.canResolve(value)) {
                return (JsonValue) resolver.resolve(value);
            }
        }
        throw new IllegalStateException("No resolver for this value: " + value);
    }
}
