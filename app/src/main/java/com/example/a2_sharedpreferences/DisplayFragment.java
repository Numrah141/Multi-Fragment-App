package com.example.a2_sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DisplayFragment extends Fragment {

    private TextView textDisplayName, textDisplayEmail, textDisplayPassword, textDisplayContact, textDisplayCity;
    private Button btnRefreshView;
    private SharedPreferences dataStorage;

    public DisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeComponents(view);
        loadAndDisplayData();
        setupRefreshButton();

        return view;
    }

    private void initializeComponents(View view) {
        textDisplayName = view.findViewById(R.id.text_display_name);
        textDisplayEmail = view.findViewById(R.id.text_display_email);
        textDisplayPassword = view.findViewById(R.id.text_display_password);
        textDisplayContact = view.findViewById(R.id.text_display_contact);
        textDisplayCity = view.findViewById(R.id.text_display_city);
        btnRefreshView = view.findViewById(R.id.btn_refresh_view);

        dataStorage = requireActivity().getSharedPreferences("AppUserData", Context.MODE_PRIVATE);
    }

    private void loadAndDisplayData() {
        String storedName = dataStorage.getString("userFullName", "Not provided");
        String storedEmail = dataStorage.getString("userEmail", "Not provided");
        String storedPassword = dataStorage.getString("userPassword", "Not provided");
        String storedContact = dataStorage.getString("userContact", "Not provided");
        String storedCity = dataStorage.getString("userCity", "Not provided");

        textDisplayName.setText(storedName);
        textDisplayEmail.setText(storedEmail);
        textDisplayPassword.setText(storedPassword);
        textDisplayContact.setText(storedContact);
        textDisplayCity.setText(storedCity);
    }

    private void setupRefreshButton() {
        btnRefreshView.setOnClickListener(v -> loadAndDisplayData());
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAndDisplayData();
    }
}