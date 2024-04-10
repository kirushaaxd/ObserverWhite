package com.example.observerwhite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.observerwhite.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.continueBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, TownGrowthActivity.class);
            intent.putExtra("isNewGame", false);
            startActivity(intent);
        });

        binding.newGameBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, TownGrowthActivity.class);
            intent.putExtra("isNewGame", true);
            startActivity(intent);
        });

        binding.quitBtn.setOnClickListener(v -> {
            finish();
        });
    }
}