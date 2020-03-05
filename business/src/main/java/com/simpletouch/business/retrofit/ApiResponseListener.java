package com.simpletouch.business.retrofit;

public interface ApiResponseListener<Response> {

    void onResponseReceived(int statusCode, Response response);

    void onError();

    void onFailure(int errorCode);
}
