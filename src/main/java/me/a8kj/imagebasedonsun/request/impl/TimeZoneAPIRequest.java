package me.a8kj.imagebasedonsun.request.impl;

import java.util.Set;

import lombok.NonNull;
import me.a8kj.imagebasedonsun.app.ImageBasedOnSunApp;
import me.a8kj.imagebasedonsun.object.GeoPosition;
import me.a8kj.imagebasedonsun.processer.PreSelectImageProcesser;
import me.a8kj.imagebasedonsun.request.GetAPIRequest;
import me.a8kj.imagebasedonsun.request.enums.RequestExecutionStatus;
import me.a8kj.imagebasedonsun.request.properties.QueryParameter;
import me.a8kj.imagebasedonsun.serialization.TimeZoneInfoSerializer;
import me.a8kj.json.object.JsonObject;

public class TimeZoneAPIRequest extends GetAPIRequest {

    private @NonNull final String apiKey;

    public TimeZoneAPIRequest(@NonNull GeoPosition geoPosition, String apiKey) {
        super(geoPosition, ImageBasedOnSunApp.isDebuggingModeEnabled());
        this.apiKey = apiKey;
    }

    @Override
    public void defineParameters(Set<QueryParameter> parameters) {
        parameters.clear();
        parameters.add(new QueryParameter("lat", String.valueOf(this.getGeoPosition().latitude())));
        parameters.add(new QueryParameter("lng", String.valueOf(this.getGeoPosition().longitude())));
        parameters.add(new QueryParameter("apiKey", apiKey));

    }

    @Override
    protected void onGetRequest(RequestExecutionStatus getExecutionStatus, JsonObject response) {
        if (getExecutionStatus != RequestExecutionStatus.SUCCESS)
            return;

        assert response != null : "response cannot be null!";
        var timeZoneInfo = new TimeZoneInfoSerializer().fromJson(response);
        new PreSelectImageProcesser(getGeoPosition(), timeZoneInfo).process();
    }

    @Override
    public String getQueryName() {
        return "timezone";
    }
}
