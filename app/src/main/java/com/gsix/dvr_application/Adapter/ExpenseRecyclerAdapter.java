package com.gsix.dvr_application.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gsix.dvr_application.Model.Expense;
import com.gsix.dvr_application.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Expense> expenseList;

    public ExpenseRecyclerAdapter(Context context, List<Expense> expenseList){
        this.context = context;
        this.expenseList  = expenseList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expenses_list_item, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Expense expense = expenseList.get(position);
        String imageurl = null;

        holder.title.setText(expense.getTitle());
        holder.description.setText(expense.getDesc());

        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(expense.getTimestamp())).getTime());

        holder.timestamp.setText(formattedDate);

        imageurl = expense.getImage();

        //to load images in the recycler view

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
        String userid;

        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);

            context = ctx;

            title = (TextView) view.findViewById(R.id.expense_title);
            description = (TextView) view.findViewById(R.id.amount);
            image = (ImageView) view.findViewById(R.id.bill_image);
            timestamp = (TextView) view.findViewById(R.id.dateText);

            userid = null;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //we can go to next activity
                }
            });
        }
    }
}
