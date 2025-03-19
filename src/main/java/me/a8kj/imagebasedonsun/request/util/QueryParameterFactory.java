package me.a8kj.imagebasedonsun.request.util;

import java.util.Set;

import me.a8kj.imagebasedonsun.request.properties.QueryParameter;

public class QueryParameterFactory {

    public static String create(Set<QueryParameter> parameters, String queryName) {
        if (parameters.isEmpty()) {
            return null;
        }

        StringBuilder queries = new StringBuilder(queryName);
        boolean isFirst = true;

        for (QueryParameter parameter : parameters) {
            if (parameter.key() == null || parameter.value() == null) {
                throw new IllegalStateException(
                        "Cannot retrieve a key-value for this parameter. It seems there is something wrong with your usage!");
            }

            if (!isFirst) {
                queries.append("&");
            }

            queries.append(parameter.key())
                    .append("=")
                    .append(parameter.value());

            isFirst = false;
        }

        return queries.toString();
    }

}
