package com.example.kwikbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminUserLogin_Activity extends AppCompatActivity {

    ImageButton userLogo, adminLogo;
    TextView hi_text, user_text, admin_text, no_ac_text, create_now_text;
    EditText login_et, pass_et;
    Button login_but;
    int flag;
    LibraryDatabaseHelper ldbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_user_login);
        userLogo = findViewById(R.id.user_login_image_button);
        adminLogo = findViewById(R.id.admin_login_Image_button);
        hi_text = findViewById(R.id.hi_id_tv);
        user_text = findViewById(R.id.user_tv);
        admin_text = findViewById(R.id.admin_tv);
        no_ac_text = findViewById(R.id.no_ac_tv);
        create_now_text = findViewById(R.id.create_now_tv);
        login_et = findViewById(R.id.login_id_et);
        pass_et = findViewById(R.id.password_et);
        login_but = findViewById(R.id.login_button);
        ldbHelper = new LibraryDatabaseHelper(this);
        SQLiteDatabase db = ldbHelper.getWritableDatabase();
        ldbHelper.generateFine(db);

        LibraryDatabaseHelper ldbHelper = new LibraryDatabaseHelper(AdminUserLogin_Activity.this);
        userLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    userLogo.setImageResource(R.drawable.user_login_logo);
                    hi_text.setVisibility(View.INVISIBLE);
                    user_text.setVisibility(View.INVISIBLE);
                    adminLogo.setVisibility(View.INVISIBLE);
                    admin_text.setVisibility(View.INVISIBLE);
                    login_et.setVisibility(View.VISIBLE);
                    pass_et.setVisibility(View.VISIBLE);
                    login_but.setVisibility(View.VISIBLE);
                    no_ac_text.setVisibility(View.VISIBLE);
                    create_now_text.setVisibility(View.VISIBLE);
                    flag = 1;

                    login_but.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String userId = login_et.getText().toString();
                            String pwd = pass_et.getText().toString();

                            if (ldbHelper.authenticateUser(userId,pwd)){
                                // Starting Intent to User's Home Page
                                Toast.makeText(AdminUserLogin_Activity.this, "Successfully Logged In!, Welcome to KwikBook", Toast.LENGTH_SHORT).show();
                                Intent loginToHome = new Intent(AdminUserLogin_Activity.this, UserHome_Activity.class);
                                loginToHome.putExtra("USERNAME",userId);
                                startActivity(loginToHome);
                            } else{
                                Toast.makeText(AdminUserLogin_Activity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    create_now_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //switching intent to sign up page
                            Intent loginToSignUp = new Intent(AdminUserLogin_Activity.this, UserSignUp_Activity.class);
                            startActivity(loginToSignUp);
                        }
                    });

                } else {
                    hi_text.setVisibility(View.VISIBLE);
                    user_text.setVisibility(View.VISIBLE);
                    adminLogo.setVisibility(View.VISIBLE);
                    admin_text.setVisibility(View.VISIBLE);
                    login_et.setVisibility(View.INVISIBLE);
                    pass_et.setVisibility(View.INVISIBLE);
                    login_but.setVisibility(View.INVISIBLE);
                    no_ac_text.setVisibility(View.INVISIBLE);
                    create_now_text.setVisibility(View.INVISIBLE);
                    userLogo.setImageResource(R.drawable.user_login_logo);
                    flag = 0;
                }
            }
        });

        adminLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    userLogo.setImageResource(R.drawable.admin_login_logo);
                    hi_text.setVisibility(View.INVISIBLE);
                    user_text.setVisibility(View.INVISIBLE);
                    userLogo.setVisibility(View.VISIBLE);
                    admin_text.setVisibility(View.INVISIBLE);
                    adminLogo.setVisibility(View.INVISIBLE);
                    login_et.setVisibility(View.VISIBLE);
                    pass_et.setVisibility(View.VISIBLE);
                    login_but.setVisibility(View.VISIBLE);
                    flag = 1;

                    login_but.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            String userId = login_et.getText().toString();
                            String pwd = pass_et.getText().toString();

                            if (userId.equals("admin") && pwd.equals("adminAccess")){
                                Toast.makeText(AdminUserLogin_Activity.this, "Successfully Logged In!", Toast.LENGTH_SHORT).show();
                                Intent loginToHome = new Intent(AdminUserLogin_Activity.this, AdminHome_Activity.class);
                                startActivity(loginToHome);
                            } else{
                                Toast.makeText(AdminUserLogin_Activity.this, "Invalid Admin Credentials !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    hi_text.setVisibility(View.VISIBLE);
                    user_text.setVisibility(View.VISIBLE);
                    userLogo.setVisibility(View.VISIBLE);
                    admin_text.setVisibility(View.VISIBLE);
                    login_et.setVisibility(View.INVISIBLE);
                    pass_et.setVisibility(View.INVISIBLE);
                    login_but.setVisibility(View.INVISIBLE);
                    adminLogo.setVisibility(View.VISIBLE);
                    userLogo.setImageResource(R.drawable.user_login_logo);
                    flag = 0;
                }
            }
        });


    }

}