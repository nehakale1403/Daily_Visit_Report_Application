package com.gsix.dvr_application.ui.profile;


        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import android.widget.Toast;


        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;



        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.gsix.dvr_application.R;

public class ProfileFragment extends Fragment {

    private TextView firstnametext, emailtext, mobiletext, empIDtext, addresstext;
    private final String TAG = this.getClass().getName().toUpperCase();
    private DatabaseReference reference,onlineCompanyIDref;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;
    public String onlineCompanyID;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserID = mUser.getUid();

        firstnametext = view.findViewById(R.id.profilefirstname);
        emailtext = view.findViewById(R.id.profileemail);
        mobiletext = view.findViewById(R.id.profilemobile);
        empIDtext = view.findViewById(R.id.profileempID);
        addresstext = view.findViewById(R.id.profileaddress);


        onlineCompanyIDref = FirebaseDatabase.getInstance().getReference().child("users").child(onlineUserID).child("CompanyId");


        onlineCompanyIDref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Company ID: ", snapshot.getValue().toString());
                Log.d("Company ID key: ", snapshot.getKey());
                onlineCompanyID = snapshot.getValue().toString();
                reference = FirebaseDatabase.getInstance().getReference().child("Company")
                        .child(onlineCompanyID).child("Employees").child(onlineUserID).child("details");



                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            firstnametext.setText(snapshot.child("name").getValue(String.class));
                            emailtext.setText(snapshot.child("email").getValue(String.class));
                            mobiletext.setText(snapshot.child("mobile").getValue(String.class));
                            empIDtext.setText(snapshot.child("empID").getValue(String.class));
                            addresstext.setText(snapshot.child("address").getValue(String.class));
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


        firstnametext = view.findViewById(R.id.profilefirstname);
        emailtext = view.findViewById(R.id.profileemail);
        mobiletext = view.findViewById(R.id.profilemobile);
        empIDtext = view.findViewById(R.id.profileempID);
        addresstext = view.findViewById(R.id.profileaddress);

        return view;
    }


}