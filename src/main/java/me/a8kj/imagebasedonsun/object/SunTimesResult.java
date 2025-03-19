package me.a8kj.imagebasedonsun.object;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SunTimesResult {

    private OffsetDateTime sunrise;
    private OffsetDateTime sunset;
    private OffsetDateTime solarNoon;
    private int dayLength;
    private OffsetDateTime civilTwilightBegin;
    private OffsetDateTime civilTwilightEnd;
    private OffsetDateTime nauticalTwilightBegin;
    private OffsetDateTime nauticalTwilightEnd;
    private OffsetDateTime astronomicalTwilightBegin;
    private OffsetDateTime astronomicalTwilightEnd;

    public static OffsetDateTime parseOffsetDateTime(String dateTimeStr) {
        return OffsetDateTime.parse(dateTimeStr);
    }
}
