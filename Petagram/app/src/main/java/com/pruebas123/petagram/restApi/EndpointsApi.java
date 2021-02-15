package com.pruebas123.petagram.restApi;

import com.pruebas123.petagram.restApi.model.InstagramApiResponse;
import com.pruebas123.petagram.restApi.model.MediaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EndpointsApi {

    @GET(ConstantesRestApi.GET_USR_INFO)
    Call<InstagramApiResponse> getMedia();

    @GET("/{media_id}/" + ConstantesRestApi.GET_MEDIA_INFO)
    Call<MediaResponse> getMediaDetail(@Path("media_id") String media_id);


}
