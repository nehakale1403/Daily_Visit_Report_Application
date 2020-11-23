package com.gsix.dvr_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gsix.dvr_application.Model.Expense;

public class AddExpenseActivity extends AppCompatActivity {

    private ImageButton billImage;
    private EditText expenseTitle;
    private EditText expenseDesc;
    private Button submitBillBtn;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog progressDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        progressDlg = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Expense");

        billImage = (ImageButton) findViewById(R.id.add_image_btn_id);
        expenseTitle = (EditText) findViewById(R.id.id_expense_title);
        expenseDesc = (EditText) findViewById(R.id.id_expense_description);
        submitBillBtn = (Button) findViewById(R.id.submit_expense_id);


        submitBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //posting to our database
                startPosting();

            }
        });
    }

    private void startPosting() {

        progressDlg.setMessage("Posting...");
        progressDlg.show();

        String titleVal = expenseTitle.getText().toString().trim();
        String descVal = expenseDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal)){

            //start uploading

            Expense expense = new Expense("Title", "Description", "imageurl",
                    "timestamp", "userid");

            mDatabaseReference.setValue(expense).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Bill added", Toast.LENGTH_LONG).show();
                    progressDlg.dismiss();
                }
            });

        }
    }
}
