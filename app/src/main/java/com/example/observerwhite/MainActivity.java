package com.example.observerwhite;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.observerwhite.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent serviceIntent = new Intent(this, AudioService.class);
        startService(serviceIntent);

        boolean isLoose = getIntent().getBooleanExtra("isLoose", false);

        binding.continueBtn.setOnClickListener(v -> {
            startGame(isLoose);
        });

        binding.newGameBtn.setOnClickListener(v -> {
            startGame(true);
        });

        binding.quitBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void startGame(boolean isNewGame) {
        Intent intent = new Intent(this, TownGrowthActivity.class);
        intent.putExtra("isNewGame", isNewGame);
        startActivity(intent);
        finish();
    }
}