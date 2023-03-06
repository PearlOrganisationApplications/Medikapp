package com.pearl.medicap.api;

import com.pearl.medicap.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Methods {
    @FormUrlEncoded
    @POST("registerMedical")
    Call<ResponseModel> registerMedicalUser(
            @Field("owner_name") String owner_name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password,
            @Field("medical_name") String medical_name,
            @Field("gst_no") String gst_no,
            @Field("certificate") String certificate,
            @Field("certificate_ext") String certificate_ext,
            @Field("shop_img") String shop_img,
            @Field("shop_img_ext") String shop_img_ext,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("location_name") String location_name

    );

    @FormUrlEncoded
    @POST("signup")
    Call<ResponseModel> registerUser(
            @Field("name") String name,
            @Field("dob") String dob,
            @Field("email") String email,
            @Field("contactnumber") String contactnumber,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("signin")
    Call<ResponseModel> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<ResponseModel> forgotPass(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("verifyotp")
    Call<ResponseModel> verifyOtp(
            @Field("otp") String otp
    );

    @FormUrlEncoded
    @POST("changePassword")
    Call<ResponseModel> changePassword(
            @Field("token") String token,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("verifyUserEmail")
    Call<ResponseModel> verifyEmail(
            @Field("email") String email
    );

    @GET("getUsers")
    Call<ResponseModel> fetchUsersDetails();
}
