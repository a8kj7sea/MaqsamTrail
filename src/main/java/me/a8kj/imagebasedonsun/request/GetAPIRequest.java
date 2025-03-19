package me.a8kj.imagebasedonsun.request;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.Optional;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.imagebasedonsun.object.GeoPosition;
import me.a8kj.imagebasedonsun.request.enums.HttpRequestStatus;
import me.a8kj.imagebasedonsun.request.enums.RequestExecutionStatus;
import me.a8kj.imagebasedonsun.request.exception.APIRequestException;
import me.a8kj.imagebasedonsun.request.exception.InvalidAPIParameters;
import me.a8kj.imagebasedonsun.request.properties.QueryParameter;
import me.a8kj.imagebasedonsun.request.util.QueryParameterFactory;
import me.a8kj.json.A8kjJson;
import me.a8kj.json.object.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Getter
public abstract class GetAPIRequest implements APIRequest {

    private static final Logger logger = Logger.getLogger(GetAPIRequest.class.getName());

    private final GeoPosition geoPosition;
    private final String timezone;
    private final Set<QueryParameter> parameters = new HashSet<>();
    private RequestExecutionStatus requestExecutionStatus;
    private JsonObject response;
    private final boolean enableLogging;

    public abstract String getQueryName();

    public GetAPIRequest(@NonNull GeoPosition geoPosition, boolean enableLogging) {
        this.geoPosition = geoPosition;
        this.timezone = ZonedDateTime.now().getZone().getId();
        this.requestExecutionStatus = RequestExecutionStatus.IDLE;
        this.response = null;
        this.enableLogging = enableLogging;

        if (enableLogging) {
            logger.info("GetAPIRequest initialized for location: " + geoPosition);
        }
    }

    @Override
    public void sendRequest(@NonNull String url) throws APIRequestException, InvalidAPIParameters {
        defineParameters(parameters);

        if (enableLogging) {
            logger.info("Parameters defined: " + parameters);
        }

        if (parameters.isEmpty()) {
            if (enableLogging) {
                logger.warning("API query parameters are missing. Throwing InvalidAPIParameters exception.");
            }
            throw new InvalidAPIParameters("parameters of API are required", new IllegalArgumentException());
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + QueryParameterFactory.create(parameters, "/" + getQueryName() + "?"))
                .build();

        if (enableLogging) {
            logger.info("Sending request to URL: " + request.url());
        }

        try (Response response = client.newCall(request).execute()) {
            HttpRequestStatus requestStatus = HttpRequestStatus.fromCode(response.code());

            if (requestStatus != HttpRequestStatus.OK || !response.isSuccessful()) {
                this.requestExecutionStatus = RequestExecutionStatus.FAILURE;
                if (enableLogging) {
                    logger.severe("Request failed with status: " + requestStatus.getDescription());
                }
                onGetRequest(requestExecutionStatus, getResponse());
                throw new APIRequestException("Request failed: " + requestStatus.getDescription());
            }

            if (response.body() != null) {
                String responseBody = response.body().string();
                if (enableLogging) {
                    logger.info("Response received: " + responseBody.substring(0, Math.min(responseBody.length(), 60)) // to make it clear
                            + "...");
                }
                this.response = Optional.ofNullable(A8kjJson.parse(responseBody).get())
                        .orElseThrow(() -> {
                            if (enableLogging) {
                                logger.severe("API response is empty or invalid.");
                            }
                            return new APIRequestException("API response is empty or invalid.");
                        });
            } else {
                if (enableLogging) {
                    logger.severe("API response body is null.");
                }
                throw new APIRequestException("API response body is null.");
            }

            this.requestExecutionStatus = RequestExecutionStatus.SUCCESS;
            if (enableLogging) {
                logger.info("Request successful. Execution status: " + requestExecutionStatus);
            }
            onGetRequest(requestExecutionStatus, this.response);
        } catch (Exception e) {
            if (enableLogging) {
                logger.severe("Error during API request execution: " + e.getMessage());
            }
            throw new APIRequestException("Error during API request execution: " + e.getMessage());
        }
    }

    public abstract void defineParameters(Set<QueryParameter> parameters);

    protected abstract void onGetRequest(RequestExecutionStatus getExecutionStatus, JsonObject response);
}
