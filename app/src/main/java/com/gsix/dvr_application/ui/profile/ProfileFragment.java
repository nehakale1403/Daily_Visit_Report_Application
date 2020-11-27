package com.gsix.dvr_application.ui.profile;


        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;


        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;



        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.gsix.dvr_application.R;

public class ProfileFragment extends Fragment {

    private TextView firstnametext, lastnametext, emailtext, mobiletext, empIDtext, addresstext;
    private final String TAG = this.getClass().getName().toUpperCase();
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;
    private String onlineCompanyID;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();

        onlineCompanyID = FirebaseDatabase.getInstance().getReference().child("users").child(onlineUserID).child("CompanyId").toString();
        reference = FirebaseDatabase.getInstance().getReference().child("Company").child(onlineCompanyID).child("Employees").child(onlineUserID).child("details");


        firstnametext = view.findViewById(R.id.profilefirstname);
        lastnametext = view.findViewById(R.id.profilelastname);
        emailtext = view.findViewById(R.id.profileemail);
        mobiletext = view.findViewById(R.id.profilemobile);
        empIDtext = view.findViewById(R.id.profileempID);
        addresstext = view.findViewById(R.id.profileaddress);



        //To read from firebase database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        firstnametext.setText(ds.child("firstname").getValue(String.class));
                        lastnametext.setText(ds.child("lastname").getValue(String.class));
                        emailtext.setText(ds.child("email").getValue(String.class));
                        mobiletext.setText(ds.child("mobile").getValue(String.class));
                        empIDtext.setText(ds.child("empID").getValue(String.class));
                        addresstext.setText(ds.child("address").getValue(String.class));
                        break;

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });



        return view;
    }


}