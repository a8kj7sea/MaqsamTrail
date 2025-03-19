package me.a8kj.imagebasedonsun.request;

import me.a8kj.imagebasedonsun.request.exception.APIRequestException;
import me.a8kj.imagebasedonsun.request.exception.InvalidAPIParameters;

public interface APIRequest {

    void sendRequest(String url) throws APIRequestException, InvalidAPIParameters;

}
