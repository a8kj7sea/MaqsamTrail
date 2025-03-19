package me.a8kj.json.resolver;

import lombok.NonNull;

public interface JsonValueResolver<U> {

    boolean canResolve(@NonNull String value);

    @NonNull
    U resolve(@NonNull String value);
}
