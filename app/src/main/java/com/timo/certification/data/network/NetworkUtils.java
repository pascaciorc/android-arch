package com.timo.certification.data.network;

public class NetworkUtils {
    private static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
