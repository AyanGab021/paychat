package com.example.raviz_1_13_2024_exam;

public class ApiResult<T> {
    private T data;
    private String message;

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
