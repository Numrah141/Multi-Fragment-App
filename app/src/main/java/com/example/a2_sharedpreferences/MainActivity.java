package com.example.a2_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnPrefScreen, btnDisplayScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        setupEventHandlers();

        // Load default fragment
        loadScreen(new PreferencesFragment());
    }

    private void initializeComponents() {
        btnPrefScreen = findViewById(R.id.btn_pref_screen);
        btnDisplayScreen = findViewById(R.id.btn_display_screen);
    }

    private void setupEventHandlers() {
        btnPrefScreen.setOnClickListener(v -> {
            loadScreen(new PreferencesFragment());
            updateActiveButton(true);
        });

        btnDisplayScreen.setOnClickListener(v -> {
            loadScreen(new DisplayFragment());
            updateActiveButton(false);
        });
    }

    private void loadScreen(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_main, fragment)
                .commit();
    }

    private void updateActiveButton(boolean isPrefScreenActive) {
        if (isPrefScreenActive) {
            btnPrefScreen.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            btnDisplayScreen.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            btnPrefScreen.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            btnDisplayScreen.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        }
    }
}