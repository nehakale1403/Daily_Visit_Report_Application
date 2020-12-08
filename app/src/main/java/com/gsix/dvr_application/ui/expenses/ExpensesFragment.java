package com.gsix.dvr_application.ui.expenses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gsix.dvr_application.ExpensesAndBillsActivity;
import com.gsix.dvr_application.R;

public class ExpensesFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses,container,false);
        Intent intent = new Intent(getContext(), ExpensesAndBillsActivity.class);
        startActivity(intent);
        getActivity().getFragmentManager().popBackStack();
        return view;
    }
}