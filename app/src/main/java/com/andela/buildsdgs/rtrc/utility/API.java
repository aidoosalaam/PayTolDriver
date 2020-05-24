package com.andela.buildsdgs.rtrc.utility;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static APIInterface getClient(){
        Retrofit adapter = new Retrofit.Builder().baseUrl(Configuration.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        APIInterface api= adapter.create(APIInterface.class);
        return api;
    }

}
