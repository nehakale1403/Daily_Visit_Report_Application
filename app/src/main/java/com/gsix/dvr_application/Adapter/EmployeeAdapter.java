package com.gsix.dvr_application.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gsix.dvr_application.CompanyDashboard;
import com.gsix.dvr_application.Model.Checkin;
import com.gsix.dvr_application.PerformanceActivity;
import com.gsix.dvr_application.R;

import java.util.List;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private List<Checkin> checkinList;

    public EmployeeAdapter(CompanyDashboard companyDashboard, List<Checkin> checkinList){
        this.checkinList  = checkinList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.performancelist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Instead of creating new view for each new row, an old view is recycled
        //and reused by binding new data to it. This happens exactly in onBindViewHolder()

        Checkin checkin = checkinList.get(position);

        holder.empname.setText(checkin.getName());
        holder.empcheckin.setText(checkin.getEmpID());
        holder.ranking.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return checkinList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView empname,empcheckin,ranking;

        public ViewHolder(@NonNull View view) {
            super(view);
            empname=(TextView)itemView.findViewById(R.id.emp_name);
            empcheckin=(TextView)itemView.findViewById(R.id.emp_checkin);
            ranking=(TextView)itemView.findViewById(R.id.ranking1);
        }
    }
}
