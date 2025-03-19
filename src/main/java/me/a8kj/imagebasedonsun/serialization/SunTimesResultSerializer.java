package me.a8kj.imagebasedonsun.serialization;

import java.time.OffsetDateTime;

import me.a8kj.imagebasedonsun.object.SunTimesResult;
import me.a8kj.json.impl.SimpleJsonObject;
import me.a8kj.json.object.JsonObject;
import me.a8kj.json.serialization.JsonSerializer;
import me.a8kj.json.values.JsonString;
import me.a8kj.json.values.JsonNumber;

public class SunTimesResultSerializer implements JsonSerializer<SunTimesResult> {

    @Override
    public JsonObject toJson(SunTimesResult u) {
        JsonObject jsonObject = new SimpleJsonObject();

        jsonObject.put("sunrise", new JsonString(u.getSunrise().toString()));
        jsonObject.put("sunset", new JsonString(u.getSunset().toString()));
        jsonObject.put("solar_noon", new JsonString(u.getSolarNoon().toString()));
        jsonObject.put("day_length", new JsonNumber(u.getDayLength()));
        jsonObject.put("civil_twilight_begin", new JsonString(u.getCivilTwilightBegin().toString()));
        jsonObject.put("civil_twilight_end", new JsonString(u.getCivilTwilightEnd().toString()));
        jsonObject.put("nautical_twilight_begin", new JsonString(u.getNauticalTwilightBegin().toString()));
        jsonObject.put("nautical_twilight_end", new JsonString(u.getNauticalTwilightEnd().toString()));
        jsonObject.put("astronomical_twilight_begin", new JsonString(u.getAstronomicalTwilightBegin().toString()));
        jsonObject.put("astronomical_twilight_end", new JsonString(u.getAstronomicalTwilightEnd().toString()));

        return jsonObject;
    }

    @Override
    public SunTimesResult fromJson(JsonObject jsonObject) {
        OffsetDateTime sunrise = OffsetDateTime.parse(jsonObject.get("sunrise").asString().replace("\"", ""));
        OffsetDateTime sunset = OffsetDateTime.parse(jsonObject.get("sunset").asString().replace("\"", ""));
        OffsetDateTime solarNoon = OffsetDateTime.parse(jsonObject.get("solar_noon").asString().replace("\"", ""));
        int dayLength = (int) ((JsonNumber) jsonObject.get("day_length")).getValue();
        OffsetDateTime civilTwilightBegin = OffsetDateTime.parse(jsonObject.get("civil_twilight_begin").asString().replace("\"", ""));
        OffsetDateTime civilTwilightEnd = OffsetDateTime.parse(jsonObject.get("civil_twilight_end").asString().replace("\"", ""));
        OffsetDateTime nauticalTwilightBegin = OffsetDateTime.parse(jsonObject.get("nautical_twilight_begin").asString().replace("\"", ""));
        OffsetDateTime nauticalTwilightEnd = OffsetDateTime.parse(jsonObject.get("nautical_twilight_end").asString().replace("\"", ""));
        OffsetDateTime astronomicalTwilightBegin = OffsetDateTime.parse(jsonObject.get("astronomical_twilight_begin").asString().replace("\"", ""));
        OffsetDateTime astronomicalTwilightEnd = OffsetDateTime.parse(jsonObject.get("astronomical_twilight_end").asString().replace("\"", ""));
    
        return new SunTimesResult(
                sunrise,
                sunset,
                solarNoon,
                dayLength,
                civilTwilightBegin,
                civilTwilightEnd,
                nauticalTwilightBegin,
                nauticalTwilightEnd,
                astronomicalTwilightBegin,
                astronomicalTwilightEnd
        );
    }
    
}
