package com.example.raviz_1_13_2024_exam;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
    private boolean status;
    private int code;
    private ApiResult<T> results;

    public boolean isStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public ApiResult<T> getResults() {
        return results;
    }

    public static class Results {
        @SerializedName("data")
        private String data;

        @SerializedName("message")
        private String message;

        public String getData() {
            return data;
        }

        public String getMessage() {
            return message;
        }
    }
}
