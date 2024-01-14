package com.example.raviz_1_13_2024_exam;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayChatApiManager {
    private final ApiService apiService;

    public PayChatApiManager(ApiService apiService) {
        this.apiService = apiService;
    }

    public void saveApplicant(String fname, String lname, String mname,
                              String email, String mobile, String mobileCountryCode,
                              String birthDate, Callback<ApiResponse<String>> callback) {
        // Validation checks
        // Validation checks
        if (!isValidString(fname, 40) ||
                !isValidString(lname, 40) ||
                !isValidString(mname, 40) ||
                !isValidEmail(email, 50) ||
                !isValidNumeric(mobile, 15) ||
                !isValidMobileCountryCode(mobileCountryCode) ||
                !isValidBirthDate(birthDate)) {
            System.out.println("Validation Error");
            return;
        }



        Call<ApiResponse> call = apiService.saveApplicant(fname, lname, mname, email, mobile, mobileCountryCode, birthDate);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                   System.out.println("Call Successful");
                } else {
                    System.out.println("Call Failed");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                System.out.println("Api Response Failed");
            }
        });

    }

    public void getApplicantDetails(String applicantId) {
        Call<ApiResponse<ApplicationDetails>> call = apiService.getApplicantDetails(applicantId);

        call.enqueue(new Callback<ApiResponse<ApplicationDetails>>() {
            @Override
            public void onResponse(Call<ApiResponse<ApplicationDetails>> call, Response<ApiResponse<ApplicationDetails>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<ApplicationDetails> apiResponse = response.body();
                    System.out.println(apiResponse);
                } else {
                    System.out.println("get APplication details failed");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ApplicationDetails>> call, Throwable t) {
                // Handle failure
                System.out.println("api call app details failed");
            }
        });
    }

    public void getApplicantPersonalId(String email) {
        Call<ApiResponse<String>> call = apiService.getApplicantPersonalId(email);

        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {

                    ApiResponse<String> apiResponse = response.body();
                    System.out.println("get applicant id succesful");
                } else {

                    System.out.println("aplicant id : failed");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                System.out.println("failed to call");
            }
        });
    }


    // Validation methods
    private boolean isValidString(String value, int maxLength) {
        return !TextUtils.isEmpty(value) && value.length() <= maxLength;
    }

    private boolean isValidEmail(String email, int maxLength) {
        return isValidString(email, maxLength) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidNumeric(String value, int maxLength) {
        return isValidString(value, maxLength) && TextUtils.isDigitsOnly(value);
    }

    private boolean isValidMobileCountryCode(String countryCode) {
        return TextUtils.equals(countryCode, "63");
    }

    private boolean isValidBirthDate(String birthDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(birthDate);
            return parsedDate != null && birthDate.equals(dateFormat.format(parsedDate));

        } catch (ParseException e) {
            return false;
        }
    }

    }
