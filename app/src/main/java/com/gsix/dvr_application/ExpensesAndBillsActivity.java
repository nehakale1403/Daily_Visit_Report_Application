package com.gsix.dvr_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ExpensesAndBillsActivity extends AppCompatActivity {

    private TextView total_expenditure, total_count;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_and_bills);

        listView =(ListView) findViewById(R.id.id_list_view_expenses);
        total_expenditure = (TextView) findViewById(R.id.id_total_expenses);
        total_count = (TextView) findViewById(R.id.id_total_count);

        refreshData();

    }

    private void refreshData(){


    }
}
