package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import com.gsix.dvr_application.Adapter.ExpenseRecyclerAdapter;
import com.gsix.dvr_application.Model.Expense;

import java.util.ArrayList;
import java.util.List;


public class ExpensesAndBillsActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private TextView total_expenditure, total_count;
    private RecyclerView recyclerView;
    private ExpenseRecyclerAdapter expenseRecyclerAdapter;
    private List<Expense> expenseList;
    private FloatingActionButton add_exp_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_and_bills);

        recyclerView =(RecyclerView) findViewById(R.id.id_recycler_view);
        total_expenditure = (TextView) findViewById(R.id.id_total_expenses);
        total_count = (TextView) findViewById(R.id.id_total_count);
        add_exp_btn = (FloatingActionButton) findViewById(R.id.id_fab_expenses);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("Expenses"); //getting the link of our database
        mDatabaseReference.keepSynced(true);

        expenseList = new ArrayList<>();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        add_exp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ExpensesAndBillsActivity.this, AddExpenseActivity.class));
                finish(); //it doesn't stack up previous activities
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

                expenseRecyclerAdapter = new ExpenseRecyclerAdapter(ExpensesAndBillsActivity.this,
                        expenseList);
                recyclerView.setAdapter(expenseRecyclerAdapter);

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
