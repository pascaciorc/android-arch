package com.timo.certification.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    @GET("/users/{id}")
    Call<UserApiResponse> getUser(@Path("id") String id);
}
