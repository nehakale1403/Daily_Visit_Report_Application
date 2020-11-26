package com.gsix.dvr_application;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gsix.dvr_application.Adapter.MyCheckinsAdapter;
import com.gsix.dvr_application.Model.Expense;
import com.gsix.dvr_application.Model.Mycheckins;

import java.util.ArrayList;
import java.util.List;

public class AddMycheckins  extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorage;
    private RecyclerView recyclerView;
    private MyCheckinsAdapter myCheckinsAdapter;
    private List<Mycheckins> mycheckinsList;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_now);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mDatabase=FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabaseReference=mDatabase.getReference().child("Mycheckins");
        mDatabaseReference.keepSynced(true);
        mycheckinsList=new ArrayList<>();

        recyclerView=(RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myCheckinsAdapter =new MyCheckinsAdapter(AddMycheckins.this,mycheckinsList);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Mycheckins mycheckins = snapshot.getValue(Mycheckins.class);
                mycheckinsList.add(mycheckins);

            myCheckinsAdapter.notifyDataSetChanged();


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
