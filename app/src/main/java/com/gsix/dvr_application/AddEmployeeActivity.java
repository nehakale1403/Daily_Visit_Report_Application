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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEmployeeActivity extends AppCompatActivity {

    private EditText empemail, emppassword, empconfpassword,empname, empid, empmobile, empaddress;
    private Button addemployee;
    FirebaseAuth mAuth;
    String CompUserId;
    DatabaseReference Rootreference,Rootreference2;
    ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        empname=(EditText)findViewById(R.id.employenameregister);
        empemail=(EditText)findViewById(R.id.employeeemailregister);
        empid = (EditText) findViewById(R.id.employeeidregister);
        empmobile = (EditText) findViewById(R.id.employeemobileregister);
        empaddress = (EditText) findViewById(R.id.employeaddressregister);
        emppassword=(EditText)findViewById(R.id.employeepwdregister);
        empconfpassword=(EditText)findViewById(R.id.employeeconfpwdregister);

        addemployee=(Button)findViewById(R.id.buttonaddemployee);
        mAuth=FirebaseAuth.getInstance();
        CompUserId=getIntent().getExtras().get("CompUserId").toString();
        Rootreference= FirebaseDatabase.getInstance().getReference().child("Company").child(CompUserId);
        Rootreference2= FirebaseDatabase.getInstance().getReference();
        loadingbar=new ProgressDialog(this);
        addemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addempactivity();
            }
        });
    }

    private void addempactivity() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        int str_pwd = emppassword.getText().toString().length();
        final String employeename=empname.getText().toString();
        final String email = empemail.getText().toString();
        final String mobile = empmobile.getText().toString();
        final String empID = empid.getText().toString();
        final String address = empaddress.getText().toString();

        final String password = emppassword.getText().toString();
        final String confpassword = empconfpassword.getText().toString();


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
                    if (!empemail.getText().toString().trim().matches(emailPattern)) {
                        Toast.makeText(getApplicationContext(), "Please enter valid email address", Toast.LENGTH_SHORT).show();
                    } else {
                        if (str_pwd < 6) {
                            Toast.makeText(getApplicationContext(), "Please enter at least 6 digit password", Toast.LENGTH_SHORT).show();
                        } else {
                            loadingbar.setTitle("Please wait while we are adding new employee...");
                            loadingbar.setCanceledOnTouchOutside(false);
                            loadingbar.show();
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                String currentuserid=mAuth.getCurrentUser().getUid();
                                                Rootreference.child("Employees").child(currentuserid).child("details").child("name").setValue(employeename);
                                                Rootreference.child("Employees").child(currentuserid).child("details").child("email").setValue(email);
                                                Rootreference.child("Employees").child(currentuserid).child("details").child("mobile").setValue(mobile);
                                                Rootreference.child("Employees").child(currentuserid).child("details").child("empID").setValue(empID);
                                                Rootreference.child("Employees").child(currentuserid).child("details").child("address").setValue(address);

                                                Rootreference.child("totalcheck").child(currentuserid).child("name").setValue(employeename);
                                                Rootreference.child("totalcheck").child(currentuserid).child("totalcheckin").setValue("0");
                                                Rootreference.child("totalcheck").child(currentuserid).child("value").setValue("10000");
                                                Rootreference.child("Employees").child(currentuserid).child("details").child("name").setValue(employeename);
                                                Rootreference2.child("users").child(currentuserid).child("type").setValue("employee");
                                                Rootreference2.child("users").child(currentuserid).child("CompanyId").setValue(CompUserId);
                                                Toast.makeText(getApplicationContext(),"Account created successfully",Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(getApplicationContext(), CompanyDashboard.class);
                                                intent.putExtra("CompUserId",CompUserId);
                                                startActivity(intent);
                                                finish();
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