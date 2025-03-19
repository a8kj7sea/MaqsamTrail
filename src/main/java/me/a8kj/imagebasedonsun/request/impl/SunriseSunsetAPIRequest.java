package me.a8kj.imagebasedonsun.request.impl;

import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.imagebasedonsun.app.ImageBasedOnSunApp;
import me.a8kj.imagebasedonsun.object.GeoPosition;
import me.a8kj.imagebasedonsun.object.SunTimesResult;
import me.a8kj.imagebasedonsun.object.TimezoneInfo;
import me.a8kj.imagebasedonsun.processer.SelectImageProcesser;
import me.a8kj.imagebasedonsun.request.GetAPIRequest;
import me.a8kj.imagebasedonsun.request.enums.RequestExecutionStatus;
import me.a8kj.imagebasedonsun.request.properties.QueryParameter;
import me.a8kj.imagebasedonsun.serialization.SunTimesResultSerializer;
import me.a8kj.json.object.JsonObject;

@Getter
public class SunriseSunsetAPIRequest extends GetAPIRequest {

    private final TimezoneInfo timezoneInfo;

    public SunriseSunsetAPIRequest(@NonNull GeoPosition geoPosition, TimezoneInfo timezoneInfo) {
        super(geoPosition, ImageBasedOnSunApp.isDebuggingModeEnabled());
        this.timezoneInfo = timezoneInfo;
    }

    @Override
    public void defineParameters(Set<QueryParameter> parameters) {
        parameters.clear();
        parameters.add(new QueryParameter("lat", String.valueOf(this.getGeoPosition().latitude())));
        parameters.add(new QueryParameter("lng", String.valueOf(this.getGeoPosition().longitude())));
        parameters.add(new QueryParameter("formatted", String.valueOf(0)));
    }

    @Override
    protected void onGetRequest(RequestExecutionStatus getExecutionStatus, JsonObject response) {
        if (getExecutionStatus != RequestExecutionStatus.SUCCESS)
            return;

        assert response != null : "response cannot be null!";
        if (response.get("status").asString() == null)
            throw new IllegalStateException("Cannot fetch site response correctly !");

        JsonObject results = (JsonObject) response.get("results");

        SunTimesResult apiResult = new SunTimesResultSerializer().fromJson(results);

        new SelectImageProcesser(timezoneInfo, apiResult).process();

    }

    @Override
    public String getQueryName() {
        return "json";
    }

}
