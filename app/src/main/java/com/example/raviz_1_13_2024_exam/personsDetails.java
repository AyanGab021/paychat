package com.example.raviz_1_13_2024_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class personsDetails extends AppCompatActivity {
    TextView fullName, Email, Number, addres;
    ImageView backButton;
    String fname, lName, num, age, em, add, id;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatButton;
    private boolean isEditMode = false;

    friendlist friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons_details);

        friend = new friendlist();

        Intent intent = getIntent();
        fname = intent.getStringExtra("firtname");
        lName = intent.getStringExtra("lastname");
        num = intent.getStringExtra("number");
        age = intent.getStringExtra("age");
        em = intent.getStringExtra("email");
        add = intent.getStringExtra("add");
        id = intent.getStringExtra("id");
        backButton = findViewById(R.id.backs);

        fullName = findViewById(R.id.fullname);
        Email = findViewById(R.id.gmails);
        Number = findViewById(R.id.phones);
        addres = findViewById(R.id.address);
        floatButton = findViewById(R.id.floatingActionButton);
        bottomNavigationView = findViewById(R.id.editmenu);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.edit) {
                    // Toggle the edit state
                    isEditMode = !isEditMode;

                    // Update the layout based on the edit state
                    View modalView = getLayoutInflater().inflate(R.layout.addfriend, null);

                    // Create a dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(personsDetails.this);
                    builder.setView(modalView);

                    // Get references to the views in the modal layout
                    EditText fNameText = modalView.findViewById(R.id.fNameTxt);
                    EditText lNameText = modalView.findViewById(R.id.lNameTxt);
                    EditText numTxt = modalView.findViewById(R.id.numTxt);
                    EditText ageTxt = modalView.findViewById(R.id.agetTxt);
                    EditText emailTxt = modalView.findViewById(R.id.emailTxt);
                    EditText addressTxt = modalView.findViewById(R.id.addressTxt);
                    Button addFriendBtn = modalView.findViewById(R.id.addF);

                    addFriendBtn.setText("Save");

                    // Create a dialog and show it
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    fNameText.setText(fname);
                    lNameText.setText(lName);
                    numTxt.setText(num);
                    ageTxt.setText(age);
                    emailTxt.setText(em);
                    addressTxt.setText(add);

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

                    addFriendBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String fNames = fNameText.getText().toString();
                            String lNames = lNameText.getText().toString();
                            String nums = numTxt.getText().toString();
                            String ages = ageTxt.getText().toString();
                            String emails = emailTxt.getText().toString();
                            String address = addressTxt.getText().toString();

                            // Check if any of the fields is empty
                            if (fNames.isEmpty() || lNames.isEmpty() || nums.isEmpty() || ages.isEmpty() || emails.isEmpty() || address.isEmpty()) {
                                Toast.makeText(personsDetails.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Check if the phone number is 11 characters
                            if (nums.length() != 11) {
                                Toast.makeText(personsDetails.this, "Phone number must be 11 characters", Toast.LENGTH_SHORT).show();
                                return; // Do not proceed with adding the friend
                            }

                            // Update the UI elements
                            fullName.setText(fNames + " " + lNames);
                            Email.setText(emails);
                            Number.setText(nums);
                            addres.setText(address);

                            // Dismiss the dialog after saving
                            dialog.dismiss();
                        }
                    });

                    return true;
                } else if (item.getItemId() == R.id.delete) {
                    // Prepare the result intent with necessary data
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("action", "delete");
                    resultIntent.putExtra("deletedUserId", id);

                    // Set the result and finish the activity
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                    return true;
                }
                // Add else-if or else blocks for other menu items as needed
                return false;
            }
        });

        fullName.setText(fname + " " + lName);
        Email.setText(em);
        Number.setText(num);
        addres.setText(add);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), exam1.class);
                startActivity(i);
            }
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), friendlist.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });
    }

    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
