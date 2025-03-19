package me.a8kj.imagebasedonsun.serialization;

import lombok.NonNull;
import me.a8kj.imagebasedonsun.object.TimezoneInfo;
import me.a8kj.json.impl.SimpleJsonObject;
import me.a8kj.json.object.JsonObject;
import me.a8kj.json.serialization.JsonSerializer;
import me.a8kj.json.values.JsonBoolean;
import me.a8kj.json.values.JsonNumber;
import me.a8kj.json.values.JsonString;

public class TimeZoneInfoSerializer implements JsonSerializer<TimezoneInfo> {

    @Override
    @Deprecated
    public JsonObject toJson(TimezoneInfo u) {
        JsonObject jsonObject = new SimpleJsonObject();
        jsonObject.put("dst_exists", new JsonBoolean(u.hasDST()));
        jsonObject.put("is_dst", new JsonBoolean(u.isDstEnabled()));
        jsonObject.put("timezone", new JsonString(u.getTimezone()));
        jsonObject.put("timezone_offset", new JsonNumber(u.getOffset()));
        return jsonObject;
    }

    @Override
    public TimezoneInfo fromJson(@NonNull JsonObject jsonObject) {
        if (jsonObject.asString().isEmpty()) {
            throw new IllegalArgumentException("The provided JSON is empty.");
        }

        TimezoneInfo timezoneInfo = new TimezoneInfo();
        timezoneInfo.setDstExist(((JsonBoolean) jsonObject.get("dst_exists")).asBoolean());
        timezoneInfo.setDstEnabled(((JsonBoolean) jsonObject.get("is_dst")).asBoolean());
        timezoneInfo.setTimezone(((JsonString) jsonObject.get("timezone")).getValue());

        double offset = ((JsonNumber) jsonObject.get("timezone_offset")).getValue();
        double offsetWithDst = ((JsonNumber) jsonObject.get("timezone_offset_with_dst")).getValue();

        timezoneInfo.setOffset(offset);

        if (timezoneInfo.hasDST() && timezoneInfo.isDstEnabled()) {
            timezoneInfo.setOffset(offsetWithDst);
        }

        return timezoneInfo;
    }
}
