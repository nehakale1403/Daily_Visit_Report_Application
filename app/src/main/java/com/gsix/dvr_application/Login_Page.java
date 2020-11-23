package com.gsix.dvr_application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login_Page extends AppCompatActivity {
        private TextView btncompany;
        EditText email;
        EditText pass;

        Button login;
        FirebaseAuth fAuth;
        private FirebaseAuth.AuthStateListener authStateListener;
        DatabaseReference rootref;
    private ProgressDialog loadingbar;
    AlertDialog.Builder build;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_page);
            getSupportActionBar().hide();
            email = (EditText) findViewById(R.id.employeeID);
            pass = (EditText) findViewById(R.id.PasswordID);
            login = (Button) findViewById(R.id.loginbutton);
            btncompany =(TextView) findViewById(R.id.btncompany);
            rootref= FirebaseDatabase.getInstance().getReference();
            build = new AlertDialog.Builder(this);
            loadingbar = new ProgressDialog(this);
            fAuth= FirebaseAuth.getInstance();

            btncompany.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),CompanyLogin.class);
                    startActivity(intent);
                }
            });
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
                        loadingbar.setTitle("Please Wait....");
                        loadingbar.setCanceledOnTouchOutside(false);
                        loadingbar.show();
                        fAuth.signInWithEmailAndPassword(mail, Password).addOnCompleteListener(Login_Page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Login_Page.this, "Login error", Toast.LENGTH_SHORT).show();
                                } else {
                                    String Currentuserid=fAuth.getCurrentUser().getUid();
                                    rootref.child("users").child(Currentuserid).child("type").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if((snapshot.exists()))
                                            {
                                                String type=snapshot.getValue().toString();
                                                if(type.equals("employee")) {
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                    loadingbar.dismiss();
                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(),"Try to login in Company section.",Toast.LENGTH_SHORT).show();
                                                    loadingbar.dismiss();
                                                }
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(),"Login Error.",Toast.LENGTH_SHORT).show();
                                                loadingbar.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    loadingbar.dismiss();

                                }
                            }

                        });


                    }

                }


            });
        }

    }