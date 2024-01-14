package com.example.raviz_1_13_2024_exam;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @Headers({
            "Content-Type: multipart/form-data",
            "X-API-KEY: kCXnaVqQEtdnvNnOACGjpzyYotHRYgY6"
    })
    @FormUrlEncoded
    @POST("/v1/save")
    Call<ApiResponse> saveApplicant(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("mname") String mname,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("mobile_country_code") String mobileCountryCode,
            @Field("birth_date") String birthDate
    );

    @FormUrlEncoded
    @POST("/v1/get")
    Call<ApiResponse<ApplicationDetails>> getApplicantDetails(@Field("applicant_id") String applicantId);

    @FormUrlEncoded
    @POST("/v1/applicant_id")
    Call<ApiResponse<String>> getApplicantPersonalId(@Field("email") String email);

}
