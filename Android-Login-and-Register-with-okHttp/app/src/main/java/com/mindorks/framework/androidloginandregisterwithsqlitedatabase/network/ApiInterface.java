package com.mindorks.framework.androidloginandregisterwithsqlitedatabase.network;

import com.google.gson.JsonObject;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.request.AuthRequest;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.request.SignUpRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

//    @GET("posts")
//    Call<List<JsonObject>>getAllPharmacies();

    @POST("login")
    Call<JsonObject> postLogin(@Body AuthRequest authRequest);

    @POST("register")
    Call<JsonObject> postRegister(@Body SignUpRequest signUpRequest);

//    @FormUrlEncoded
//    @POST("/auth/")
//    Call<JsonObject>postLogin(@Field("username") String name,
//                              @Field("password") String password);

//    @GET("pharmacy/{id}")
//    Call<Pharmacy>getPharmacyById(@Path("id") int id);
//
//    @GET("pharmacyView")
//    Call<Pharmacy>getLoggedInUserPharmacy(@Query("api_token") String token);
//
//    @POST("logout")
//    Call<JsonObject>postLogoutAuthUser(@Query("api_token") String token);
//
//    @FormUrlEncoded
//    @POST("rating")
//    Call<JsonObject>postRatingCurrentPharmacy(
//            @Field("app_user_id") String appUserId,
//            @Field("pharmacy_id") int pharmacyId,
//            @Field("rate") Float rating
//    );
//
//    @FormUrlEncoded
//    @POST("add/app-user")
//    Call<JsonObject> postMobileUniqueId(
//            @Field("broadcast_key") String broadcastToken,
//            @Field("device_type") String deviceType
//    );



}
