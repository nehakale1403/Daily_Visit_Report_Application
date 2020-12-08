package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gsix.dvr_application.Adapter.ExpenseRecyclerAdapter;
import com.gsix.dvr_application.Adapter.PerformanceAdapter;
import com.gsix.dvr_application.Model.Checkin;
import com.gsix.dvr_application.Model.Expense;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PerformanceActivity extends AppCompatActivity {
    private TextView txtProgress;
    private ProgressBar progressBar;
    private RecyclerView recycleview;
    private PerformanceAdapter performanceAdapter;
    private List<Checkin> checkinList;
    private FirebaseAuth mAuth;
    private String CurrentUserId, CompanyId,status;
    private DatabaseReference employref, employref1,employref2;
    private int pStatus = 70;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        txtProgress = (TextView) findViewById(R.id.txtProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recycleview = (RecyclerView) findViewById(R.id.recyclerranking);
        employref = FirebaseDatabase.getInstance().getReference();
        employref2 = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();

        employref.keepSynced(true);

        checkinList = new ArrayList<>();
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.setHasFixedSize(true);
        performanceAdapter = new PerformanceAdapter(PerformanceActivity.this,
                checkinList);
        recycleview.setAdapter(performanceAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        employref.child("users").child(CurrentUserId).child("CompanyId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    CompanyId=snapshot.getValue().toString();
                }
                employref2.child("Company").child(CompanyId).child("totalcheck").child(CurrentUserId).child("checkinPercent").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            status=snapshot.getValue().toString();
                            pStatus=Integer.parseInt(status);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                employref1 = FirebaseDatabase.getInstance().getReference().child("Company").child(CompanyId).child("totalcheck");
                Query querydesc = employref1.orderByChild("value");
                querydesc.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Checkin checkin = snapshot.getValue(Checkin.class);
                        checkinList.add(checkin);
                        performanceAdapter.notifyDataSetChanged();

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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(pStatus);
                            txtProgress.setText(pStatus + "%");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //pStatus++;
                }
            }
        }).start();

    }
}
