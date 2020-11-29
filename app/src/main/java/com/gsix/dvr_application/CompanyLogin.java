package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

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

import org.w3c.dom.Text;

public class CompanyLogin extends AppCompatActivity {
    private TextView textViewalready,textViewemployeelogin;
    private Button ButtonLogin;
    private EditText compemail,comppassword;
    AlertDialog.Builder build;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    private FirebaseUser currentuser;
    private DatabaseReference rootref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        textViewalready=(TextView)findViewById(R.id.textViewalready);
        textViewemployeelogin=(TextView)findViewById(R.id.textViewemployeelogin);
        ButtonLogin=(Button)findViewById(R.id.buttonlogin);
        compemail=(EditText)findViewById(R.id.compemail);
        comppassword=(EditText)findViewById(R.id.comppassword);
        build = new AlertDialog.Builder(this);
        loadingbar = new ProgressDialog(this);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        currentuser=mAuth.getCurrentUser();
        rootref= FirebaseDatabase.getInstance().getReference();
        textViewemployeelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Login_Page.class);
                startActivity(intent);
            }
        });
        textViewalready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CompanyRegister.class);
                startActivity(intent);
                finish();
            }
        });
        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companylogin();
            }
        });
    }

    public void companylogin() {
        int str = comppassword.getText().toString().length();
        String email = compemail.getText().toString();
        String password = comppassword.getText().toString();


        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter the email address", Toast.LENGTH_SHORT).show();
        } else {
            if (password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_SHORT).show();
            } else {
                if (!compemail.getText().toString().trim().matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Please enter valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    if (str < 6) {
                        Toast.makeText(getApplicationContext(), "Please enter at least 6 digit password", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingbar.setTitle("Please Wait....");
                        loadingbar.setCanceledOnTouchOutside(false);
                        loadingbar.show();
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            final String Currentuserid=mAuth.getCurrentUser().getUid();
                                            rootref.child("users").child(Currentuserid).child("type").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if((snapshot.exists()))
                                                    {
                                                        String type=snapshot.getValue().toString();
                                                        if(type.equals("company")) {
                                                            Intent intent = new Intent(getApplicationContext(), CompanyDashboard.class);
                                                            intent.putExtra("CompUserId",Currentuserid);
                                                            startActivity(intent);
                                                            finish();
                                                            loadingbar.dismiss();
                                                        }
                                                        else{
                                                            Toast.makeText(getApplicationContext(),"Try to login in employee section.",Toast.LENGTH_SHORT).show();
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
    }
}