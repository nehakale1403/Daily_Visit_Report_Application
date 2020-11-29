package com.gsix.dvr_application.Adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gsix.dvr_application.ExpensesAndBillsActivity;
import com.gsix.dvr_application.Model.Expense;
import com.gsix.dvr_application.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.ViewHolder> {

    private List<Expense> expenseList;


    public ExpenseRecyclerAdapter(ExpensesAndBillsActivity expensesAndBillsActivity, List<Expense> expenseList){
        this.expenseList  = expenseList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expenses_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Instead of creating new view for each new row, an old view is recycled
        //and reused by binding new data to it. This happens exactly in onBindViewHolder()

        Expense expense = expenseList.get(position);

        holder.title.setText(expense.getTitle());
        holder.amount.setText("Amount: " + expense.getAmount() + "Rs.");

        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(expense.getTimestamp())).getTime());
        holder.timestamp.setText(formattedDate);

        String imageurl = expense.getImage();
        Picasso.get().load(imageurl).into(holder.image);

    }


    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView description;
        public TextView amount;
        public TextView timestamp;
        public ImageView image;

        public ViewHolder(@NonNull View view) {
             super(view);

            title = (TextView) view.findViewById(R.id.expense_title);
            amount = (TextView) view.findViewById(R.id.amount);
            image = (ImageView) view.findViewById(R.id.bill_image);
            timestamp = (TextView) view.findViewById(R.id.dateText);
        }
    }
}
