package com.andela.buildsdgs.rtrc.services;

import com.andela.buildsdgs.rtrc.models.User;
import com.andela.buildsdgs.rtrc.models.UserDetail;
import com.andela.buildsdgs.rtrc.utility.Configuration;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RTRCService {
    @POST(Configuration.CONTEXT_LOGIN)
    Call<UserDetail> loginUser(@Body User user);
}
