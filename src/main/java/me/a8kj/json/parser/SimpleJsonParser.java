package me.a8kj.json.parser;

import me.a8kj.json.object.JsonArray;
import me.a8kj.json.object.JsonObject;
import me.a8kj.json.object.JsonValue;
import me.a8kj.json.values.JsonBoolean;
import me.a8kj.json.values.JsonNull;
import me.a8kj.json.values.JsonNumber;
import me.a8kj.json.values.JsonString;
import me.a8kj.json.JsonParser;
import me.a8kj.json.impl.SimpleJsonArray;
import me.a8kj.json.impl.SimpleJsonObject;

public class SimpleJsonParser implements JsonParser<JsonObject> {
    private String json;
    private int index;

    @Override
    public JsonObject parse(String json) {
        this.json = json.trim();
        this.index = 0;
        return parseObject();
    }

    private JsonObject parseObject() {
        JsonObject object = new SimpleJsonObject();
        if (json.charAt(index) != '{') {
            throw new IllegalArgumentException("Expected '{' at the beginning of the object.");
        }
        index++;

        skipWhitespace();

        while (json.charAt(index) != '}') {
            String key = parseString();
            skipWhitespace();
            if (json.charAt(index) != ':') {
                throw new IllegalArgumentException("Expected ':' after key.");
            }
            index++;
            skipWhitespace();
            JsonValue value = parseValue();
            object.put(key, value);
            skipWhitespace();
            if (json.charAt(index) == ',') {
                index++;
                skipWhitespace();
            }
        }
        index++;
        return object;
    }

    // lazem a3ml add ll comments processer / start with #
    private JsonValue parseValue() {
        char currentChar = json.charAt(index);
        if (currentChar == '"') {
            return parseStringValue();
        } else if (currentChar == '{') {
            return parseObject();
        } else if (currentChar == '[') {
            return parseArray();
        } else if (currentChar == 'n') {
            return parseNull();
        } else if (currentChar == 't' || currentChar == 'f') {
            return parseBoolean();
        } else if (Character.toString(currentChar).matches("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?")) {
            return parseNumber();

        } else {
            throw new IllegalArgumentException("Unexpected character: " + currentChar);
        }
    }

    private JsonBoolean parseBoolean() {
        if (json.startsWith("true", index)) {
            index += 4;
            return JsonBoolean.TRUE;
        } else if (json.startsWith("false", index)) {
            index += 5;
            return JsonBoolean.FALSE;
        } else {
            throw new IllegalArgumentException("Invalid boolean value.");
        }
    }

    private JsonString parseStringValue() {
        StringBuilder sb = new StringBuilder();
        index++;
        while (json.charAt(index) != '"') {
            sb.append(json.charAt(index));
            index++;
        }
        index++;
        return new JsonString(sb.toString());
    }

    private String parseString() {
        StringBuilder sb = new StringBuilder();
        index++;
        while (json.charAt(index) != '"') {
            sb.append(json.charAt(index));
            index++;
        }
        index++;
        return sb.toString();
    }

    private JsonArray parseArray() {
        JsonArray array = new SimpleJsonArray();
        index++;
        skipWhitespace();
        while (json.charAt(index) != ']') {
            JsonValue value = parseValue();
            array.add(value);
            skipWhitespace();
            if (json.charAt(index) == ',') {
                index++;
                skipWhitespace();
            }
        }
        index++;
        return array;
    }

    private JsonNull parseNull() {
        if (json.startsWith("null", index)) {
            index += 4;
            return JsonNull.INSTANCE;
        } else {
            throw new IllegalArgumentException("Invalid null value");
        }
    }

    private JsonNumber parseNumber() {
        String numberPattern = "-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?";
        StringBuilder sb = new StringBuilder();
        char currentChar = json.charAt(index);

        while (currentChar >= '0' && currentChar <= '9' || currentChar == '.' || currentChar == '-'
                || (currentChar == 'e' || currentChar == 'E')) {
            sb.append(currentChar);
            index++;
            currentChar = json.charAt(index);
        }

        String numberString = sb.toString().trim();

        if (numberString.matches(numberPattern)) {
            try {
                if (numberString.contains(".") || numberString.contains("e") || numberString.contains("E")) {
                    return new JsonNumber(Double.parseDouble(numberString));
                } else {
                    try {
                        return new JsonNumber(Integer.parseInt(numberString));
                    } catch (NumberFormatException e1) {
                        return new JsonNumber(Long.parseLong(numberString));
                    }
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format: " + numberString);
            }
        } else {
            throw new IllegalArgumentException("Invalid number format: " + numberString);
        }
    }

    private void skipWhitespace() {
        while (index < json.length() && (json.charAt(index) == ' ' || json.charAt(index) == '\n'
                || json.charAt(index) == '\r' || json.charAt(index) == '\t')) {
            index++;
        }
    }
}
