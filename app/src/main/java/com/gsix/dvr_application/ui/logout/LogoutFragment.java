package com.gsix.dvr_application.ui.logout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.gsix.dvr_application.Login_Page;
import com.gsix.dvr_application.R;

public class LogoutFragment extends Fragment {
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_logout2, container, false);
        mAuth=FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(getContext(), Login_Page.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().getFragmentManager().popBackStack();
        return view;
    }
}