package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompanyRegister extends AppCompatActivity {
    private TextView alreadylogin;
    private EditText compemail1, comppassword, compconfpassword;
    private Button signIn;
    FirebaseAuth mAuth;
    DatabaseReference Rootreference;
    ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);
        getSupportActionBar().hide();
        alreadylogin=(TextView)findViewById(R.id.textViewalreadylogin);
        compemail1=(EditText)findViewById(R.id.compemail1);
        comppassword=(EditText)findViewById(R.id.comppassword1);
        compconfpassword=(EditText)findViewById(R.id.comppassword2);
        signIn=(Button)findViewById(R.id.buttonregister);
        mAuth=FirebaseAuth.getInstance();
        Rootreference= FirebaseDatabase.getInstance().getReference();
        loadingbar=new ProgressDialog(this);
        alreadylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CompanyLogin.class);
                startActivity(intent);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginactivity();
            }
        });
    }

    private void loginactivity() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        int str_pwd = comppassword.getText().toString().length();
        final String email = compemail1.getText().toString();
        final String password = comppassword.getText().toString();
        final String confpassword = compconfpassword.getText().toString();

        if(email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter the email address.", Toast.LENGTH_SHORT).show();
        }
        else{
            if (password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter the Password.", Toast.LENGTH_SHORT).show();
            } else {
                if (!password.equals(confpassword)) {
                    Toast.makeText(getApplicationContext(), "Password must be same.", Toast.LENGTH_SHORT).show();
                } else {
                    if (!compemail1.getText().toString().trim().matches(emailPattern)) {
                        Toast.makeText(getApplicationContext(), "Please enter valid email address", Toast.LENGTH_SHORT).show();
                    } else {
                        if (str_pwd < 6) {
                            Toast.makeText(getApplicationContext(), "Please enter at least 6 digit password", Toast.LENGTH_SHORT).show();
                        } else {
                                loadingbar.setTitle("Please wait while we are registering you...");
                                loadingbar.setCanceledOnTouchOutside(false);
                                loadingbar.show();
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    String currentuserid=mAuth.getCurrentUser().getUid();
                                                    Rootreference.child("Company").child(currentuserid).child("details").child("email").setValue(email);
                                                    Rootreference.child("users").child(currentuserid).child("type").setValue("company");
                                                    Toast.makeText(getApplicationContext(),"Account created successfully",Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(getApplicationContext(), CompanyLogin.class);
                                                    startActivity(intent);
                                                    loadingbar.dismiss();
                                                } else {
                                                    String message = task.getException().toString();
                                                    Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                                    loadingbar.dismiss();
                                                }
                                            }
                                        });

                            }


                    }
                }
            }



        }}
}