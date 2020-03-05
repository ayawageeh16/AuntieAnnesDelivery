package com.simpletouch.business.network;

public interface NetworkListener {
    // This Method is Implemented By All Activities
    default void isNetworkAvailable(Boolean isAvailable){}
}
