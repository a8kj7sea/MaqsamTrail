package me.a8kj.imagebasedonsun.processer;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.imagebasedonsun.action.actions.PrintResultAction;
import me.a8kj.imagebasedonsun.action.actions.change.impl.LinuxChanger;
import me.a8kj.imagebasedonsun.action.actions.change.impl.WindowsChanger;
import me.a8kj.imagebasedonsun.app.ImageBasedOnSunApp;
import me.a8kj.imagebasedonsun.object.SunTimesResult;
import me.a8kj.imagebasedonsun.object.TimezoneInfo;

@RequiredArgsConstructor
@Getter
public class SelectImageProcesser implements Processer {

    private final TimezoneInfo timezoneInfo;
    private final SunTimesResult sunTimes;

    @Override
    public void process() {
        String imageName = getImageName(ZoneId.of(timezoneInfo.getTimezone()));
        String actionName = ImageBasedOnSunApp.getAction();

        if ("mix".equalsIgnoreCase(actionName)) {
            printAndChangeBackground(imageName);
        } else if ("change".equalsIgnoreCase(actionName)) {
            changeBackground(imageName);
        } else {
            printResult(imageName);
        }
    }

    private void printAndChangeBackground(String imageName) {
        new PrintResultAction(imageName).perform();
        changeBackground(imageName);
    }

    @Deprecated
    private void changeBackground(String imageName) {
        var changers = Arrays.asList(
                new LinuxChanger(ImageBasedOnSunApp.getImageFileByName(imageName)),
                new WindowsChanger(ImageBasedOnSunApp.getImageFileByName(imageName)));
        for (var changer : changers) {
            if (changer.canChange()) {
                changer.change();

            }
        }
    }

    private void printResult(String imageName) {
        new PrintResultAction(imageName).perform();
    }

    private String getImageName(ZoneId zoneId) {
        ZonedDateTime now = ZonedDateTime.now(zoneId);

        ZonedDateTime sunriseTime = sunTimes.getSunrise().atZoneSameInstant(zoneId);
        ZonedDateTime sunsetTime = sunTimes.getSunset().atZoneSameInstant(zoneId);
        ZonedDateTime solarNoonTime = sunTimes.getSolarNoon().atZoneSameInstant(zoneId);
        ZonedDateTime twilightBegin = sunTimes.getCivilTwilightBegin().atZoneSameInstant(zoneId);
        ZonedDateTime twilightEnd = sunTimes.getCivilTwilightEnd().atZoneSameInstant(zoneId);

        int dayLengthInSeconds = sunTimes.getDayLength(); //
        int dayLengthInMinutes = dayLengthInSeconds / 60;
        ZonedDateTime midMorning = sunriseTime.plusMinutes(dayLengthInMinutes / 4);
        ZonedDateTime midAfternoon = solarNoonTime.plusMinutes(dayLengthInMinutes / 4);

        if (now.isBefore(twilightBegin) || now.isAfter(twilightEnd)) {
            return "night.png";
        } else if (now.isAfter(twilightBegin) && now.isBefore(sunriseTime)) {
            return "morning.png";
        } else if (now.isAfter(sunriseTime) && now.isBefore(sunriseTime.plusMinutes(30))) {
            return "sunrise.png";
        } else if (now.isAfter(sunriseTime.plusMinutes(30)) && now.isBefore(midMorning)) {
            return "morning.png";
        } else if (now.isAfter(midMorning) && now.isBefore(solarNoonTime.minusMinutes(30))) {
            return "morning.png";
        } else if (now.isAfter(solarNoonTime.minusMinutes(30)) && now.isBefore(solarNoonTime.plusMinutes(30))) {
            return "noon.png";
        } else if (now.isAfter(solarNoonTime.plusMinutes(30)) && now.isBefore(midAfternoon)) {
            return "evening.png";
        } else if (now.isAfter(midAfternoon) && now.isBefore(sunsetTime.minusMinutes(30))) {
            return "evening.png";
        } else if (now.isAfter(sunsetTime.minusMinutes(30)) && now.isBefore(sunsetTime.plusMinutes(30))) {
            return "sunset.png";
        } else if (now.isAfter(sunsetTime.plusMinutes(30)) && now.isBefore(twilightEnd)) {
            return "night.png";
        }

        return "night.png";
    }
}