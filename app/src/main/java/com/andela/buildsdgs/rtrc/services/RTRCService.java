package com.andela.buildsdgs.rtrc.services;


import com.andela.buildsdgs.rtrc.models.User;
import com.andela.buildsdgs.rtrc.models.UserDetail;
import com.andela.buildsdgs.rtrc.models.Vehicle;
import com.andela.buildsdgs.rtrc.models.VehicleAddRequest;
import com.andela.buildsdgs.rtrc.models.VehicleCategoryList;
import com.andela.buildsdgs.rtrc.models.VehicleListResp;
import com.andela.buildsdgs.rtrc.models.WalletResponse;
import com.andela.buildsdgs.rtrc.utility.ServiceContants;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RTRCService {
    @POST(ServiceContants.CONTEXT_LOGIN)
    Call<UserDetail> loginUser(@Body User user);
    @POST(ServiceContants.CONTEXT_SIGNUP)
    Call<UserDetail> signUpUser(@Body User user);
    @GET(ServiceContants.CONTEXT_VEHICLE_CATEGORY_LIST)
    Call<VehicleCategoryList> getCategoryList(@Header("Authorization") String bearerToken);
    @POST(ServiceContants.CONTEXT_VEHICLES)
    Call<Vehicle> addVehicle(@Header("Authorization") String bearerToken, @Body VehicleAddRequest vehicle);
    @GET(ServiceContants.CONTEXT_VEHICLES)
    Call<VehicleListResp> getVehiclesList(@Header("Authorization") String bearerToken);
    @GET(ServiceContants.CONTEXT_VEHICLE_DETAIL)
    Call<Vehicle> getVehicleDetail(@Header("Authorization") String bearerToken, @Path("id") String id);
    @GET(ServiceContants.CONTEXT_WALLET_BALANCE)
    Call<WalletResponse> getUserWallet(@Header("Authorization") String bearerToken);
}
