package com.gsix.dvr_application.ui.mycheckins;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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
import com.gsix.dvr_application.CheckinNowActivity;
import com.gsix.dvr_application.Model.Mycheckins;
import com.gsix.dvr_application.R;

import java.util.ArrayList;
import java.util.List;

public class MyCheckinsFragment extends Fragment {

    private DatabaseReference mDatabaseReference;
    private StorageReference mStorage;
    private RecyclerView recyclerView;
    private MyCheckinsAdapter myCheckinsAdapter;
    private List<Mycheckins> mycheckinsList;
    private CardView top_card_mycheckins;

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycheckins, container, false);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mDatabase=FirebaseDatabase.getInstance();
        String userid= mUser.getUid();
        Log.d("UID: ", userid);
        mDatabaseReference=mDatabase.getReference().child("users").child(userid);
        mDatabaseReference.keepSynced(true);
        mycheckinsList=new ArrayList<>();



        top_card_mycheckins = (CardView) view.findViewById(R.id.topp_card_mycheckins);
        recyclerView=(RecyclerView) view.findViewById(R.id.mycheckin_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        myCheckinsAdapter =new MyCheckinsAdapter(MyCheckinsFragment.this,mycheckinsList);

       return view;
    }

    @Override
    public void onStart() {
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