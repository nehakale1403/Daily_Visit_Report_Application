package com.gsix.dvr_application.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gsix.dvr_application.CheckinNowActivity;
import com.gsix.dvr_application.ExpensesAndBillsActivity;
import com.gsix.dvr_application.MainActivity;
import com.gsix.dvr_application.PerformanceActivity;
import com.gsix.dvr_application.R;
import com.gsix.dvr_application.TodoListActivity;
import com.gsix.dvr_application.ui.profile.ProfileFragment;

public class DashboardFragment extends Fragment {

    private CardView checkin_now, expenses_bills, todo_list, performance;

        @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);

        checkin_now = view.findViewById(R.id.checkin_now_button);
        expenses_bills = view.findViewById(R.id.expenses_button);
        todo_list = view.findViewById(R.id.todo_list_button);
        performance = view.findViewById(R.id.performance_button);

        checkin_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), CheckinNowActivity.class);
                startActivity(intent);
            }
        });

        expenses_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ExpensesAndBillsActivity.class);
                startActivity(intent);
            }
        });

        todo_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), TodoListActivity.class);
                startActivity(intent);
            }
        });

        performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), PerformanceActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}