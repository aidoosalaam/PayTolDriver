package com.andela.buildsdgs.rtrc.utility;

import com.andela.buildsdgs.rtrc.models.User;
import com.andela.buildsdgs.rtrc.models.UserDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("auth/login/")
    Call<UserDetail> login(User user,Callback<UserDetail> callback);
}
