package com.pruebas123.petagram.restApiFirebase.adapter;

import com.pruebas123.petagram.restApiFirebase.ConstantesRestApiFirebase;
import com.pruebas123.petagram.restApiFirebase.EndpointsRestApiFirebase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiFirebaseAdapter {

    public EndpointsRestApiFirebase establecerConRestApiFirebase(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApiFirebase.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(EndpointsRestApiFirebase.class);
    }
}
