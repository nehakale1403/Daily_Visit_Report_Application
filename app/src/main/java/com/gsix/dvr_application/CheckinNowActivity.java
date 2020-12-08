package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CheckinNowActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST = 100;
    private TextView Pinglocation;
    AlertDialog.Builder build;
    private GpsTracker gpsTracker;
    private ProgressDialog loadingbar;
    private ImageView checkboxgreen;
    private Spinner customertype, visitpurpose;
    private String custtypestr, visitpurposestr, saveCurrentDate, saveCurrentTime, productRandomKey, CurrentUserId,
            companyid, tvLatitude, checkincount, tvLongitude, CustomerName, VisitDetail;
    private boolean locping = true;
    private EditText editname, editvisitdetail;
    private Button Submit, Clear;
    private ProgressDialog loadingBar;
    private DatabaseReference dataref, databaseReference, RootReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_now);
        Pinglocation = (TextView) findViewById(R.id.pinglocation);
        checkboxgreen = (ImageView) findViewById(R.id.checkboxgreen);
        customertype = (Spinner) findViewById(R.id.spinnercustomertype);
        editname = (EditText) findViewById(R.id.editname);
        editvisitdetail = (EditText) findViewById(R.id.editvisdetail);
        Submit = (Button) findViewById(R.id.submitbtn);
        Clear = (Button) findViewById(R.id.clearbtn);
        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();
        RootReference = FirebaseDatabase.getInstance().getReference();
        loadingBar = new ProgressDialog(this);
        dataref = FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUserId).child("checkins");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final String[] VisitPur = {"Select", "Meeting", "Appointment", "Payment Collection", "Product Approval", "Customer Complaint", "Sales Order"};
        final String[] CustType = {"Select", "Dealer", "Distributor", "Contractor", "Channel Partner", "OEM"};

        visitpurpose = (Spinner) findViewById(R.id.spinnervisitpurp);
        build = new AlertDialog.Builder(this);
        loadingbar = new ProgressDialog(this);
        Pinglocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CheckinNowActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                        loadingbar.setTitle("Please Wait....");
                        loadingbar.setCanceledOnTouchOutside(false);
                        loadingbar.show();
                        gpsTracker = new GpsTracker(CheckinNowActivity.this);
                        if (gpsTracker.canGetLocation()) {
                            double latitude = gpsTracker.getLatitude();
                            double longitude = gpsTracker.getLongitude();
                            tvLatitude = String.valueOf(latitude);
                            tvLongitude = String.valueOf(longitude);
                            checkboxgreen.setVisibility(View.VISIBLE);
                            locping = false;
                            loadingbar.dismiss();
                        } else {
                            loadingbar.dismiss();
                            gpsTracker.showSettingsAlert();
                        }
                    }
                    else{
                        loadingbar.setTitle("Please Wait....");
                        loadingbar.setCanceledOnTouchOutside(false);
                        loadingbar.show();
                        gpsTracker = new GpsTracker(CheckinNowActivity.this);
                        if (gpsTracker.canGetLocation()) {
                            double latitude = gpsTracker.getLatitude();
                            double longitude = gpsTracker.getLongitude();
                            tvLatitude = String.valueOf(latitude);
                            tvLongitude = String.valueOf(longitude);
                            checkboxgreen.setVisibility(View.VISIBLE);
                            locping = false;
                            loadingbar.dismiss();
                        } else {
                            loadingbar.dismiss();
                            gpsTracker.showSettingsAlert();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        customertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                custtypestr = CustType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        visitpurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                visitpurposestr = VisitPur[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter a1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CustType);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customertype.setAdapter(a1);

        ArrayAdapter a2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, VisitPur);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        visitpurpose.setAdapter(a2);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerName = editname.getText().toString();
                VisitDetail = editvisitdetail.getText().toString();
                if (CustomerName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the customer name.", Toast.LENGTH_SHORT).show();
                } else {
                    if (custtypestr.equals("Select")) {
                        Toast.makeText(getApplicationContext(), "Please select customer type.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (visitpurposestr.equals("Select")) {
                            Toast.makeText(getApplicationContext(), "Please select visit purpose.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (locping) {
                                Toast.makeText(getApplicationContext(), "Please ping your location first.", Toast.LENGTH_SHORT).show();
                            } else {
                                loadingBar.setTitle("Please wait...");
                                loadingBar.setCanceledOnTouchOutside(false);
                                loadingBar.show();
                                Calendar calendar = Calendar.getInstance();

                                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                                saveCurrentDate = currentDate.format(calendar.getTime());

                                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                                saveCurrentTime = currentTime.format(calendar.getTime());
                                productRandomKey = saveCurrentDate + saveCurrentTime;
                                SaveOrderIntoDatabase();
                            }
                        }
                    }
                }

            }
        });

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editname.setText("");
                editvisitdetail.setText("");
                ArrayAdapter a1 = new ArrayAdapter(CheckinNowActivity.this, android.R.layout.simple_spinner_item, CustType);
                a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                customertype.setAdapter(a1);

                ArrayAdapter a2 = new ArrayAdapter(CheckinNowActivity.this, android.R.layout.simple_spinner_item, VisitPur);
                a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                visitpurpose.setAdapter(a2);
            }
        });
    }

    private void SaveOrderIntoDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("customerName", CustomerName);
        productMap.put("customerType", custtypestr);
        productMap.put("visitPurpose", visitpurposestr);
        productMap.put("visitDetail", VisitDetail);
        productMap.put("longitude", tvLongitude);
        productMap.put("latitude", tvLatitude);

        dataref.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            int checkincnti = Integer.parseInt(checkincount);
                            checkincnti++;
                            int valuei = 9999 - checkincnti;
                            int checkinPercent=0;
                            if(checkincnti>20)
                            {
                                checkinPercent=100;
                            }
                            else{
                                checkinPercent=checkincnti*5;
                            }

                            HashMap<String, Object> productMap1 = new HashMap<>();
                            productMap1.put("totalcheckin", String.valueOf(checkincnti));
                            productMap1.put("value", String.valueOf(valuei));
                            productMap1.put("checkinPercent",String.valueOf(checkinPercent));

                            databaseReference.child("Company").child(companyid).child("totalcheck").
                                    child(CurrentUserId).updateChildren(productMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(CheckinNowActivity.this, "Check-in added.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    } else {
                                        loadingBar.dismiss();
                                        String message = task.getException().toString();
                                        Toast.makeText(CheckinNowActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        } else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(CheckinNowActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.child("users").child(CurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChild("CompanyId")) {
                    companyid = snapshot.child("CompanyId").getValue().toString();
                    RootReference.child("Company").child(companyid).child("totalcheck").
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        checkincount = snapshot.child(CurrentUserId).child("totalcheckin").getValue().toString();
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
