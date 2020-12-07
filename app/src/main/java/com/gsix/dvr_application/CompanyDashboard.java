package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gsix.dvr_application.Adapter.EmployeeAdapter;
import com.gsix.dvr_application.Adapter.PerformanceAdapter;
import com.gsix.dvr_application.Model.Checkin;

import java.util.ArrayList;
import java.util.List;

public class CompanyDashboard extends AppCompatActivity {
    private Button addemployee;
    private FirebaseAuth mAuth;
    FirebaseUser CompUser;
    RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private List<Checkin> checkinList;
    private DatabaseReference employref;
    String CompUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_dashboard);
        addemployee=(Button)findViewById(R.id.addemployee);
        recyclerView=(RecyclerView)findViewById(R.id.recyleremplist);
        mAuth=FirebaseAuth.getInstance();
        CompUser=mAuth.getCurrentUser();
        CompUserId=getIntent().getExtras().get("CompUserId").toString();
        employref = FirebaseDatabase.getInstance().getReference().child("Company").child(CompUserId).child("totalcheck");
        employref.keepSynced(true);

        checkinList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        employeeAdapter = new EmployeeAdapter(CompanyDashboard.this,
                checkinList);
        recyclerView.setAdapter(employeeAdapter);
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

    @Override
    protected void onStart() {
        super.onStart();

        employref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Checkin checkin = snapshot.getValue(Checkin.class);
                checkinList.add(checkin);
                employeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}