package me.a8kj.imagebasedonsun.processer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.imagebasedonsun.object.GeoPosition;
import me.a8kj.imagebasedonsun.object.TimezoneInfo;
import me.a8kj.imagebasedonsun.request.APIRequest;
import me.a8kj.imagebasedonsun.request.exception.APIRequestException;
import me.a8kj.imagebasedonsun.request.exception.InvalidAPIParameters;
import me.a8kj.imagebasedonsun.request.impl.SunriseSunsetAPIRequest;

@Getter
@RequiredArgsConstructor
public class PreSelectImageProcesser implements Processer {

    private final GeoPosition geoPosition;
    private final TimezoneInfo timezoneInfo;

    @Override
    public void process() {

        APIRequest apiRequest = new SunriseSunsetAPIRequest(geoPosition, timezoneInfo);
        try {
            apiRequest.sendRequest("https://api.sunrise-sunset.org");
        } catch (APIRequestException | InvalidAPIParameters e) {
            e.printStackTrace();
        }

    }
}
