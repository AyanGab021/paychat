package com.example.raviz_1_13_2024_exam;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class exam2 extends AppCompatActivity {
    TextView txtId;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam2);

        txtId = findViewById(R.id.applicantID);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://odin.paychat.ph/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        txtId.setVisibility(View.INVISIBLE);
        ApiService payChatApi = retrofit.create(ApiService.class);
        PayChatApiManager apiManager = new PayChatApiManager(payChatApi);

        apiManager.saveApplicant("Brian Ken", "Raviz", "Gabriel", "ravizbrian@gmail.com", "09691243868", "63", "10/21/2000", new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> apiResponse = response.body();
                    id = apiResponse.getResults().getData();
                    Log.d("Debug", "ID: " + id); // Add this line for debugging
                    txtId.setText(id);


                    // Now that id is available, make subsequent requests
                    apiManager.getApplicantDetails(id);
                    apiManager.getApplicantPersonalId("ravizbrian@gmail.com");
                    Toast.makeText(exam2.this, "ID: " + id, Toast.LENGTH_SHORT).show();
                } else {
                    txtId.setText("failed");
                    Log.d("Debug", "Failed to get ID");
                    Toast.makeText(exam2.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                // Handle failure
                Log.e("Error", "Network request failed", t);
                Toast.makeText(exam2.this, "Network request failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
