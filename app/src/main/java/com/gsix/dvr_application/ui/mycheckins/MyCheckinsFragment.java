package com.gsix.dvr_application.ui.mycheckins;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gsix.dvr_application.Adapter.MyCheckinsAdapter;
import com.gsix.dvr_application.CheckinNowActivity;
import com.gsix.dvr_application.Model.Mycheckins;
import com.gsix.dvr_application.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyCheckinsFragment extends Fragment {
    private View view;
    private TextView total_checkins;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference, total_checkins_ref,userref;
    private StorageReference mStorage;
    private RecyclerView recyclerView;
    private MyCheckinsAdapter myCheckinsAdapter;
    private List<Mycheckins> mycheckinsList;
    private CardView top_card_mycheckins;
    private String userid,companyid;
    private String hashmap;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mycheckins, container, false);
        top_card_mycheckins = (CardView) view.findViewById(R.id.topp_card_mycheckins);
        recyclerView=(RecyclerView) view.findViewById(R.id.mycheckin_recycler_view);
        total_checkins= (TextView) view.findViewById(R.id.total_checkins_id);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mDatabase=FirebaseDatabase.getInstance();
        userid= mUser.getUid();
        userref=mDatabase.getReference().child("users").child(userid);

        mDatabaseReference=mDatabase.getReference().child("users").child(userid).child("checkins");
        total_checkins_ref=mDatabase.getReference();
        mDatabaseReference.keepSynced(true);

        mycheckinsList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        myCheckinsAdapter =new MyCheckinsAdapter(MyCheckinsFragment.this,mycheckinsList);
        recyclerView.setAdapter(myCheckinsAdapter);

       return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()) {
                    Mycheckins mycheckins = snapshot.getValue(Mycheckins.class);
                    mycheckinsList.add(mycheckins);

                    Collections.reverse(mycheckinsList);
                    myCheckinsAdapter.notifyDataSetChanged();
                }


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
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    companyid=snapshot.child("CompanyId").getValue().toString();
                    total_checkins_ref.child("Company").child(companyid)
                            .child("totalcheck").child(userid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                String name = snapshot.child("totalcheckin").getValue().toString();
                                total_checkins.setText("Total checkin: "+name);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}