package com.example.a2_sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PreferencesFragment extends Fragment {

    private EditText inputName, inputEmail, inputPassword, inputContact, inputCity;
    private Button btnStoreData, btnClearAll;
    private TextView textStatus;
    private SharedPreferences dataStorage;

    public PreferencesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initializeComponents(view);
        loadExistingData();
        setupButtonActions();

        return view;
    }

    private void initializeComponents(View view) {
        inputName = view.findViewById(R.id.input_name);
        inputEmail = view.findViewById(R.id.input_email);
        inputPassword = view.findViewById(R.id.input_password);
        inputContact = view.findViewById(R.id.input_contact);
        inputCity = view.findViewById(R.id.input_city);
        btnStoreData = view.findViewById(R.id.btn_store_data);
        btnClearAll = view.findViewById(R.id.btn_clear_all);
        textStatus = view.findViewById(R.id.text_status);

        dataStorage = requireActivity().getSharedPreferences("AppUserData", Context.MODE_PRIVATE);
    }

    private void loadExistingData() {
        inputName.setText(dataStorage.getString("userFullName", ""));
        inputEmail.setText(dataStorage.getString("userEmail", ""));
        // Don't load password for security
        inputContact.setText(dataStorage.getString("userContact", ""));
        inputCity.setText(dataStorage.getString("userCity", ""));
    }

    private void setupButtonActions() {
        btnStoreData.setOnClickListener(v -> storeUserInformation());
        btnClearAll.setOnClickListener(v -> clearAllData());
    }

    private void storeUserInformation() {
        String userName = inputName.getText().toString().trim();
        String userEmail = inputEmail.getText().toString().trim();
        String userPassword = inputPassword.getText().toString().trim();
        String userContact = inputContact.getText().toString().trim();
        String userCity = inputCity.getText().toString().trim();

        if (!validateUserInput(userName, userEmail, userPassword, userContact, userCity)) {
            return;
        }

        SharedPreferences.Editor dataEditor = dataStorage.edit();
        dataEditor.putString("userFullName", userName);
        dataEditor.putString("userEmail", userEmail);
        dataEditor.putString("userPassword", "••••••"); // Mask password for display
        dataEditor.putString("userContact", userContact);
        dataEditor.putString("userCity", userCity);
        dataEditor.apply();

        showStatusMessage("Data stored successfully!", true);
        Toast.makeText(getActivity(), "Information Saved!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateUserInput(String name, String email, String password, String contact, String city) {
        if (name.isEmpty()) {
            showStatusMessage("Please enter your full name", false);
            return false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showStatusMessage("Please enter valid email address", false);
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            showStatusMessage("Password must be at least 6 characters", false);
            return false;
        }
        if (contact.isEmpty() || contact.length() < 10) {
            showStatusMessage("Please enter valid contact number", false);
            return false;
        }
        if (city.isEmpty()) {
            showStatusMessage("Please enter your city", false);
            return false;
        }
        return true;
    }

    private void clearAllData() {
        SharedPreferences.Editor dataEditor = dataStorage.edit();
        dataEditor.clear();
        dataEditor.apply();

        inputName.setText("");
        inputEmail.setText("");
        inputPassword.setText("");
        inputContact.setText("");
        inputCity.setText("");

        showStatusMessage("All data cleared successfully!", false);
        Toast.makeText(getActivity(), "Data Cleared!", Toast.LENGTH_SHORT).show();
    }

    private void showStatusMessage(String message, boolean isSuccess) {
        textStatus.setText(message);
        if (isSuccess) {
            textStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            textStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }
}