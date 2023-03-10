package com.pearl.medicap.api;

import com.google.gson.JsonObject;
import com.pearl.medicap.model.CustomerMedicineList;
import com.pearl.medicap.model.ResponseModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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
            @Header("Authorization") String token,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("verifyotp")
    Call<ResponseModel> verifyOtp(
            @Header("Authorization") String token,
            @Field("otp") String otp
    );

    @FormUrlEncoded
    @POST("changePassword")
    Call<ResponseModel> changePassword(
            @Header("Authorization") String token,
            @Field("password") String password
    );


    @POST("getUserDetails")
    Call<ResponseModel> fetchUsersDetails(
            @Header("Authorization") String token

            );

//    @POST("getUserDetails")
//    Call<ResponseModel> fetchMedUsersDetails(
//            @Header("Authentication") String token
//    );


    @POST("addMedicineRequest")
    Call<ResponseModel> addMedicine(
            @Header("Authorization") String token,
            @Body JSONArray medicineDataList
            );
}
