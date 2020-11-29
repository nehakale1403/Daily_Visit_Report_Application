package com.gsix.dvr_application.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gsix.dvr_application.Model.Mycheckins;
import com.gsix.dvr_application.R;
import com.gsix.dvr_application.ui.mycheckins.MyCheckinsFragment;

import java.util.List;

public class MyCheckinsAdapter extends RecyclerView.Adapter<MyCheckinsAdapter.ViewHolder> {

    private List<Mycheckins> Mycheckinslist;


    public MyCheckinsAdapter(MyCheckinsFragment myCheckinsFragment, List<Mycheckins> mycheckinslist) {

       this.Mycheckinslist = mycheckinslist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mycheckin_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCheckinsAdapter.ViewHolder holder, int position) {

        Mycheckins mycheckins=Mycheckinslist.get(position);

        holder.Customername.setText("Customer Name: "+mycheckins.getCustomerName());
        holder.visitpurpose.setText("Visit Purpose: "+mycheckins.getvisitpurpose());

        //DateFormat dateFormat = DateFormat.getDateInstance();
        //String formattedDate = dateFormat.format(new Date(Long.valueOf(Mycheckins.getTimestamp())).getTime());
      //  holder.timestamp.setText(formattedDate);


    }

    @Override
    public int getItemCount() {
        return Mycheckinslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Customername;
        public TextView visitpurpose;
        public TextView customertype;

        public ViewHolder(@NonNull View View) {
            super(View);
            Customername = (TextView) View.findViewById(R.id.customerNID);
            visitpurpose = (TextView) View.findViewById(R.id.visitID);
//            customertype=(TextView) View.findViewById(R.id.customer_type);


        }
    }
}
