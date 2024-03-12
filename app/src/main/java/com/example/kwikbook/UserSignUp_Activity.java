package com.example.kwikbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class UserSignUp_Activity extends AppCompatActivity {
    EditText signup_name, signup_email, signup_mobile, signup_password, signup_confirm_password;
    Button signup_but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_sign_up);
        signup_name = findViewById(R.id.signup_name_et);
        signup_email = findViewById(R.id.signup_email_et);
        signup_mobile = findViewById(R.id.signup_mobile_et);
        signup_password = findViewById(R.id.signup_password_et);
        signup_confirm_password = findViewById(R.id.signup_confirm_password_et);
        signup_but = findViewById(R.id.signup_button);

        signup_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signup_name.getText().length() == 0 || signup_email.getText().length() == 0 ||
                        signup_mobile.getText().length() == 0 || signup_password.getText().length() == 0 ||
                        signup_confirm_password.getText().length() == 0) {
                    Toast.makeText(UserSignUp_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String name = signup_name.getText().toString();
                    String email = signup_email.getText().toString();
                    String mobile = signup_mobile.getText().toString();
                    String password = signup_password.getText().toString();
                    String confirm_password = signup_confirm_password.getText().toString();

                    if (!password.equals(confirm_password)) {
                        Toast.makeText(UserSignUp_Activity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    } else {
                        LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(UserSignUp_Activity.this);
                        SQLiteDatabase db = ldbHelper.getWritableDatabase();

                        // Checking if the email already exists in the database
                        if (ldbHelper.doesUserExist(db, email)) {
                            Toast.makeText(UserSignUp_Activity.this, "Account with this email already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            // If email doesn't exist, proceeding with user registration
                            User newUser = new User(email, password, name, mobile);
                            ldbHelper.addUser(db, newUser);
                            Toast.makeText(UserSignUp_Activity.this, "Successfully Signed Up! Welcome to KwikBook", Toast.LENGTH_SHORT).show();
                            Intent loginToHome = new Intent(UserSignUp_Activity.this, UserHome_Activity.class);
                            startActivity(loginToHome);
                        }
                    }
                }
            }
        });
    }
}