package com.gsix.dvr_application.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        Expense expense = expenseList.get(position);
        holder.title.setText(expense.getTitle());
        holder.description.setText(expense.getAmount());

        holder.timestamp.setText(expense.getTimestamp());

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
        public TextView timestamp;
        public ImageView image;

        public ViewHolder(@NonNull View view) {
             super(view);

            title = (TextView) view.findViewById(R.id.expense_title);
            description = (TextView) view.findViewById(R.id.amount);
            image = (ImageView) view.findViewById(R.id.bill_image);
            timestamp = (TextView) view.findViewById(R.id.dateText);
        }
    }
}
