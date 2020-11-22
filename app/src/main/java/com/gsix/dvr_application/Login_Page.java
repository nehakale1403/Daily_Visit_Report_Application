package com.gsix.dvr_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login_Page extends AppCompatActivity {
        EditText email;
        EditText pass;

        Button login;
        FirebaseAuth fAuth;
        private FirebaseAuth.AuthStateListener authStateListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_page);
            email = (EditText) findViewById(R.id.employeeID);
            pass = (EditText) findViewById(R.id.PasswordID);
            login = (Button) findViewById(R.id.loginbutton);
            fAuth= FirebaseAuth.getInstance();

            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser mfirebaseuser = fAuth.getCurrentUser();
                    if (mfirebaseuser != null) {
                        Toast.makeText(Login_Page.this, "logged in", Toast.LENGTH_SHORT).show();
                        Intent I = new Intent(Login_Page.this, MainActivity.class);
                        startActivity(I);
                    }
                    else
                        {
                        Toast.makeText(Login_Page.this, "Please LogIn", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mail = email.getText().toString();
                    String Password = pass.getText().toString();


                    if (mail.isEmpty()) {
                        email.setError("ID not mentioned");
                        email.requestFocus();
                    } else if (Password.isEmpty()) {
                        pass.setError("Please enter password");
                        pass.requestFocus();
                    } else if (mail.isEmpty() && Password.isEmpty()) {
                        Toast.makeText(Login_Page.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    } else {
                        fAuth.signInWithEmailAndPassword(mail, Password).addOnCompleteListener(Login_Page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Login_Page.this, "Login error", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intHome = new Intent(Login_Page.this, MainActivity.class);
                                    startActivity(intHome);

                                }
                            }

                        });


                    }

                }


            });
        }
    }