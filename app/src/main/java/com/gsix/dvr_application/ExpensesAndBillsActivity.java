package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


public class ExpensesAndBillsActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private TextView total_expenditure, total_count;
    private RecyclerView recyclerView;
    private FloatingActionButton add_exp_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_and_bills);

        recyclerView =(RecyclerView) findViewById(R.id.id_add_expense_btn);
        total_expenditure = (TextView) findViewById(R.id.id_total_expenses);
        total_count = (TextView) findViewById(R.id.id_total_count);
        add_exp_btn = (FloatingActionButton) findViewById(R.id.id_fab_expenses);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                mAuth = firebaseAuth.getInstance();
                mUser = firebaseAuth.getCurrentUser();

                mDatabase = FirebaseDatabase.getInstance();
                mDatabaseReference = mDatabase.getReference().child("Expenses"); //getting the link of our database
                mDatabaseReference.keepSynced(true);

            }
        };

        add_exp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ExpensesAndBillsActivity.this, add_expense.class));
                finish(); //it doesn't stack up previous activities
            }
        });


    }

}
