package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CompanyDashboard extends AppCompatActivity {
    private Button addemployee;
    private FirebaseAuth mAuth;
    FirebaseUser CompUser;
    String CompUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_dashboard);
        addemployee=(Button)findViewById(R.id.addemployee);
        mAuth=FirebaseAuth.getInstance();
        CompUser=mAuth.getCurrentUser();
        CompUserId=mAuth.getCurrentUser().getUid();
        addemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AddEmployeeActivity.class);
                intent.putExtra("CompUserId", CompUserId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.logout)
         {
             mAuth.signOut();
             Intent intent = new Intent(getApplicationContext(), CompanyLogin.class);
             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(intent);
             finish();
         }
         return true;
    }
}