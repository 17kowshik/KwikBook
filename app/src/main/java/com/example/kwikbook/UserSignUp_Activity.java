package com.example.kwikbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                // write code to store details in the database
            }
        });
    }
}