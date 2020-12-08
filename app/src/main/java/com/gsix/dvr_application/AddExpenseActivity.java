package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {

    private ImageButton billImage;
    private EditText expenseTitle;
    private EditText amount;
    private EditText expenseDesc;
    private Button submitBillBtn;
    private StorageReference mStorage;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog progressDlg;
    private Uri imageUri;
    private static final int GALLERY_CODE = 1;

    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        progressDlg = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();

        userid = mUser.getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Expense").child(userid);

        billImage = (ImageButton) findViewById(R.id.add_image_btn_id);
        expenseTitle = (EditText) findViewById(R.id.id_expense_title);
        amount = (EditText) findViewById(R.id.id_amount);
        expenseDesc = (EditText) findViewById(R.id.id_expense_description);
        submitBillBtn = (Button) findViewById(R.id.submit_expense_id);


        billImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");

                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        });


        submitBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //posting to our database
                startPosting();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {

            imageUri = data.getData();
            billImage.setImageURI(imageUri);

        }
    }

    private void startPosting() {

        progressDlg.setMessage("Posting...");
        progressDlg.show();

        final String titleVal = expenseTitle.getText().toString().trim();
        final String descVal = expenseDesc.getText().toString().trim();
        final String amountVal = amount.getText().toString().trim();

        if (!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal) && !TextUtils.isEmpty(amountVal)
                && imageUri != null) {

            final StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                    .child("images/" + UUID.randomUUID().toString());

            final StorageReference filepath = mStorage.child("Bills").child(imageUri.getLastPathSegment());

            storageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful()) {

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String URL = uri.toString();

                                DatabaseReference newPost = mDatabaseReference.push();

                                Map<String, String> dataToSave = new HashMap<>();
                                dataToSave.put("title", titleVal);
                                dataToSave.put("amount", amountVal);
                                dataToSave.put("description", descVal);
                                dataToSave.put("image", URL);
                                dataToSave.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));

                                newPost.setValue(dataToSave);

//                                total_expenditure += Double.valueOf(amountVal);
//                                total_exp_str= String.valueOf(total_expenditure);

                            }
                        });

                    }

                }
            });

            progressDlg.dismiss();
            Toast.makeText(AddExpenseActivity.this, "Expense uploaded successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(AddExpenseActivity.this, ExpensesAndBillsActivity.class));

            startActivity(new Intent(AddExpenseActivity.this, MainActivity.class));

            finish();
        }else {
            progressDlg.dismiss();
            Toast.makeText(AddExpenseActivity.this, "Please fill all the fields...", Toast.LENGTH_LONG).show();
        }
    }


}