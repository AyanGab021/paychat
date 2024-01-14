package com.example.raviz_1_13_2024_exam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class friendlist extends AppCompatActivity {
    TextView txt;
    private RecyclerView recViews;
    private List<PersonModel> people;

    Button add;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);

        recViews = findViewById(R.id.friendView);
        add = findViewById(R.id.buttons);
        people = loadFriendListFromJson(3130);

        // Set up the RecyclerView adapter
        PersonAdapter adapter = new PersonAdapter(people, new PersonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PersonModel person) {
                String details = "Friend Name: " + person.getFirst_name() +
                        "\nFriend Email: " + person.getEmail();
                Toast.makeText(friendlist.this, details, Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate the modal layout
                View modalView = getLayoutInflater().inflate(R.layout.addfriend, null);

                // Create a dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(friendlist.this);
                builder.setView(modalView);

                // Get references to the views in the modal layout
                EditText fNameText = modalView.findViewById(R.id.fNameTxt);
                EditText lNameText = modalView.findViewById(R.id.lNameTxt);
                EditText numTxt = modalView.findViewById(R.id.numTxt);
                EditText ageTxt = modalView.findViewById(R.id.agetTxt);
                EditText emailTxt = modalView.findViewById(R.id.emailTxt);
                EditText addressTxt = modalView.findViewById(R.id.addressTxt);
                Button addFriendBtn = modalView.findViewById(R.id.addF);

                // Create a dialog and show it
                AlertDialog dialog = builder.create();
                dialog.show();

                // Add TextWatcher to the numTxt EditText
                numTxt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // Not needed
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // Not needed
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        // Check the length of the input
                        if (editable.length() != 11) {
                            // Change text color to red
                            numTxt.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        } else {
                            // Reset text color
                            numTxt.setTextColor(getResources().getColor(android.R.color.black));
                        }
                    }
                });

                emailTxt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // Not needed
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // Not needed
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        // Check if the entered email is a valid email address
                        if (!isValidEmail(editable.toString())) {
                            // Change text color to red
                            emailTxt.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        } else {
                            // Reset text color
                            emailTxt.setTextColor(getResources().getColor(android.R.color.black));
                        }
                    }
                });

                // Set onClickListener for the "Add Friend" button in the modal
                addFriendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get values from the modal
                        String fName = fNameText.getText().toString();
                        String lName = lNameText.getText().toString();
                        String num = numTxt.getText().toString();
                        String age = ageTxt.getText().toString();
                        String email = emailTxt.getText().toString();
                        String address = addressTxt.getText().toString();

                        // Check the length of the phone number
                        if (num.length() != 11) {
                            Toast.makeText(friendlist.this, "Phone number must be 11 characters", Toast.LENGTH_SHORT).show();
                            return; // Do not proceed with adding the friend
                        }

                        // Create a new PersonModel
                        PersonModel newFriend = new PersonModel("1", fName, lName, num, Integer.parseInt(age), address, email);

                        // Add the new friend
                        addFriend(newFriend);

                        // Update the RecyclerView adapter with the new data
                        adapter.updateData(people);

                        // Save the updated list to the JSON file
                        saveFriendListToJson(people);

                        // Dismiss the dialog
                        dialog.dismiss();

                        Toast.makeText(friendlist.this, "Friend Added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        recViews.setLayoutManager(new LinearLayoutManager(this));
        recViews.setAdapter(adapter);
    }

    void addFriend(PersonModel newFriend) {
        // Add the new friend to the existing list
        people.add(newFriend);

        // Save the updated list to the JSON file
        saveFriendListToJson(people);

        // Notify the adapter that the data set has changed
        recViews.getAdapter().notifyItemInserted(people.size() - 1);
    }

    private List<PersonModel> loadFriendListFromJson(int personId) {
        Gson gson = new Gson();
        InputStream inputStream = getResources().openRawResource(R.raw.peoplelist);

        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (jsonObject.has("friendlist") && jsonObject.get("friendlist").isJsonArray()) {
                    JsonArray friendListArray = jsonObject.getAsJsonArray("friendlist");

                    for (JsonElement friendElement : friendListArray) {
                        JsonObject friendObject = friendElement.getAsJsonObject();
                        if (friendObject.has("person_id") && friendObject.get("person_id").getAsInt() == personId) {
                            JsonArray friendsArray = friendObject.getAsJsonArray("friends");

                            // Convert friends to a list of PersonModel objects
                            return gson.fromJson(friendsArray, new TypeToken<List<PersonModel>>() {
                            }.getType());
                        }
                    }
                }
            }

            // Handle case where friend list is not found or person_id is not present
            return Collections.emptyList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void saveFriendListToJson(List<PersonModel> updatedList) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(updatedList);

        if (isExternalStorageWritable()) {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, "peoplelist.json");

            // Write JSON data to the file
            writeJsonToFile(file, jsonString);
        } else {
            // Handle the case where external storage is not available
        }
    }


    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private String loadOriginalJsonFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getResources().openRawResource(R.raw.peoplelist);
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            int data;
            while ((data = reader.read()) != -1) {
                stringBuilder.append((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void writeJsonToFile(File file, String jsonString) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
