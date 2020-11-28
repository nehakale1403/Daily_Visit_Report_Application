package com.gsix.dvr_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gsix.dvr_application.Model.ToDoModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserID;

    private String key = "";
    private String task;
    private String description;

    private ProgressDialog loader;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
//   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
   setContentView(R.layout.activity_todo_list);


   recyclerView = findViewById(R.id.todoRecyclerView);
   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
   linearLayoutManager.setReverseLayout(true);
   linearLayoutManager.setStackFromEnd(true);
   recyclerView.setHasFixedSize(true);
   recyclerView.setLayoutManager(linearLayoutManager);

   loader = new ProgressDialog(this);

   mAuth = FirebaseAuth.getInstance();
   mUser = mAuth.getCurrentUser();
   onlineUserID = mUser.getUid();
   reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserID);

   floatingActionButton = findViewById(R.id.todofab);
   floatingActionButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           addTask();
       }
   });
       }

    private void addTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View myView = inflater.inflate(R.layout.input_file, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText task = myView.findViewById(R.id.task);
        final EditText description = myView.findViewById(R.id.description);
        Button save = myView.findViewById(R.id.saveBtntodo);
        Button cancel = myView.findViewById(R.id.cancelBtntodo);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTask = task.getText().toString().trim();
                String mDescription = description.getText().toString().trim();
                String id = reference.push().getKey();
                String date = DateFormat.getDateInstance().format(new Date());

                if (TextUtils.isEmpty(mTask)) {
                    task.setError("Task Required");
                    return;
                }
                if (TextUtils.isEmpty(mDescription)) {
                    description.setError("Description Required");
                    return;
                } else {
                    loader.setMessage("Adding Your Data");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    ToDoModel model = new ToDoModel(mTask, mDescription, id, date);
                    reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(TodoListActivity.this, "Task has been inserted successfully", Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(TodoListActivity.this, "Failed" + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onStart(){
       super.onStart();

       FirebaseRecyclerOptions<ToDoModel> options = new FirebaseRecyclerOptions.Builder<ToDoModel>()
               .setQuery(reference, ToDoModel.class)
               .build();
        FirebaseRecyclerAdapter<ToDoModel, MyviewHolder> adapter = new FirebaseRecyclerAdapter<ToDoModel, MyviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyviewHolder holder, final int position, @NonNull final ToDoModel toDoModel) {
                holder.setDate(toDoModel.getDate());
                holder.setTask(toDoModel.getTask());
                holder.setDesc(toDoModel.getDescription());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        key = getRef(holder.getAdapterPosition()).getKey();
                        task = toDoModel.getTask();
                        description = toDoModel.getDescription();

                        updateTask();
                    }
                });
            }

            @NonNull
            @Override
            public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieved_layout,parent,false);
                return new MyviewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
   public static class  MyviewHolder extends RecyclerView.ViewHolder{
        View mView;
       public MyviewHolder(@NonNull View itemView) {
           super(itemView);
           mView = itemView;

       }
       public  void setTask(String task){
           TextView taskTextView = mView.findViewById(R.id.taskTv);
           taskTextView.setText(task);
       }
       public void setDesc(String desc){
           TextView descTextview = mView.findViewById(R.id.descriptionTv);
           descTextview.setText(desc);
       }
       public void setDate(String date){
           TextView dateTextView = mView.findViewById(R.id.dateTv);
           dateTextView.setText(date);
       }
   }
        private void updateTask(){
       AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
       LayoutInflater inflater = LayoutInflater.from(this);
       View view = inflater.inflate(R.layout.update_data,null);
       myDialog.setView(view);

       final AlertDialog dialog = myDialog.create();

       final EditText mTask = view.findViewById(R.id.mEditTextTask);
       final EditText mDescription = view.findViewById(R.id.mEditTextDescription);

       mTask.setText(task);
       mTask.setSelection(task.length());
       mDescription.setText(description);
       mDescription.setSelection(description.length());

       Button delButton = view.findViewById(R.id.btnDelete);
       Button updateButton = view.findViewById(R.id.btnUpdate);

       updateButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               task = mTask.getText().toString().trim();
               description = mDescription.getText().toString().trim();

               String date = DateFormat.getDateInstance().format(new Date());

               ToDoModel model = new ToDoModel(task,description,key,date);
               reference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(TodoListActivity.this, "Data has been Updated Successfully", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           String err = task.getException().toString();
                           Toast.makeText(TodoListActivity.this, "update Failed"+err, Toast.LENGTH_SHORT).show();
                       }
                   }
               });
               dialog.dismiss();
           }
       });

       delButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(TodoListActivity.this, "Task Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String err = task.getException().toString();
                        Toast.makeText(TodoListActivity.this, "Failed to delete"+err, Toast.LENGTH_SHORT).show();
                    }
                }
            });


               dialog.dismiss();
           }
       });

       dialog.show();
        }
}