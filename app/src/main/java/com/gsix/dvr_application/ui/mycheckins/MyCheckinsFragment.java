package com.gsix.dvr_application.ui.mycheckins;

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

import com.gsix.dvr_application.R;

public class MyCheckinsFragment extends Fragment {

    private MyCheckinsViewModel myCheckinsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myCheckinsViewModel =
                ViewModelProviders.of(this).get(MyCheckinsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mycheckins, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        myCheckinsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}