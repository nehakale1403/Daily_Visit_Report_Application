package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gsix.dvr_application.Adapter.ExpenseRecyclerAdapter;
import com.gsix.dvr_application.Model.Expense;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ExpensesAndBillsActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference, databaseReferenceAmount;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private TextView total_expenditure, total_count;
    private RecyclerView recyclerView;
    private ExpenseRecyclerAdapter expenseRecyclerAdapter;
    private AddExpenseActivity addExpenseActivity;
    private List<Expense> expenseList;
    private FloatingActionButton add_exp_btn;
    private Double total_exp = 0.0;
    private int total_cnt=0;

    private String userid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_and_bills);

        recyclerView =(RecyclerView) findViewById(R.id.id_recycler_view);
        total_expenditure = (TextView) findViewById(R.id.id_total_expenses);
        total_count = (TextView) findViewById(R.id.id_total_count);
        add_exp_btn = (FloatingActionButton) findViewById(R.id.id_fab_expenses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userid = mUser.getUid();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("Expense").child(userid); //getting the link of our database
        mDatabaseReference.keepSynced(true);

        expenseList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseRecyclerAdapter = new ExpenseRecyclerAdapter(ExpensesAndBillsActivity.this,
                expenseList);
        recyclerView.setAdapter(expenseRecyclerAdapter);

        add_exp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ExpensesAndBillsActivity.this, AddExpenseActivity.class));
                finish(); //it doesn't stack up previous activities
            }
        });

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){

                    String amt = ds.child("amount").getValue(String.class);
                    total_exp = total_exp + Double.valueOf(amt);
                    total_expenditure.setText("Total Expenditure: "+String.valueOf(total_exp));

                    total_cnt+=1;
                    total_count.setText("Total Count: "+ String.valueOf(total_cnt));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Expense expense = snapshot.getValue(Expense.class);
                expenseList.add(expense);

                Collections.reverse(expenseList);
                expenseRecyclerAdapter.notifyDataSetChanged();

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
    protected void onStop() {
        super.onStop();
    }
}
