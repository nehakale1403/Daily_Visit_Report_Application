package com.gsix.dvr_application.ui.profile;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.Nullable;
        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.Observer;
        import androidx.lifecycle.ViewModelProviders;


        /*import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;*/
        import com.gsix.dvr_application.R;

public class ProfileFragment extends Fragment {

   /* private TextView firstnametext, lastnametext, emailtext, mobiletext, empIDtext, addresstext;
    private final String TAG = this.getClass().getName().toUpperCase();
    private String email;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String USERS = "users";*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //receive data from login activity
        /*Intent intent = getActivity().getIntent();
        email = intent.getStringExtra("email");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID",userRef.getKey());

        firstnametext = view.findViewById(R.id.profilefirstname);
        lastnametext = view.findViewById(R.id.profilelastname);
        emailtext = view.findViewById(R.id.profileemail);
        mobiletext = view.findViewById(R.id.profilemobile);
        empIDtext = view.findViewById(R.id.profileempID);
        addresstext = view.findViewById(R.id.profileaddress);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USERS);

        //To read from firebase database
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.child("email").getValue().equals(email))  {
                        firstnametext.setText(ds.child("firstname").getValue(String.class));
                        lastnametext.setText(ds.child("lastname").getValue(String.class));
                        emailtext.setText(email);
                        mobiletext.setText(ds.child("mobile").getValue(String.class));
                        empIDtext.setText(ds.child("empID").getValue(String.class));
                        addresstext.setText(ds.child("address").getValue(String.class));
                        break;
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });*/



        return view;
    }


}