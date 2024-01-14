package com.example.raviz_1_13_2024_exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// exam1.java
public class exam1 extends AppCompatActivity {
    TextView txt;
    private RecyclerView recView;
    private PersonAdapter adapter;
    private List<PersonModel> people;
    private static final int PERSON_DETAILS_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam1);



        // Assuming you have a RecyclerView with the ID 'listOfPeople' in your layout
        recView = findViewById(R.id.listOfPeople);

        // Load people from JSON
        people = loadPeopleFromJson();

        // Create and set the adapter
        adapter = new PersonAdapter(people, new PersonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PersonModel person) {
                // Handle item click, for example, show a toast with the person details
                String details = "Person ID: " + person.getPerson_id() +
                        "\nFirst Name: " + person.getFirst_name() +
                        "\nLast Name: " + person.getLast_name() +
                        "\nMobile: " + person.getMobile() +
                        "\nAge: " + person.getAge() +
                        "\nAddress: " + person.getAddress() +
                        "\nEmail: " + person.getEmail();

                String person_id = person.getPerson_id();
                String first_name = person.getFirst_name();
                String last_name = person.getLast_name();
                String number = person.getMobile();
                int age = person.getAge();
                String ageString = String.valueOf(age);
                String email = person.getEmail();
                String add = person.getAddress();

                Intent i = new Intent(getApplicationContext(), personsDetails.class);
                i.putExtra("firtname", first_name);
                i.putExtra("lastname", last_name);
                i.putExtra("number", number);
                i.putExtra("age", ageString);
                i.putExtra("email", email);
                i.putExtra("add", add);
                i.putExtra("id", person_id);
                startActivityForResult(i, PERSON_DETAILS_REQUEST_CODE); // Use the constant instead of YOUR_REQUEST_CODE
            }
        });

        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adapter);
    }

    private List<PersonModel> loadPeopleFromJson() {
        Gson gson = new Gson();
        InputStream inputStream = getResources().openRawResource(R.raw.peoplelist);

        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (jsonObject.has("people") && jsonObject.get("people").isJsonArray()) {
                    JsonArray peopleArray = jsonObject.getAsJsonArray("people");
                    return gson.fromJson(peopleArray, new TypeToken<List<PersonModel>>() {}.getType());
                } else {
                    // Handle case where "people" key is missing or not an array
                    return Collections.emptyList();
                }
            } else {
                // Handle other cases
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PERSON_DETAILS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Check the action and update the RecyclerView accordingly
                String action = data.getStringExtra("action");
                if ("delete".equals(action)) {
                    String deletedUserId = data.getStringExtra("deletedUserId");
                    // Remove the user with this ID from your RecyclerView adapter's dataset
                    adapter.removeUser(deletedUserId);
                }
            }
        }
    }
}