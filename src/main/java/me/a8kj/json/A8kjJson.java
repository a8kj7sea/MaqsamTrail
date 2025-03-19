package me.a8kj.json;

import lombok.NonNull;
import me.a8kj.json.impl.SimpleJsonObject;
import me.a8kj.json.object.JsonObject;
import me.a8kj.json.parser.SimpleJsonParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.logging.Logger;

public class A8kjJson {

    private static final Logger logger = Logger.getLogger(A8kjJson.class.getName());

    public static Optional<JsonObject> parse(@NonNull String json) {
        if (json.isEmpty()) {
            logger.warning("JSON string is empty");
            return Optional.empty();
        }
        try {
            return Optional.of((SimpleJsonObject) new SimpleJsonParser().parse(json));
        } catch (Exception e) {
            logger.severe("Error while parsing JSON: " + e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<JsonObject> fromFile(@NonNull File file) {
        try {
            String json = new String(Files.readAllBytes(file.toPath()));
            return parse(json);
        } catch (IOException e) {
            logger.severe("Error while reading file: " + e.getMessage());
            return Optional.empty();
        }
    }

    public static void writeToFile(@NonNull File file, @NonNull JsonObject jsonObject) {
        try {
            Files.write(file.toPath(), jsonObject.asString().getBytes());
        } catch (IOException e) {
            logger.severe("Error while writing to file: " + e.getMessage());
        }
    }
}
